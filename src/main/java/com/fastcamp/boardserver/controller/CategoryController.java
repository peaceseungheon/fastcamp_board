package com.fastcamp.boardserver.controller;

import com.fastcamp.boardserver.aop.LoginCheck;
import com.fastcamp.boardserver.aop.LoginCheck.UserType;
import com.fastcamp.boardserver.dto.CategoryDTO;
import com.fastcamp.boardserver.dto.CategoryDTO.SortStatus;
import com.fastcamp.boardserver.service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.ADMIN)
    public void registerCategory(String userId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(userId, categoryDTO);
    }

    @PatchMapping("/{categoryId}")
    @LoginCheck(type = UserType.ADMIN)
    public void updateCategory(String userId, @PathVariable("categoryId") int categoryId,
        @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(),
            SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    @LoginCheck(type = UserType.ADMIN)
    public void deleteCategory(String userId, @PathVariable("categoryId") int categoryId){
        categoryService.delete(categoryId);
    }

    @Getter
    @Setter
    private static class CategoryRequest {

        private int id;
        private String name;
    }

}
