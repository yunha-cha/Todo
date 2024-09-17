package com.yunha.backend.service;

import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.Task;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.TaskRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.QueryTimeoutException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


//    @Transactional
//    public String createMyTask(TaskDTO newTaskDTO, Long userCode) {
//        try {
//            Task task = taskRepository.findById(newTaskDTO.getTaskCode()).orElseThrow();
//            task.setTaskContent(newTaskDTO.getTaskContent());
//            taskRepository.save(task);
//            return "성공";
//
//        } catch (Exception e) {
//            Task newTask = new Task(
//                    newTaskDTO.getTaskCode(),
//                    newTaskDTO.getTaskContent(),
//                    newTaskDTO.getTaskStartDate(),
//                    newTaskDTO.getTaskEndDate(),
//                    false,
//                    new User(userCode),
//                    new Category(1L)
//            );
//
//            taskRepository.save(newTask);
//            return "할 일 성공";
//        }
//    }


    @Transactional
    public String removeMyTask(Long taskCode) {

        try {
            taskRepository.deleteById(taskCode);
            return "할 일 삭제 성공";
        } catch (Exception e) {
            return "할 일 삭제 실패";
        }
    }

    public List<TaskDTO> getMyTaskList(LocalDate calendarDate, Long userCode) {

        try {

            return taskRepository.findAllByTaskUserCode(calendarDate.getYear(),
                    calendarDate.getMonthValue(),
                    userCode);
        } catch (NoResultException e) {
            throw new NoResultException("결과를 찾을 수 없습니다.");
        } catch (QueryTimeoutException e) {
            throw new QueryTimeoutException("시간 초과로 취소되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 이유로 실패했습니다.");
        }
    }

    public List<TaskDayDTO> getTaskOfDay(LocalDate day, Long userCode) {
        try {
            return taskRepository.getTaskOfDay(day, userCode);
        } catch (NoResultException e) {
            throw new NoResultException("결과를 찾을 수 없습니다.");
        } catch (QueryTimeoutException e) {
            throw new QueryTimeoutException("시간 초과로 취소되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 이유로 실패했습니다.");
        }
    }
}
