package com.todo.app.service;

import java.util.List;

import com.todo.app.entity.Task;
import com.todo.app.form.TaskSearchForm;

public interface TaskService {
	public List<Task> searchIncomplete(Long teamId,TaskSearchForm form);
	
	public List<Task> searchComplete(Long teamId,TaskSearchForm form) ;

	public List<Task> selectAll();

	public List<Task> selectIncomplete(long team_id);

	public List<Task> selectComplete(long team_id);
	
	public void add(Task todo);

	public void update(Task todo);

	public void delete(long id);
}
