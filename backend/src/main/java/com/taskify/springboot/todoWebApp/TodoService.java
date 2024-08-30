//package com.taskify.springboot.todoWebApp;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.springframework.stereotype.Service;
//
//import com.taskify.springboot.todoWebApp.todo.Todo;
//
//import jakarta.validation.Valid;
//
//@Service
//public class TodoService {
//	private static List<Todo> todos = new ArrayList<>(); 
//	private static int todosCount=0; 
//	static {
//		todos.add(new Todo(++todosCount,"name","learn spring",
//				LocalDate.now().plusYears(1),false)); 
//		todos.add(new Todo(++todosCount,"name","learn dsa",
//				LocalDate.now().plusYears(2),false));
//		todos.add(new Todo(++todosCount,"name","learn react",
//				LocalDate.now().plusYears(3),false));
//	}
//	public List<Todo> findByUserName(String userName) {
//		Predicate<?super Todo> predicate = todo -> todo.getUserName().equalsIgnoreCase(userName); 
//		return todos.stream().filter(predicate).toList(); 
//	}
//	
//	public void addTodo(String userName,String description,LocalDate targetDate, boolean done) {
//		Todo todo = new Todo(++todosCount,userName,description,targetDate,done); 
//		todos.add(todo);
//	}
//	
//	public void deleteById(int id) {
//		Predicate<?super Todo> predicate = todo -> todo.getId()==id; 
//		todos.removeIf(predicate); 
//	}
//
//	public Todo findById(int id) {
//		Predicate<?super Todo> predicate = todo -> todo.getId()==id;
//		Todo todo = todos.stream().filter(predicate).findFirst().get(); 
//		return todo; 
//	}
//
//	public void updateTodo(@Valid Todo todo) {
//		deleteById(todo.getId()); 
//		todos.add(todo); 
//	}
//}
