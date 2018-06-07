package ec.com.levelap.gameclub.module.game.service;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ec.com.levelap.gameclub.application.ApplicationContextHolder;

@Component
public class GameScheduledTasks {
	@Scheduled(cron="0 0 0 15 * ?")
	public void updateFromPriceCharting() throws ServletException, MessagingException {
		GameService gameService = ApplicationContextHolder.getContext().getBean(GameService.class);
		gameService.reloadPrices();
		
		//this.sendUpdateMail();
	}
	
	/*private void sendUpdateMail() throws MessagingException {
		GameClubMailService mailService = ApplicationContextHolder.getContext().getBean(GameClubMailService.class);
		LevelapMail levelapMail = new LevelapMail();
		levelapMail.setRecipentTO(Arrays.asList(Const.EMAIL_INFO));
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("date", df.format(new Date()));
		
		mailService.sendMailWihTemplate(levelapMail, "PCHUDT", params);
	}*/
}
