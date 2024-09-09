package com.yunha.backend.service;

import com.yunha.backend.dto.TaskDTO;
import com.yunha.backend.dto.TaskDayDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.Task;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Transactional
    public String createMyTask(TaskDTO newTaskDTO) {

        try{
            Task newTask = new Task(
                    newTaskDTO.getTaskCode(),
                    newTaskDTO.getTaskContent(),
                    newTaskDTO.getTaskStartDate(),
                    newTaskDTO.getTaskEndDate(),
                    false,       // newTaskDTO.isTaskState(),
                    new User(1L),       //userCode 받기
                    new Category(1L)    //categoryCode 받기
            );

            taskRepository.save(newTask);
            return "할 일 등록 성공";

        }catch (Exception e){
            return "할 일 등록 실패";
        }

    }


    @Transactional
    public String removeMyTask(Long taskCode) {

        try{
            taskRepository.deleteById(taskCode);
            return "할 일 삭제 성공";
        }catch (Exception e){
            return "할 일 삭제 실패";
        }
    }

    public List<TaskDTO> getMyTaskList(LocalDate calendarDate, Long userCode) {

        try {

            System.out.println("calendarDate : " + calendarDate);
            int year = calendarDate.getYear();
            int month = calendarDate.getMonthValue();

            List<Task> myTaskList = taskRepository.findAllByTaskUserCode(year, month, userCode);
            System.out.println("할일 myTasks : " + myTaskList);

            List<TaskDTO> taskDTOList = new ArrayList<>();
            //dto로 변환
            for(Task task: myTaskList){
                TaskDTO taskDTO = new TaskDTO(
                        task.getTaskCode(),
                        task.getTaskContent(),
                        task.getTaskStartDate(),
                        task.getTaskEndDate(),
                        task.isTaskState(),
                        task.getTaskUser().getUserId(), // 이게 필요한가?
                        task.getTaskCategory().getCategoryName() // 둘다 필요없는거같은데
                );
                taskDTOList.add(taskDTO);
            }

            return taskDTOList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<TaskDayDTO> getTaskOfDay(LocalDate day, Long userCode) {
        List<Task> entity = taskRepository.getTaskOfDay(day, userCode);
        List<TaskDayDTO> dtos = new ArrayList<>();
        try{
            for(Task t : entity){
                TaskDayDTO dto = new TaskDayDTO(
                        t.getTaskContent(),
                        t.getTaskStartDate(),
                        t.getTaskEndDate(),
                        t.isTaskState(),
                        t.getTaskCategory().getCategoryName()
                );
                dtos.add(dto);
            }
            return dtos;
        } catch (Exception e){
            return null;
        }
    }
}
