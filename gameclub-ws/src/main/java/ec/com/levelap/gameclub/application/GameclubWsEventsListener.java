package ec.com.levelap.gameclub.application;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ec.com.levelap.gameclub.module.game.service.GameScheduledTasks;
import ec.com.levelap.gameclub.module.loan.service.LoanService;

@Component
public class GameclubWsEventsListener {
	
	@EventListener(classes=ContextRefreshedEvent.class)
	public void contextRefreshed()  throws ServletException, MessagingException {
		LoanService loanService = ApplicationContextHolder.getContext().getAutowireCapableBeanFactory().getBean(LoanService.class);
		loanService.rescheduleTasks();
		
		GameScheduledTasks gameScheduleTasks = ApplicationContextHolder.getContext().getBean(GameScheduledTasks.class);
		gameScheduleTasks.updateFromPriceCharting();
	}
}
