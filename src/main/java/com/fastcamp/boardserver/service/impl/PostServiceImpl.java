package com.fastcamp.boardserver.service.impl;

import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.UserDTO;
import com.fastcamp.boardserver.mapper.PostMapper;
import com.fastcamp.boardserver.mapper.UserProfileMapper;
import com.fastcamp.boardserver.service.PostService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;

    @Override
    public void register(String userId, PostDTO postDTO) {
        UserDTO userProfile = userProfileMapper.getUserProfile(userId);

        if(userProfile == null){
            log.error("post register ERROR {}", postDTO);
            throw new RuntimeException("작성자 정보가 존재하지 않습니다.");
        }

        postDTO.setUserNo(userProfile.getId());
        postDTO.setCreateTime(new Date());

        postMapper.register(postDTO);
    }

    @Override
    public List<PostDTO> getMyPosts(int userNo) {
        if(userNo == 0){
            log.error("getMyPosts ERROR {}", userNo);
            throw new RuntimeException("조회하는 회원 정보가 올바르지 않습니다." + userNo);
        }
        return postMapper.selectMyPosts(userNo);
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if(postDTO == null || postDTO.getId() == 0){
            log.error("updatePosts ERROR {}", postDTO);
            throw new RuntimeException("게시글 수정 메소드를 확인해주세요." + postDTO);
        }

        postMapper.updatePosts(postDTO);
    }

    @Override
    public void deletePosts(String userId, int postNo) {
        UserDTO userProfile = userProfileMapper.getUserProfile(userId);
        if(userProfile == null || postNo == 0){
            log.error("updatePosts ERROR userId: {}, postNo : {}", userId, postNo);
            throw new RuntimeException("게시글 삭제 메소드를 확인해주세요.");
        }
        postMapper.deletePosts(postNo);
    }
}
