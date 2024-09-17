package com.yunha.backend.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskDTO {

    private Long taskCode;
    private LocalDate taskStartDate;
    private LocalDate taskEndDate;

    public TaskDTO(Long taskCode, LocalDate taskStartDate, LocalDate taskEndDate) {
        this.taskCode = taskCode;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
    }
}
