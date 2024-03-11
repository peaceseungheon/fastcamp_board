package com.fastcamp.boardserver.service;

import com.fastcamp.boardserver.dto.CategoryDTO;

public interface CategoryService {

    void register(String userId, CategoryDTO categoryDTO);
    void update(CategoryDTO categoryDTO);

    void delete(int categoryId);

}
