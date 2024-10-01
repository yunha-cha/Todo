package com.yunha.backend.repository;

import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

//    @Query(value = "SELECT * \n" +
//            "FROM task\n" +
//            "WHERE ((YEAR(task_start_date) = ?1 AND MONTH(task_start_date) = ?2)\n" +
//            "   OR (YEAR(task_end_date) = ?1 AND MONTH(task_end_date) = ?2)) AND task_user_code = ?3", nativeQuery = true)
    @Query("SELECT new com.yunha.backend.dto.TaskDTO(t.taskCode, t.taskStartDate, t.taskEndDate) FROM Task t WHERE  ((YEAR(t.taskStartDate) = :year AND MONTH(t.taskStartDate) = :month) OR (YEAR(t.taskEndDate) = :year AND MONTH(t.taskEndDate) = :month)) AND t.taskUser.userCode = :userCode")
    List<TaskDTO> findAllByTaskUserCode(int year, int month, Long userCode);

//    @Query(value = "SELECT * FROM task WHERE ?1 >= task_start_date AND ?1 <= task_end_date AND task_user_code = ?2", nativeQuery = true)
//    @Query("SELECT new com.yunha.backend.dto.TaskDayDTO(t.taskCode, t.taskContent, t.taskStartDate, t.taskEndDate, t.taskState, t.taskCategory.categoryCode) FROM Task t WHERE :day >= t.taskStartDate AND :day <= t.taskEndDate AND t.taskUser.userCode = :userCode")
//    Page<TaskDayDTO> getTaskOfDay(Pageable pageable, LocalDate day, Long userCode);

    @Query("SELECT new com.yunha.backend.dto.TaskDayDTO(t.taskCode, t.taskContent, t.taskStartDate, t.taskEndDate, t.taskState, t.taskCategory.categoryCode) FROM Task t WHERE :day >= t.taskStartDate AND :day <= t.taskEndDate AND t.taskUser.userCode = :userCode")
    Page<Task> getTaskOfDay(Pageable pageable, LocalDate day, Long userCode);

}
