package com.karthick.springboot.myfirstwebapp.todo.service;

import com.karthick.springboot.myfirstwebapp.todo.Todo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<>();
    private static int id=0;

    
    public void addTodo(String username, String description,
                        LocalDate localDate, boolean done){
        todos.add(new Todo(++id, username, description, localDate, done));

    }

    public void deleteTodo(int id){
        todos.removeIf(todo -> todo.getId()==id);
    }

    public Todo  findById(int id){
        return todos.stream()
                .filter(todo -> todo.getId()==id)
                .findFirst().get();
    }

    public void updateTodo(int id, String username, String description,
                           LocalDate localDate, boolean done){

        Todo utodo = todos.stream()
                .filter(todo -> todo.getId()==id)
                .findFirst().get();
        utodo.setDescription(description);
        utodo.setUserName(username);
        utodo.setDone(done);
        utodo.setTargetDate(localDate);
    }
    public List<Todo> findByUserName(String userName){
        return todos.stream()
                .filter(n -> n.getUserName().equals(userName))
                .collect(Collectors.toList());
    }


}