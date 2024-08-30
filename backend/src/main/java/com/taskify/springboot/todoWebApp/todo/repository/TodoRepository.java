package com.taskify.springboot.todoWebApp.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskify.springboot.todoWebApp.todo.Todo;

public interface TodoRepository extends JpaRepository <Todo, Integer>{
	public List<Todo> findByUsername(String userName); 
}
