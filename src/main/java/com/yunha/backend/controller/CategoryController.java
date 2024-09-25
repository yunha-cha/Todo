package com.yunha.backend.controller;


import com.yunha.backend.dto.CategoryDTO;
import com.yunha.backend.entity.User;
import com.yunha.backend.security.dto.CustomUserDetails;
import com.yunha.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    // 자신 카테고리 전체 + 다른 사용자 isPrivate = false 카테고리
    @GetMapping("/category")
    public ResponseEntity<?> getPublicCategory(@AuthenticationPrincipal CustomUserDetails user){

        try{
            return ResponseEntity.ok().body(categoryService.getPublicCategoryList(user.getUserCode()));

        }catch (Exception e){ // 스프링 데이터 jpa에서 select할 때 발생할 수 있는 모든 에러 참조/ 에러 커스텀하기
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    // 내 카테고리 추가 - 이름, 공개/비공개 입력
    @PostMapping("/category")
    public ResponseEntity<?> createMyCategory(@RequestBody CategoryDTO newCategoryDTO, @AuthenticationPrincipal CustomUserDetails user){
        try {
            return ResponseEntity.ok().body(categoryService.createMyCategory(newCategoryDTO, user.getUserCode()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/category")
    public ResponseEntity<?> modifyCategory(@RequestBody CategoryDTO categoryDTO){

        return ResponseEntity.ok().body(categoryService.modifyCategory(categoryDTO));
    }



    @DeleteMapping("/category/{categoryCode}")
    public ResponseEntity<?> removeMyCategory(@PathVariable Long categoryCode){

        try {

            return ResponseEntity.ok().body(categoryService.removeMyCategory(categoryCode));

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카테고리 삭제 에러");
        }

    }


}
