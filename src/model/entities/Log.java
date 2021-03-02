package model.entities;

import java.util.Date;

import model.dao.FactoryDAO;

public class Log {

	private String log;
	private Date moment;
	
	public Log() {}
	
	public Log(String log) {
		this.log = log;
		this.moment = new Date();
	}
	
	public Log(Integer id) {
		Log log = FactoryDAO.logDAO().getById(id);
		this.log = log.getLog();
		this.moment = log.getMoment();
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@Override
	public String toString() {
		return "Log [moment=" + moment + ", log=" + log + "]";
	}
}
