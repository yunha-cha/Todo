package com.yunha.backend.controller;


import com.yunha.backend.dto.ResponseDTO;
import com.yunha.backend.entity.Task;
import com.yunha.backend.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController     // 응답객체 반환하는 컨트롤러
@RequestMapping("todo")     // tomcat, spring 서버 2개 / 프론트에서 요청받음
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // task 일정 리스트 보기
    @GetMapping("/tasks") // GET : http method
    public ResponseEntity<?> getTasks(){

        try{

            List<Task> t = taskRepository.findAll();
            System.out.println("할일 목록 : " + t.get(0).getTaskContent());
            return ResponseEntity.ok().body(taskRepository.findAll());

        }catch (Exception e){

            return ResponseEntity.ok().body(new ResponseDTO("실패", "실패패"));
        }

    }

//    @PostMapping("/tasks")
//    public ResponseEntity<?> createTasks(){
//
//        taskRepository.save(new Task);
//
//    }

}
