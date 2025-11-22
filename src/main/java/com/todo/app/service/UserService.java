package com.todo.app.service;

import java.util.List;

import com.todo.app.entity.User;

public interface UserService {
	public User loginAuth(String userName, String password) ;
	
	public User findById(long id) ;
	
	public List<User> findByTeamId(long teamId);
	
	public User findByUserName(String userName);
	
	public List<User> findAll();
	
	public List<User> findAllByOrderById();

	public void regist(User user) ;
	
	public void update(User user);
	
	public void delete(long id);
}
