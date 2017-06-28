package ec.com.levelap.gameclub.module.game.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.repository.GameRepo;

@Component
public class GameScheduledTasks {
	@Scheduled(cron="0 0 0 15 * ?")
	public void updateFromPriceCharting() throws ServletException {
		GameRepo gameRepo = ApplicationContextHolder.getContext().getBean(GameRepo.class);
		GameService gameService = ApplicationContextHolder.getContext().getBean(GameService.class);
		
		List<Game> games = gameRepo.findAll();
		for (Game game : games) {
			if (game.getPriceChartingId() != null) {
				HashMap<String, String> priceCharting = gameService.getPriceCharting("" + game.getPriceChartingId());
				Double newPrice = gameService.getAvailablePrice(priceCharting);
				
				if (newPrice != null) {
					game.setUploadPayment(newPrice);
				}
			}
		}
		
		gameRepo.save(games);
	}
}
