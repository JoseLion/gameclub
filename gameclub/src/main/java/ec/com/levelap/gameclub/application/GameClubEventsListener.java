package ec.com.levelap.gameclub.application;

import javax.servlet.ServletException;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ec.com.levelap.gameclub.module.loan.service.LoanService;

@Component
public class GameClubEventsListener {
	
	@EventListener(classes=ContextRefreshedEvent.class)
	public void contextRefreshed()  throws ServletException {
		LoanService loanService = ApplicationContextHolder.getContext().getAutowireCapableBeanFactory().getBean(LoanService.class);
		System.out.println("********************** RESCHEDULING TASKS!!!");
		loanService.rescheduleTasks();
	}
}
