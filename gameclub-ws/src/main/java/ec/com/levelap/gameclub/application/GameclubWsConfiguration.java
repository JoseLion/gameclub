package ec.com.levelap.gameclub.application;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.Session;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import ec.com.levelap.gameclub.utils.Const;

@Configuration
@EnableConfigurationProperties
public class GameclubWsConfiguration extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GameclubWsApplication.class);
	}
	
	@Value("${wildfly.jndi.data-base}")
	private String JNDI_DATA_BASE;
	
	@Value("${wildfly.jndi.mail}")
	private String JNDI_MAIL;
	
	@Value("${spring.jpa.generate-ddl}")
	private Boolean GENERATE_DDL;

	@Value("${spring.jpa.show-sql}")
	private Boolean SHOW_SQL;

	@Bean
	public DataSource dataBaseDataSource() throws NamingException {
		JndiTemplate jndiTemplate = new JndiTemplate();
		DataSource dataSource = (DataSource) jndiTemplate.lookup(JNDI_DATA_BASE);
		return dataSource;
	}

	@Bean
	public JavaMailSender javaMailService() throws NamingException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding(StandardCharsets.UTF_8.name());
		mailSender.setSession(getMailSession());
		return mailSender;
	}

	private Session getMailSession() throws NamingException {
		JndiTemplate template = new JndiTemplate();
		Session session = (Session) template.lookup(JNDI_MAIL);
		return session;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws NamingException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(GENERATE_DDL);
		vendorAdapter.setShowSql(SHOW_SQL);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(Const.PACKAGE_NAMING);
		factory.setDataSource(this.dataBaseDataSource());
		Properties properties = new Properties();
		properties.setProperty("hibernate.jdbc.batch_size", "100");
		properties.setProperty("hibernate.order_inserts", "true");
		properties.setProperty("hibernate.order_updates", "true");
		factory.setJpaProperties(properties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("1024MB");
		factory.setMaxRequestSize("1024MB");
		return factory.createMultipartConfig();
	}
	
	@Bean
	public ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}
	
	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}
}
