package com.yunha.backend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private Long categoryCode;
    private String categoryName;

    private String categoryUserId;
    private boolean categoryIsPrivate;


    public CategoryDTO(Long categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public CategoryDTO(Long categoryCode, String categoryName, boolean categoryIsPrivate) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.categoryIsPrivate = categoryIsPrivate;
    }
}


