package com.taskify.springboot.todoWebApp.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskify.springboot.todoWebApp.todo.repository.TodoRepository;

@RestController
public class TodoJpaResource {

	// retrieveTodos
	// retrieveTodo
	// deleteTodo
	// updateTodo
	// createTodo 
	
	private TodoRepository todoRepository; 
	
	public TodoJpaResource(TodoRepository todoRepository) {
		this.todoRepository = todoRepository; 
	}
	
	@GetMapping("/users/{username}/todos")
	public List<Todo> retrieveTodos(@PathVariable String username) {
		return todoRepository.findByUsername(username); 
	}
	
	@GetMapping("/users/{username}/todos/{id}")
	public Todo retrieveTodo(@PathVariable String username,@PathVariable int id) {
		return todoRepository.findById(id).get();  
	}
	
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username,@PathVariable int id) {
	    todoRepository.deleteById(id); 
		return ResponseEntity.noContent().build(); 
	}
	
	@PutMapping("/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username,@PathVariable int id,@RequestBody Todo todo) {
		return todoRepository.save(todo); 
	}
	
	@PostMapping("/users/{username}/todos")
	public Todo createTodo(@PathVariable String username,@RequestBody Todo todo) {
		todo.setUsername(username); 
		todo.setId(null);
		return todoRepository.save(todo);
	}
	
}
