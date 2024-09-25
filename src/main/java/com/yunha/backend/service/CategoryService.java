package com.yunha.backend.service;


import com.yunha.backend.dto.CategoryDTO;
import com.yunha.backend.entity.Category;
import com.yunha.backend.entity.User;
import com.yunha.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<CategoryDTO> getPublicCategoryList(Long userCode) {
        return categoryRepository.getPublicCategoryList(userCode);
    }


    @Transactional
    public String createMyCategory(CategoryDTO newCategoryDTO, Long userCode) {

        try{
            // dto -> entity
            Category newCategory = new Category(
                    newCategoryDTO.getCategoryCode(),
                    newCategoryDTO.getCategoryName(),
                    new User(userCode),
                    newCategoryDTO.isCategoryIsPrivate());

            categoryRepository.save(newCategory);
            return "카테고리 등록 성공";
        } catch (Exception e){
            throw new RuntimeException();
        }


    }


    @Transactional
    public String modifyCategory(CategoryDTO categoryDTO) {
        try{
            Category findCategory = categoryRepository.findByCategoryUserId(categoryDTO.getCategoryUserId());
            findCategory.setCategoryName(categoryDTO.getCategoryName());
            findCategory.setCategoryIsPrivate(categoryDTO.isCategoryIsPrivate());
            categoryRepository.save(findCategory);
            return "카테고리 수정 성공";
        } catch (Exception e){
            throw new RuntimeException();
        }
    }


    @Transactional
    public String removeMyCategory(Long categoryCode) {

        categoryRepository.deleteById(categoryCode);
        return "카테고리 삭제 성공";
    }


}
