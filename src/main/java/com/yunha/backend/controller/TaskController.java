package com.yunha.backend.controller;


import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping("todo")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    // task 할 일 리스트 보기
    @GetMapping("/tasks")
    public ResponseEntity<?> getMyTasks(@RequestParam LocalDate calendarDate, @AuthenticationPrincipal CustomUserDetails user){
        try{
            return ResponseEntity.ok().body(taskService.getMyTaskList(calendarDate, user.getUserCode()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/tasks/day")
    public ResponseEntity<?> getTaskOfDay(@RequestParam LocalDate day, @AuthenticationPrincipal CustomUserDetails user){
        try{
            return ResponseEntity.ok().body(taskService.getTaskOfDay(day, user.getUserCode()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/tasks")
    public ResponseEntity<String> createMyTask(@ModelAttribute TaskDayDTO newTaskDayDTO, @AuthenticationPrincipal CustomUserDetails user){
        try{
            return ResponseEntity.ok().body(taskService.createMyTask(newTaskDayDTO, user.getUserCode()));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


    @PutMapping("/tasks")
    public ResponseEntity<?> modifyMyTask(@RequestBody TaskDTO newTaskDTO){

        return ResponseEntity.ok().body("수정 성공");
    }


    @DeleteMapping("/tasks/{taskCode}")
    public ResponseEntity<?> removeMyTask(@PathVariable Long taskCode){

        return ResponseEntity.ok().body(taskService.removeMyTask(taskCode));
    }

}
