package com.fastcamp.boardserver.service;

import com.fastcamp.boardserver.dto.PostDTO;
import java.util.List;

public interface PostService {

    void register(String userId, PostDTO postDTO);

    List<PostDTO> getMyPosts(int userNo);

    void updatePosts(PostDTO postDTO);

    void deletePosts(String userId, int postId);

}
