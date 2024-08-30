//package com.taskify.springboot.todoWebApp;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import com.taskify.springboot.todoWebApp.todo.Todo;
//import com.taskify.springboot.todoWebApp.todo.repository.TodoRepository;
//
//
//@Controller
//@SessionAttributes("name")
//public class TodoControllerJpa {
//	
////	private TodoService todoService;
//	private TodoRepository todoRepository; 
//	
//	public TodoControllerJpa(TodoRepository todoRepository) {
//		super();
//		//this.todoService = todoService;
//		this.todoRepository = todoRepository; 
//	}
//
//
//	@RequestMapping("list-todos")
//	public String listAllTodos(ModelMap model) {
//		String userName = getLoggedInUserName(model); 
//		List<Todo> todos = todoRepository.findByUsername(userName); 
//		model.addAttribute("todos",todos); 
//		return "listTodos";
//	}
//
//
//	private String getLoggedInUserName(ModelMap model) {
//		Authentication authentication = 
//				SecurityContextHolder.getContext().getAuthentication();
//		return authentication.getName(); 
//	}
//	
//	@RequestMapping(value="add-todos",method=RequestMethod.GET)
//	public String showNewTodoPage(ModelMap model) {
//		String userName = getLoggedInUserName(model); 
//		Todo todo = new Todo(0,userName,"",LocalDate.now().plusYears(1),false);
//		model.put("todo", todo); 
//		return "todo";
//	}
//	@RequestMapping(value="add-todos",method=RequestMethod.POST)
//	public String addNewTodo(ModelMap model,@Valid Todo todo,BindingResult result) {
//		if(result.hasErrors()) {
//			return "todo"; 
//		}
//		String username=getLoggedInUserName(model);
//		todo.setUsername(username);
//		todoRepository.save(todo); 
//		//todoService.addTodo(userName, todo.getDescription(), todo.getTargetDate(), todo.isDone());
//		return "redirect:list-todos";
//	}
//	
//	@RequestMapping("delete-todo")
//	public String deleteTodo(@RequestParam int id) {
//		todoRepository.deleteById(id); 
//		//todoService.deleteById(id);
//		return "redirect:list-todos";
//	}
//	
//	@RequestMapping(value="update-todo",method=RequestMethod.GET)
//	public String showUpdateTodoPage(@RequestParam int id,ModelMap model) {
//		Todo todo = todoRepository.findById(id).get();
//		model.addAttribute("todo",todo); 
//		return "todo";
//	}
//	
//	@RequestMapping(value="update-todo",method=RequestMethod.POST)
//	public String updateTodo(ModelMap model,@Valid Todo todo,BindingResult result) {
//		if(result.hasErrors()) {
//			return "todo"; 
//		}
//		String username=getLoggedInUserName(model);
//		todo.setUsername(username);
//		todoRepository.save(todo); 
//		//todoService.updateTodo(todo);
//		return "redirect:list-todos";
//	}
//}
