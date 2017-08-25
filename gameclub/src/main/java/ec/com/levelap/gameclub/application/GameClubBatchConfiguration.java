package ec.com.levelap.gameclub.application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class GameClubBatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public GameClubMailTasklet gameClubMailTasklet() {
		return new GameClubMailTasklet();
	}
	
	@Bean
	public Step gameClubStep(GameClubMailTasklet gameClubMailTasklet) {
		return stepBuilderFactory.get("gameClubStep").tasklet(gameClubMailTasklet).build();
	}
	
	@Bean
	public Job gameClubJob(Step gameClubStep) {
		return jobBuilderFactory.get("gameClubJob").incrementer(new RunIdIncrementer()).start(gameClubStep).build();
	}
}
