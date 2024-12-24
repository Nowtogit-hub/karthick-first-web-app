package com.karthick.springboot.myfirstwebapp.todo.controller;

import com.karthick.springboot.myfirstwebapp.todo.Todo;
import com.karthick.springboot.myfirstwebapp.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

//@Controller
@SessionAttributes("name")
public class TodoController {

    private TodoService todoService ;

    public TodoController(TodoService todoService){

        this.todoService = todoService;

    }

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap modelMap){
        String username = (String) modelMap.get("name");
        List<Todo> todos = todoService.findByUserName(username);
        modelMap.addAttribute("todos", todos);
        return "listTodos";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap modelMap){
        String username = (String)modelMap.get("name");
        Todo todo = new Todo(0, username, "hi bro",
                LocalDate.now().plusYears(1),false);
        modelMap.put("todo",todo);
        return "todo";
    }

    @RequestMapping( value ="add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap modelMap, @Valid Todo todo,
                          BindingResult result){

        if(result.hasErrors()){
             return "todo";
        }
        String username = (String)modelMap.get("name");
        todoService.addTodo(username, todo.getDescription(),
                todo.getTargetDate(),false);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id){
        todoService.deleteTodo(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String updateTodo(@RequestParam int id, ModelMap modelMap){
        Todo todo = todoService.findById(id);
        modelMap.put("todo", todo);
        return "todo";
    }

    @RequestMapping( value ="update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap modelMap,@RequestParam int id , @Valid Todo todo,
                             BindingResult result){

        if(result.hasErrors()){
            return "todo";
        }
        String username = (String)modelMap.get("name");
        todoService.updateTodo(id, username, todo.getDescription(),
                todo.getTargetDate(),false);
        return "redirect:list-todos";
    }
    private String getLoggedinUserName(){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return authentication.getName();
    }


}