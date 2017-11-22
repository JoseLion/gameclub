package ec.com.levelap.gameclub.module.reports.amountRequestReport.entity;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.reports.amountRequestReport.repository.AmountRequestReportRepo;
import ec.com.levelap.gameclub.utils.Const;

@Entity
@Table(schema=Const.SCHEMA, name="amount_request_report")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Immutable
public class AmountRequestReport {

	@Id
	@Column
	private Long id;
	
	@Column(name="application_date")
	private Date applicationDate;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column
	private String document;
	
	@JsonIgnore
	@Column
	private byte[] amount;
	
	@Column
	private String status;

	@Column(name="payment_date")
	private Date paymentDate;
	
	@Column(name="id_user")
	private Long idUser;
	
	@Transient
	private Double shownAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public byte[] getAmount() {
//		try {
//			getShownAmount();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (GeneralSecurityException e) {
//			e.printStackTrace();
//		}
		return amount;
	}

	public void setAmount(byte[] amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Double getShownAmount() throws IOException, GeneralSecurityException {
		AmountRequestReportRepo amtRqRpRepo = ApplicationContextHolder.getContext().getBean(AmountRequestReportRepo.class);
		byte[] privateKey = amtRqRpRepo.userKey(getIdUser());
		
		if (privateKey != null && amount.length > 0) {
			LevelapCryptography cryptoService = ApplicationContextHolder.getContext().getBean(LevelapCryptography.class);
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, privateKey);
			String decypted = cryptoService.decrypt(amount, key);
			shownAmount = Double.parseDouble(decypted);
		}
		
		return shownAmount;
	}
	
}
