package com.fastcamp.boardserver.service.impl;

import com.fastcamp.boardserver.dto.CategoryDTO;
import com.fastcamp.boardserver.mapper.CategoryMapper;
import com.fastcamp.boardserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public void register(String userId, CategoryDTO categoryDTO) {

        if (userId == null) {
            log.error("register ERROR! {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메소드를 확인해주세요." + categoryDTO);
        }

        categoryMapper.register(categoryDTO);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            log.error("update ERROR! categoryDTO 정보가 null입니다.");
            throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메소드를 확인해주세요.");
        }

        categoryMapper.updateCategory(categoryDTO);
    }

    @Override
    public void delete(int categoryId) {
        if (categoryId == 0) {
            log.error("delete ERROR! {}", categoryId);
            throw new RuntimeException("register ERROR! 게시글 카테고리 삭제 메소드를 확인해주세요." + categoryId);
        }

        categoryMapper.deleteCategory(categoryId);
    }
}
