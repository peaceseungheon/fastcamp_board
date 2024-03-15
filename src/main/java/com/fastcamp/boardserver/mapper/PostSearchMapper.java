package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.request.PostSearchRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostSearchMapper {

    List<PostDTO> selectPosts(PostSearchRequest request);

}
