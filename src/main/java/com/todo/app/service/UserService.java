package com.todo.app.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.todo.app.entity.User;
import com.todo.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;
// ユーザーサービス
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository loginRepository;
	
	public User loginAuth(String userName, String password) {
		return loginRepository.findByUserIdAndPassword(userName, password);
	}
	
	public User findById(Long id) {
		return loginRepository.findById(id).orElse(null);
	}
	
	public User findByUserName(String userName) {
		return loginRepository.findByUserName(userName);
	}
	
	@Transactional
	public void regist(User user) {
		loginRepository.save(user);
	}
}
