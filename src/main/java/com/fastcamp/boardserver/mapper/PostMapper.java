package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.PostDTO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {

    int register(PostDTO postDTO);

    List<PostDTO> selectMyPosts(@Param("userNo") int userNo);

    void updatePosts(PostDTO postDTO);

    void deletePosts(@Param("id") int postNo);

}
