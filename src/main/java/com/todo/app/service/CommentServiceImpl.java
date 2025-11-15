package com.todo.app.service;

import org.springframework.stereotype.Service;

import com.todo.app.entity.Comment;
import com.todo.app.repository.CommentRepository;

// コメントサービス
@Service
public class CommentServiceImpl implements CommonService {

	private final CommentRepository commentRepository;
	
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public void add(Comment comment) {
		commentRepository.save(comment);
	}
}
