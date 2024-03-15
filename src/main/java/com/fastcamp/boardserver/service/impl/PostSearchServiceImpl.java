package com.fastcamp.boardserver.service.impl;

import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.request.PostSearchRequest;
import com.fastcamp.boardserver.mapper.PostSearchMapper;
import com.fastcamp.boardserver.service.PostSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    @Override
    //@Cacheable(value = "getPosts", key = "'getPosts' + #request.getName() + #request.getCategoryNo()")
    public List<PostDTO> getPosts(PostSearchRequest request) {
        List<PostDTO> list = null;

        try {
            list = postSearchMapper.selectPosts(request);
        }catch (RuntimeException e){
            log.error("selectPosts 메서드 실패", e.getMessage());
        }
        return list;
    }
}
