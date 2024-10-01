package com.yunha.backend.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
public class TaskDayDTO {
    private Long taskCode;
    private String taskContent;
    private LocalDate taskStartDate;
    private LocalDate taskEndDate;
    private boolean taskState;
    private Long taskCategoryCode;
}
