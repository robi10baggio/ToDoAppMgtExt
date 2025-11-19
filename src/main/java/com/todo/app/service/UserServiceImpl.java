package com.todo.app.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.repository.UserRepository;
// ユーザーサービス
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository loginRepository;
	
	public UserServiceImpl(UserRepository loginRepository) {
		this.loginRepository = loginRepository;
	}
	
	public User loginAuth(String userName, String password) {
		return loginRepository.findByUserIdAndPassword(userName, password);
	}
	
	public List<User> findAll() {
		return   loginRepository.findAll();
	}
	
	public User findById(long id) {
		return loginRepository.findById(id).orElse(null);
	}
	
	public List<User> findAllByOrderById() {
		return loginRepository.findAllByOrderById();
	}
	
	public List<User> findByTeamId(long teamId) {
		return loginRepository.findByTeamId(teamId);
	}
	
	public User findByUserName(String userName) {
		return loginRepository.findByUserName(userName);
	}
	
	@Transactional
	public void regist(User user) {
		loginRepository.save(user);
	}
	
	@Transactional
	public void update(User user) {
		loginRepository.save(user);
	}
	
	@Transactional
	public void delete(long id) {
		loginRepository.deleteById(id);
	}
}
