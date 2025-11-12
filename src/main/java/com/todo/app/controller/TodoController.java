package com.todo.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.app.entity.Comment;
import com.todo.app.entity.Todo;
import com.todo.app.entity.User;
import com.todo.app.form.CommentForm;
import com.todo.app.form.TodoForm;
import com.todo.app.form.TodoSearchForm;
import com.todo.app.model.Account;
import com.todo.app.service.CommentService;
import com.todo.app.service.TodoService;
import com.todo.app.service.UserService;

@Controller
@RequestMapping("/todo")
public class TodoController {
	
	// インジェクトされたセッション情報(ログイン情報)を保持するモデル
	private final Account account;
	
	private final TodoService todoService;
	
	private final UserService userService;

	private final CommentService commentService;
	
	// ステータスメニュー
	private static Map<Integer, String> statusMenumap = new HashMap<>();
	
	// コンストラクタinjection
	public TodoController(TodoService todoService, UserService userService, CommentService commentService, Account account) {
		this.todoService = todoService;
		this.userService = userService;
		this.commentService = commentService;
		this.account = account;
		statusMenumap.put(0, "未着手");
    	statusMenumap.put(1, "実施中");
    	statusMenumap.put(2, "完了");
	}
	
	// ステータスメニュー取得
    public static  Map<Integer, String> getStatusMenu() {
		return TodoController.statusMenumap;
    }
    
    public Account getAccount() {
		return this.account;
    }
    
    // リスト更新
    private void updateList(List<Todo> list, List<Todo> doneList, Model model) {
		List<TodoForm> forms = new ArrayList<>();
		for (Todo todo: list) {
			// Entityから表示用のFormに変換
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTaskContent(todo.getTaskContent());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamName(todo.getUser().getTeam().getTeamName());
			
			form.setDueDate(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			List<CommentForm> commForms = new ArrayList<>();
			List<Comment>comments = todo.getComments();
			for (Comment comment:comments) {
			
				CommentForm commForm = new CommentForm();
				commForm.setComment(comment.getComment());
				commForm.setUserName(userService.findById(comment.getUserId()).getUserName());
				commForm.setPostDate(comment.getPostDate());
				commForms.add(commForm);
			}
			
			form.setComments(commForms);;
			forms.add(form);
		}
		model.addAttribute("todos",forms);
		
		List<TodoForm> doneForms = new ArrayList<>();
		for (Todo todo:doneList) {
			// Entityから表示用のFormに変換
			TodoForm form = new TodoForm();
			form.setId(todo.getId());
			form.setTaskContent(todo.getTaskContent());
			form.setStatus(todo.getStatus());
			form.setUserId(todo.getUser().getId());
			form.setUserName(todo.getUser().getUserName());
			form.setTeamName(todo.getUser().getTeam().getTeamName());
			
			form.setDueDate(todo.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			List<CommentForm> commForms = new ArrayList<>();
			List<Comment>comments = todo.getComments();
			for (Comment comment:comments) {
			
				CommentForm commForm = new CommentForm();
				commForm.setComment(comment.getComment());
				commForm.setUserName(userService.findById(comment.getUserId()).getUserName());
				commForm.setPostDate(comment.getPostDate());
				commForms.add(commForm);
			}
			
			form.setComments(commForms);
			doneForms.add(form);
		}
		
		model.addAttribute("doneTodos",doneForms);
		model.addAttribute("statusMenu", getStatusMenu());
		model.addAttribute("account", getAccount());
    }
    
    // 一覧表示
	@GetMapping("/list")
	public String list(
			TodoForm todoForm, 
			TodoSearchForm todoSearchForm,
			Model model) {
		User user = userService.findById(account.getUserId());
    	Long teamId = user.getTeam().getId();
		List<Todo> list = todoService.selectIncomplete(teamId);
		List<Todo> doneList = todoService.selectComplete(teamId);
		updateList(list, doneList, model);
		
		return "Todo-list";
	}
	
	// 追加実行
	@PostMapping("/add")
	public String add(
			@Validated TodoForm todoForm,
			TodoSearchForm todoSearchForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttribute,
			Model model) {

		//バリデーションチェック
		if (bindingResult.hasErrors()) {
			User user = userService.findById(account.getUserId());
	    	Long teamId = user.getTeam().getId();
			List<Todo> list = todoService.selectIncomplete(teamId);
			List<Todo> doneList = todoService.selectComplete(teamId);
			updateList(list, doneList, model);
			return "Todo-list";
		}
		Todo todo = new Todo();
		todo.setTaskContent(todoForm.getTaskContent());
		todo.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		todo.setStatus(0);
		User user = userService.findById(account.getUserId()); 

		todo.setUser(user);
		todoService.add(todo);
		
		return "redirect:/todo/list";
	}

	// 更新実行
	@PostMapping("/update/{id}")
	public String update(
			@PathVariable Long id, 
			@Validated TodoForm todoForm,
			TodoSearchForm todoSearchForm) {

		Todo todo = new Todo();
		todo.setId(id);
		todo.setTaskContent(todoForm.getTaskContent());
		todo.setDueDate(LocalDate.parse(todoForm.getDueDate()));
		
		todo.setStatus(todoForm.getStatus());
		User user = userService.findById(account.getUserId()); 

		todo.setUser(user);
		todoService.update(todo);
		return "redirect:/todo/list";
	}
	// 削除実行
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		todoService.delete(id);
		return "redirect:/todo/list";
	}
	
	
	// 検索実行
	@PostMapping("/search")
	public String searchTodos(
			TodoForm todoForm, 
			@Validated TodoSearchForm todoSearchForm, 
			Model model) {
		Long userId = null;
		if (!todoSearchForm.getUserName().isEmpty()) { 
			User user = userService.findByUserName(todoSearchForm.getUserName());
		
			if (user != null) {
				userId = user.getId();
			}
		}
		todoSearchForm.setUserId(userId);
		User user = userService.findById(account.getUserId());
    	Long teamId = user.getTeam().getId();
		List<Todo> list = todoService.searchIncomplete(teamId, todoSearchForm);
		List<Todo> doneList = todoService.searchComplete(teamId, todoSearchForm);
		updateList(list, doneList, model);
	    
		return "Todo-list";
	}
	
	// コメント追加実行
	@PostMapping("/comment-add/{id}")
	public String commentAdd(
			@PathVariable Long id, 
			@RequestParam String comment, Model model) {
		
		Comment commentObj = new Comment();
		commentObj.setComment(comment);
		commentObj.setTodoId(id);
		commentObj.setUserId(account.getUserId());
		commentObj.setPostDate(LocalDate.now());
		
		commentService.add(commentObj);
		return "redirect:/todo/list";
	}
}
