package com.todo.app.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.todo.app.entity.Team;
import com.todo.app.repository.TeamRepository;

// チームサービス
@Service
public class TeamServiceImpl implements TeamService {
	private final TeamRepository teamRepository;

	public TeamServiceImpl(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}
	
	public List<Team> findAll() {
		return   teamRepository.findAll();
	}
	
	public List<Team> findAllByOrderById() {
		return teamRepository.findAllByOrderById();
	}
	
	public Team findById(long id) {
		return teamRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void regist(Team team) {
		teamRepository.save(team);
	}
	
	@Transactional
	public void update(Team team) {
		teamRepository.save(team);
	}
	
	@Transactional
	public void delete(long id) {
		teamRepository.deleteById(id);
	}
}
