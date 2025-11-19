package com.todo.app.service;

import java.util.List;

import com.todo.app.entity.Team;

public interface TeamService {
	public List<Team> findAll();
	
	public List<Team> findAllByOrderById();
	
	public Team findById(long id);
	
	public void regist(Team team);
	
	public void update(Team team);
	
	public void delete(long id);
}
