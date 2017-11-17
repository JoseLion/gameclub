package ec.com.levelap.gameclub.module.game.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.repository.GameRepo;
import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.mail.MailParameters;

@Component
public class GameScheduledTasks {
	@Scheduled(cron="0 0 0 15 * ?")
	public void updateFromPriceCharting() throws ServletException, MessagingException {
		GameRepo gameRepo = ApplicationContextHolder.getContext().getBean(GameRepo.class);
		GameService gameService = ApplicationContextHolder.getContext().getBean(GameService.class);
		MailService mailService = ApplicationContextHolder.getContext().getBean(MailService.class);
		
		List<Game> games = gameRepo.findAll();
		for (Game game : games) {
			if (game.getPriceChartingId() != null) {
				HashMap<String, String> priceCharting = gameService.getPriceCharting("" + game.getPriceChartingId());
				Double newPrice = gameService.getAvailablePrice(priceCharting);
				
				if (newPrice != null) {
					String percentage = gameRepo.priceChartinNationalitation();
					newPrice = (double) Math.round(((newPrice*Double.parseDouble(percentage)/100)+newPrice)*100)/100;
					game.setUploadPayment(newPrice);
				}
			}
		}
		
		gameRepo.save(games);
		
		MailParameters mailParameters = new MailParameters();
		mailParameters.setRecipentTO(Arrays.asList(Const.SYSTEM_ADMIN_EMAIL));
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Map<String, String> params = new HashMap<>();
		params.put("date", df.format(new Date()));

		mailService.sendMailWihTemplate(mailParameters, "PCHUDT", params);
	}
}
