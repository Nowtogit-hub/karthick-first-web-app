package com.karthick.springboot.myfirstwebapp.todo.controller;

import com.karthick.springboot.myfirstwebapp.todo.Todo;
import com.karthick.springboot.myfirstwebapp.todo.repository.TodoRepository;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TodoControllerJpa {

    private final TodoRepository todoRepository;

    public TodoControllerJpa(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap modelMap){
        String username = (String) modelMap.get("name");
        List<Todo> todos = todoRepository.findByUserName(getLoggedinUserName());
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
        todo.setUserName(getLoggedinUserName());
        todoRepository.save(todo);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id){
        todoRepository.deleteById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String updateTodo(@RequestParam int id, ModelMap modelMap){
        Todo todo = todoRepository.findById(id).get();
        modelMap.put("todo", todo);
        return "todo";
    }

    @RequestMapping( value ="update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap modelMap,@RequestParam int id , @Valid Todo todo,
                             BindingResult result){

        if(result.hasErrors()){
            return "todo";
        }
        todo.setUserName(getLoggedinUserName());
        todoRepository.save(todo);
        return "redirect:list-todos";
    }
    private String getLoggedinUserName(){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return authentication.getName();
    }

}
