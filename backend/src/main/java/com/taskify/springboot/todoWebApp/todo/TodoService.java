package com.taskify.springboot.todoWebApp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class TodoService {
	private static List<Todo> todos = new ArrayList<>(); 
	private static int todosCount=0; 
	static {
		todos.add(new Todo(++todosCount,"name","learn spring",
				LocalDate.now().plusYears(1),false)); 
		todos.add(new Todo(++todosCount,"name","learn dsa",
				LocalDate.now().plusYears(2),false));
		todos.add(new Todo(++todosCount,"name","learn react",
				LocalDate.now().plusYears(3),false));
	}
	public List<Todo> findByUsername(String userName) {
		Predicate<?super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(userName); 
		return todos.stream().filter(predicate).toList(); 
	}
	
	public Todo addTodo(String userName,String description,LocalDate targetDate, boolean done) {
		Todo todo = new Todo(++todosCount,userName,description,targetDate,done); 
		todos.add(todo);
		return todo; 
	}
	
	public void deleteById(int id) {
		Predicate<?super Todo> predicate = todo -> todo.getId()==id; 
		todos.removeIf(predicate); 
	}

	public Todo findById(int id) {
		Predicate<?super Todo> predicate = todo -> todo.getId()==id;
		Todo todo = todos.stream().filter(predicate).findFirst().get(); 
		return todo; 
	}

	public void updateTodo(Todo todo) {
		deleteById(todo.getId()); 
		todos.add(todo); 
	}
}
