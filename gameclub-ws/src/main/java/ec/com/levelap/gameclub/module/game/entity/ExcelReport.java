package ec.com.levelap.gameclub.module.game.entity;

import java.util.HashMap;
import java.util.Map;

public class ExcelReport {
	private Boolean hasFormat;
	
	private Integer totalRows;
	
	private Integer wrongRows;
	
	private Map<String, String> errors = new HashMap<>();

	public ExcelReport(Boolean hasFormat) {
		super();
		this.hasFormat = hasFormat;
	}

	public ExcelReport(Integer totalRows) {
		super();
		this.totalRows = totalRows;
	}

	public Boolean getHasFormat() {
		return hasFormat;
	}

	public void setHasFormat(Boolean hasFormat) {
		this.hasFormat = hasFormat;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getWrongRows() {
		return wrongRows;
	}

	public void setWrongRows(Integer wrongRows) {
		this.wrongRows = wrongRows;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
