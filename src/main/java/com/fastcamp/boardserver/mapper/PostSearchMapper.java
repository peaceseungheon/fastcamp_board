package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.request.PostSearchRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostSearchMapper {

    List<PostDTO> selectPosts(PostSearchRequest request);
    List<PostDTO> selectByTagName(@Param("tagName") String tagName);

}
