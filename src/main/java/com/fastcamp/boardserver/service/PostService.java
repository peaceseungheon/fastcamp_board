package com.fastcamp.boardserver.service;

import com.fastcamp.boardserver.dto.CommentDTO;
import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.TagDTO;
import java.util.List;

public interface PostService {

    void register(String userId, PostDTO postDTO);

    List<PostDTO> getMyPosts(int userNo);

    void updatePosts(PostDTO postDTO);

    void deletePosts(String userId, int postId);

    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(int userNo, int commentNo);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deleteTag(int userNo, int tagNo);
}
