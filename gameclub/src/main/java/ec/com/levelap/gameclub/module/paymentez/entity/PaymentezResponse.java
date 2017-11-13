package ec.com.levelap.gameclub.module.paymentez.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentezResponse {
	
	private Date date;
	
	@JsonProperty("paid_date")
	private Date paidDate;
	
	@JsonProperty("application_code")
	private String applicationCode;
	
	@JsonProperty("user_id")
	private Long userId;
	
	@JsonProperty("transaction_id")
	private Long transactionId;
	
	@JsonProperty("recurrent_transaction_id")
	private Long recurrentTransactionId;
	
	@JsonProperty("product_id")
	private Long productId;
	
	private String stoken;
	
	private String currency;
	
	@JsonProperty("gross_value")
	private Long grossValue;
	
	@JsonProperty("num_coins")
	private Integer num_coins;
	
	@JsonProperty("product_description")
	private String productDescription;
	
	private Integer carrier;
	
	@JsonProperty("payment_method")
	private Integer paymentMethod;
	
	@JsonProperty("dev_reference")
	private String devReference;
	
	private Integer status;
	
	@JsonProperty("test_mode")
	private Boolean testMode;
	
	@JsonProperty("buyer_first_name")
	private String buyerFirstName;
	
	@JsonProperty("buyer_last_name")
	private String buyerLastName;
	
	@JsonProperty("buyer_phone")
	private String buyerPhone;
	
	@JsonProperty("buyer_ip")
	private String buyerIp;
	
	@JsonProperty("buyer_email")
	private String buyerEmail;
	
	@JsonProperty("buyer_street")
	private String buyerStreet;
	
	@JsonProperty("buyer_number")
	private String buyerNumber;
	
	@JsonProperty("buyer_complement")
	private String buyerComplement;
	
	@JsonProperty("buyer_district")
	private String buyerDistrict;
	
	@JsonProperty("buyer_city")
	private String buyerCity;
	
	@JsonProperty("buyer_state")
	private String buyerState;
	
	@JsonProperty("buyer_zip_code")
	private String buyerZipCode;
	
	@JsonProperty("buyer_country")
	private String buyerCountry;
	
	@JsonProperty("pm_user_id")
	private Long pmUserId;
	
	@JsonProperty("usd_amount")
	private Double usdAmount;
	
	@JsonProperty("status_detail")
	private Integer statusDetail;
	
	@JsonProperty("bank_name")
	private String bankName;
	
	@JsonProperty("authorization_code")
	private String authorizationCode;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getRecurrentTransactionId() {
		return recurrentTransactionId;
	}

	public void setRecurrentTransactionId(Long recurrentTransactionId) {
		this.recurrentTransactionId = recurrentTransactionId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getStoken() {
		return stoken;
	}

	public void setStoken(String stoken) {
		this.stoken = stoken;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(Long grossValue) {
		this.grossValue = grossValue;
	}

	public Integer getNum_coins() {
		return num_coins;
	}

	public void setNum_coins(Integer num_coins) {
		this.num_coins = num_coins;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getCarrier() {
		return carrier;
	}

	public void setCarrier(Integer carrier) {
		this.carrier = carrier;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDevReference() {
		return devReference;
	}

	public void setDevReference(String devReference) {
		this.devReference = devReference;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getTestMode() {
		return testMode;
	}

	public void setTestMode(Boolean testMode) {
		this.testMode = testMode;
	}

	public String getBuyerFirstName() {
		return buyerFirstName;
	}

	public void setBuyerFirstName(String buyerFirstName) {
		this.buyerFirstName = buyerFirstName;
	}

	public String getBuyerLastName() {
		return buyerLastName;
	}

	public void setBuyerLastName(String buyerLastName) {
		this.buyerLastName = buyerLastName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerIp() {
		return buyerIp;
	}

	public void setBuyerIp(String buyerIp) {
		this.buyerIp = buyerIp;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerStreet() {
		return buyerStreet;
	}

	public void setBuyerStreet(String buyerStreet) {
		this.buyerStreet = buyerStreet;
	}

	public String getBuyerNumber() {
		return buyerNumber;
	}

	public void setBuyerNumber(String buyerNumber) {
		this.buyerNumber = buyerNumber;
	}

	public String getBuyerComplement() {
		return buyerComplement;
	}

	public void setBuyerComplement(String buyerComplement) {
		this.buyerComplement = buyerComplement;
	}

	public String getBuyerDistrict() {
		return buyerDistrict;
	}

	public void setBuyerDistrict(String buyerDistrict) {
		this.buyerDistrict = buyerDistrict;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerState() {
		return buyerState;
	}

	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

	public String getBuyerZipCode() {
		return buyerZipCode;
	}

	public void setBuyerZipCode(String buyerZipCode) {
		this.buyerZipCode = buyerZipCode;
	}

	public String getBuyerCountry() {
		return buyerCountry;
	}

	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
	}

	public Long getPmUserId() {
		return pmUserId;
	}

	public void setPmUserId(Long pmUserId) {
		this.pmUserId = pmUserId;
	}

	public Double getUsdAmount() {
		return usdAmount;
	}

	public void setUsdAmount(Double usdAmount) {
		this.usdAmount = usdAmount;
	}

	public Integer getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(Integer statusDetail) {
		this.statusDetail = statusDetail;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	@Override
	public String toString() {
		return "PaymentezResponse [date: " + date + ",\npaidDate: " + paidDate + ",\napplicationCode: "
				+ applicationCode + ",\nuserId: " + userId + ",\ntransactionId: " + transactionId
				+ ",\nrecurrentTransactionId: " + recurrentTransactionId + ",\nproductId: " + productId + ",\nstoken: "
				+ stoken + ",\ncurrency: " + currency + ",\ngrossValue: " + grossValue + ",\nnum_coins: " + num_coins
				+ ",\nproductDescription: " + productDescription + ",\ncarrier: " + carrier + ",\npaymentMethod: "
				+ paymentMethod + ",\ndevReference: " + devReference + ",\nstatus: " + status + ",\ntestMode: "
				+ testMode + ",\nbuyerFirstName: " + buyerFirstName + ",\nbuyerLastName: " + buyerLastName
				+ ",\nbuyerPhone: " + buyerPhone + ",\nbuyerIp: " + buyerIp + ",\nbuyerEmail: " + buyerEmail
				+ ",\nbuyerStreet: " + buyerStreet + ",\nbuyerNumber: " + buyerNumber + ",\nbuyerComplement: "
				+ buyerComplement + ",\nbuyerDistrict: " + buyerDistrict + ",\nbuyerCity: " + buyerCity
				+ ",\nbuyerState: " + buyerState + ",\nbuyerZipCode: " + buyerZipCode + ",\nbuyerCountry: "
				+ buyerCountry + ",\npmUserId: " + pmUserId + ",\nusdAmount: " + usdAmount + ",\nstatusDetail: "
				+ statusDetail + ",\nbankName: " + bankName + ",\nauthorizationCode: " + authorizationCode + "]";
	}
}
