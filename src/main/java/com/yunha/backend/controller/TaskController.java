package com.yunha.backend.controller;


import com.yunha.backend.dto.ResponseDTO;
import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.Task;
import com.yunha.backend.repository.TaskRepository;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController     // 응답객체 반환하는 컨트롤러
@RequestMapping("todo")     // tomcat, spring 서버 2개 / 프론트에서 요청받음
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    // task 내 할 일 리스트 보기
    // 남의 할 일은 어케 볼까
    // 카테고리별 할 일 필터링은 프론트에서?

    // 현재 날짜
    @GetMapping("/tasks")
    public ResponseEntity<?> getMyTasks(@RequestParam LocalDate calendarDate, @AuthenticationPrincipal CustomUserDetails user){        // userCode 받아야함

        try{
            System.out.println("calendarDate = " + calendarDate);
            List<TaskDTO> myTaskList = taskService.getMyTaskList(calendarDate, user.getUserCode());

            return ResponseEntity.ok().body(new ResponseDTO("내 할일 조회 성공", myTaskList));

        }catch (Exception e){

            return ResponseEntity.ok().body(new ResponseDTO("실패", "내 할일 조회 실패"));
        }

    }
    @GetMapping("/tasks/day")
    public ResponseEntity<?> getTaskOfDay(@RequestParam LocalDate day, @AuthenticationPrincipal CustomUserDetails user){
        try{
            System.out.println("day = " + day);
            List<TaskDayDTO> myTaskList = taskService.getTaskOfDay(day, user.getUserCode());
            if(myTaskList == null){
                throw new Exception("for문 error");
            }
            return ResponseEntity.ok().body(new ResponseDTO("success",myTaskList));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("fail");
        }
    }


    // 할 일 등록할 때 내용, 시작날짜, 끝날짜, 카테고리도 등록해준다.
    @PostMapping("/tasks")
    public ResponseEntity<String> createMyTask(@RequestBody TaskDTO newTaskDTO){

        try{
            String result = taskService.createMyTask(newTaskDTO);
            return ResponseEntity.ok().body(result);

        }catch (Exception e){

            return ResponseEntity.ok().body("할 일 등록 실패");

        }


    }


    // 할 일 수정 - 수정 버튼(영역에 hover하면 수정 버튼 보이게)
    @PutMapping("/tasks")
    public ResponseEntity<?> modifyMyTask(@RequestBody TaskDTO newTaskDTO){

        return ResponseEntity.ok().body("수정 성공");
    }


    // 할 일 삭제 - 삭제 버튼 누르면 삭제
    @DeleteMapping("/tasks/{taskCode}")
    public ResponseEntity<?> removeMyTask(@PathVariable Long taskCode){

        String result = taskService.removeMyTask(taskCode);
        return ResponseEntity.ok().body(result);

    }



}
