package com.yunha.backend.service;

import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.Task;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.QueryTimeoutException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public String createMyTask(TaskDayDTO newTaskDayDTO, Long userCode) {
        try {       // 수정
            Task task = taskRepository.findById(newTaskDayDTO.getTaskCode()).orElseThrow();
            task.setTaskContent(newTaskDayDTO.getTaskContent());
            taskRepository.save(task);
            return "할 일 수정 성공";

        } catch (Exception e) {         // 등록
            Task newTask = new Task(
                    newTaskDayDTO.getTaskCode(),
                    newTaskDayDTO.getTaskContent(),
                    newTaskDayDTO.getTaskStartDate(),
                    newTaskDayDTO.getTaskEndDate(),
                    false,
                    new User(userCode),
                    new Category(newTaskDayDTO.getTaskCategoryName())
            );
            taskRepository.save(newTask);
            return "할 일 등록 성공";
        }
    }


    @Transactional
    public String removeMyTask(Long taskCode) {

        try {
            taskRepository.deleteById(taskCode);
            return "할 일 삭제 성공";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("삭제할 엔티티를 찾을 수 없습니다.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("다른 엔티티로 인해 삭제할 수 없습니다.");
        } catch (Exception e){
            throw new RuntimeException("알 수 없는 이유로 삭제할 수 없습니다.");
        }
    }



    public List<TaskDTO> getMyTaskList(LocalDate calendarDate, Long userCode) {

        try {
            return taskRepository.findAllByTaskUserCode(calendarDate.getYear(),
                    calendarDate.getMonthValue(),
                    userCode);
        } catch(EntityNotFoundException e){
            throw new EntityNotFoundException("특정 엔티티가 존재하지 않습니다.");
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
        } catch(EntityNotFoundException e){
            throw new EntityNotFoundException("특정 엔티티가 존재하지 않습니다.");
        } catch (NoResultException e) {
            throw new NoResultException("결과를 찾을 수 없습니다.");
        } catch (QueryTimeoutException e) {
            throw new QueryTimeoutException("시간 초과로 취소되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 이유로 실패했습니다.");
        }
    }
}
