package model.dao;

import model.entities.Log;

public interface LogDAO {

	public Log getById(Integer id);
	public boolean log(Log log);
	
	
}
