package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {

    int register(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(@Param("id") int categoryId);

}
