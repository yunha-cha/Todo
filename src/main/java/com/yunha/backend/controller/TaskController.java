package com.yunha.backend.controller;


import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Task;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    // 해당 년/월 할 일 리스트 조회
    @GetMapping("/tasks")
    public ResponseEntity<?> getMyTasks(@RequestParam LocalDate calendarDate, @AuthenticationPrincipal CustomUserDetails user){
        try{
            return ResponseEntity.ok().body(taskService.getMyTaskList(calendarDate, user.getUserCode()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    //해당 날짜의 할 일 조회
    @GetMapping("/tasks/day")
    public ResponseEntity<?> getTaskOfDay(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size, @RequestParam LocalDate day, @AuthenticationPrincipal CustomUserDetails user){
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<Task> taskPage = taskService.getTaskOfDay(pageable, day, user.getUserCode());
            System.out.println(taskPage.get());
            return ResponseEntity.ok().body(taskPage);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/tasks")
    public ResponseEntity<String> createMyTask(@ModelAttribute TaskDayDTO newTaskDayDTO, @AuthenticationPrincipal CustomUserDetails user){
        try{
            System.out.println(newTaskDayDTO);
            return ResponseEntity.ok().body(taskService.createMyTask(newTaskDayDTO, user.getUserCode()));

        }catch (Exception e){
            System.out.println(e.getMessage());
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
