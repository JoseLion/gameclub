package ec.com.levelap.gameclub.application;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import ec.com.levelap.gameclub.module.mail.service.MailService;
import ec.com.levelap.mail.MailParameters;

public class GameClubMailTasklet implements Tasklet {
	@Autowired
	private MailService mailService;
	
	private MailParameters mailParameters;
	
	private String template;
	
	private Map<String, String> params;
	
	public MailParameters getMailParameters() {
		return mailParameters;
	}

	public void setMailParameters(MailParameters mailParameters) {
		this.mailParameters = mailParameters;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		mailService.sendMailWihTemplate(mailParameters, template, params);
		return RepeatStatus.FINISHED;
	}
	
	public RepeatStatus sendMail() throws Exception {
		return this.execute(null, null);
	}
}
