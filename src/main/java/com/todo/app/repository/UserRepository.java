package com.todo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entity.User;

// ユーザリポジトリ
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserIdAndPassword(String userId, String password);
	
	public List<User> findAllByOrderById();
	
	public List<User> findByTeamId(long teamId);
	
	public User findByUserName(String userName);
}
