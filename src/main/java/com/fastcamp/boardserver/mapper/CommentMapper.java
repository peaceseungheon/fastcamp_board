package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {

    int register(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(@Param("commentNo") int commentNo);

}
