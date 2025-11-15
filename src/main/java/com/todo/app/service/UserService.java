package com.todo.app.service;

import jakarta.transaction.Transactional;

import com.todo.app.entity.User;

public interface UserService {
	public User loginAuth(String userName, String password) ;
	
	public User findById(Long id) ;
	
	public User findByUserName(String userName);
	
	@Transactional
	public void regist(User user) ;
}
