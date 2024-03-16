package com.fastcamp.boardserver.service;

import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.request.PostSearchRequest;
import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchRequest request);

    List<PostDTO> searchByTagName(String tagName);
}
