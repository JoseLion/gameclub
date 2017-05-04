package ec.com.levelap.gameclub.application;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.module.user.entity.AdminUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.repository.AdminUserRepo;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.mail.MailParameters;
import ec.com.levelap.security.SecurityConfig;

public class GameClubSecurity implements SecurityConfig {
	@Override
	public Long getUserId(String username, String extra) {
		if (extra != null) {
			if (extra.equals(Const.ADMIN_USER)) {
				AdminUserRepo adminUserRepo = ApplicationContextHolder.getContext().getBean(AdminUserRepo.class);
				AdminUser user = adminUserRepo.findByUsername(username);
				
				if (user != null) {
					return user.getId();
				}
			}
			
			if (extra.equals(Const.PUBLIC_USER)) {
				PublicUserRepo publicUserRepo = ApplicationContextHolder.getContext().getBean(PublicUserRepo.class);
				PublicUser user = publicUserRepo.findByUsername(username);
				
				if (user != null) {
					return user.getId();
				}
			}
		}
		
		return -1L;
	}

	@Override
	public String getPassword(String username, String extra) {
		if (extra != null) {
			if (extra.equals(Const.ADMIN_USER)) {
				AdminUserRepo adminUserRepo = ApplicationContextHolder.getContext().getBean(AdminUserRepo.class);
				AdminUser user = adminUserRepo.findByUsername(username);
				
				if (user != null) {
					return user.getPassword();
				}
			}
			
			if (extra.equals(Const.PUBLIC_USER)) {
				PublicUserRepo publicUserRepo = ApplicationContextHolder.getContext().getBean(PublicUserRepo.class);
				PublicUser user = publicUserRepo.findByUsername(username);
				
				if (user != null) {
					return user.getPassword();
				}
			}
		}
		
		return "****";
	}

	@Override
	public List<String> getRoles(String username, String extra) {
		return null;
	}

	@Override
	public Boolean getIsLoked(String username, String extra) {
		if (extra != null) {
			if (extra.equals(Const.ADMIN_USER)) {
				AdminUserRepo adminUserRepo = ApplicationContextHolder.getContext().getBean(AdminUserRepo.class);
				AdminUser user = adminUserRepo.findByUsername(username);
				
				if (user != null) {
					return !user.getStatus();
				}
			}
			
			if (extra.equals(Const.PUBLIC_USER)) {
				PublicUserRepo publicUserRepo = ApplicationContextHolder.getContext().getBean(PublicUserRepo.class);
				PublicUser user = publicUserRepo.findByUsername(username);
				
				if (user != null) {
					return !user.getStatus();
				}
			}
		}
		
		return null;
	}

	@Override
	public Date getLockDate(String username, String extra) {
		return null;
	}

	@Override
	public void setLockDate(String username, Date date, String extra) {
		
	}

	@Override
	public Double getLockTimeMinutes() {
		return null;
	}

	@Override
	public Date getLastFailedAttempt(String username, String extra) {
		return null;
	}

	@Override
	public void setLastFailedAttempt(String username, Date date, String extra) {
		
	}

	@Override
	public Integer getNumberOfAttempts(String username, String extra) {
		return null;
	}

	@Override
	public void setNumberOfAttempts(String username, Integer attempts, String extra) {
		
	}

	@Override
	public Integer getMaxAttempts() {
		return null;
	}

	@Override
	public Double getResetTimeHours() {
		return null;
	}

	@Override
	public boolean resetUserPassword(String username, String extra) {
		if (extra != null) {
			MailService mail = ApplicationContextHolder.getContext().getBean(MailService.class);
			
			if (extra.equals(Const.ADMIN_USER)) {
				AdminUserRepo adminUserRepo = ApplicationContextHolder.getContext().getBean(AdminUserRepo.class);
				AdminUser user = adminUserRepo.findByUsername(username);
				
				if (user != null) {
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
					SecureRandom random = new SecureRandom();
					
					String randomPassword = "";
					for (int i = 0; i < 8; i++) {
						randomPassword += Const.PASSWORD_SYMBOLS.charAt(random.nextInt(Const.PASSWORD_SYMBOLS.length()));
					}
					
					String encodedPassword = encoder.encode(randomPassword);
					user.setPassword(encodedPassword);
					user.setHasTempPassword(true);
					
					MailParameters mailParameters = new MailParameters();
					mailParameters.setRecipentTO(Arrays.asList(user.getUsername()));
					Map<String, String> params = new HashMap<>();
					params.put("password", randomPassword);
					
					try {
						mail.sendMailWihTemplate(mailParameters, "TMPWRD", params);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					
					adminUserRepo.save(user);
					return true;
				}
			}
			
			if (extra.equals(Const.PUBLIC_USER)) {
				PublicUserRepo publicUserRepo = ApplicationContextHolder.getContext().getBean(PublicUserRepo.class);
				PublicUser user = publicUserRepo.findByUsername(username);
				
				if (user != null) {
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Const.ENCODER_STRENGTH);
					SecureRandom random = new SecureRandom();
					
					String randomPassword = "";
					for (int i = 0; i < 8; i++) {
						randomPassword += Const.PASSWORD_SYMBOLS.charAt(random.nextInt(Const.PASSWORD_SYMBOLS.length()));
					}
					
					String encodedPassword = encoder.encode(randomPassword);
					user.setPassword(encodedPassword);
					user.setHasTempPassword(true);
					
					MailParameters mailParameters = new MailParameters();
					mailParameters.setRecipentTO(Arrays.asList(user.getUsername()));
					Map<String, String> params = new HashMap<>();
					params.put("password", randomPassword);
					
					try {
						mail.sendMailWihTemplate(mailParameters, "TMPWRD", params);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					
					publicUserRepo.save(user);
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void handleSuccess() {
		PublicUserRepo publicUserRepo = ApplicationContextHolder.getContext().getBean(PublicUserRepo.class);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		PublicUser publicUser = publicUserRepo.findByUsername(auth.getName());
		publicUser.setLastConnection(new Date());
		
		publicUserRepo.save(publicUser);
	}

	@Override
	public void handleError() {
		
	}

}
