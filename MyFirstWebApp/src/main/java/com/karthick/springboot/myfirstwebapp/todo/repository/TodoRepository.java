package com.karthick.springboot.myfirstwebapp.todo.repository;

import com.karthick.springboot.myfirstwebapp.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {


    List<Todo> findByUserName(String loggedinUserName);
}
