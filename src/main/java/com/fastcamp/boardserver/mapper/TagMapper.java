package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Param;

public interface TagMapper {

    int register(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deleteTag(@Param("tagNo") int tagNo);

    void createPostTag(int tagNo, int postNo);
}
