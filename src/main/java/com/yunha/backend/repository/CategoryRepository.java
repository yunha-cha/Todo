package com.yunha.backend.repository;

import com.yunha.backend.dto.CategoryDTO;
import com.yunha.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query(value = "SELECT * FROM category WHERE category_user_code = ?1 " +
//            "OR (category_user_code != ?1 AND category_is_private = 0)", nativeQuery = true)
    @Query("SELECT new com.yunha.backend.dto.CategoryDTO(c.categoryCode, c.categoryName) FROM Category c WHERE c.categoryUser.userCode = :userCode OR (c.categoryUser.userCode != :userCode AND c.categoryIsPrivate = false)")
    List<CategoryDTO> getPublicCategoryList(Long userCode);

    @Query("SELECT new com.yunha.backend.dto.CategoryDTO(c.categoryCode, c.categoryName, c.categoryIsPrivate) FROM Category c WHERE c.categoryUser.userId = :categoryUserId")
    Category findByCategoryUserId(String categoryUserId);

}
