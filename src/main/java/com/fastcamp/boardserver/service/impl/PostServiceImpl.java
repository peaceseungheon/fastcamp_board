package com.fastcamp.boardserver.service.impl;

import com.fastcamp.boardserver.dto.CommentDTO;
import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.TagDTO;
import com.fastcamp.boardserver.dto.UserDTO;
import com.fastcamp.boardserver.mapper.CommentMapper;
import com.fastcamp.boardserver.mapper.PostMapper;
import com.fastcamp.boardserver.mapper.TagMapper;
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
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

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

        if(postDTO.getTagDTOS() == null){
            return;
        }

        int postNo = postDTO.getId();

        for (TagDTO tagDTO : postDTO.getTagDTOS()) {
            tagMapper.register(tagDTO);
            tagMapper.createPostTag(tagDTO.getTagNo(), postNo);
        }
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

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if(commentDTO.getPostNo() == 0){
            log.error("registerComment ERROR: {}", commentDTO);
            throw new RuntimeException("게시글 정보에 오류가 있습니다." + commentDTO);
        }

        commentMapper.register(commentDTO);
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO == null){
            log.error("updateComment ERROR: dto class is null");
            throw new RuntimeException("댓글 수정 요청정보에 오류가 있습니다.");
        }

        commentMapper.updateComment(commentDTO);
    }

    @Override
    public void deleteComment(int userNo, int commentNo) {
        if(userNo == 0 || commentNo == 0){
            log.error("deleteComment ERROR userNo: {}, commentNo: {}", userNo, commentNo);
            throw new RuntimeException("댓글 삭제 요청정보에 오류가 있습니다.");
        }
        commentMapper.deleteComment(commentNo);
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO == null){
            log.error("registerTag ERROR dto class is null");
            throw new RuntimeException("태그 저장 정보에 오류가 있습니다.");
        }

        if(tagDTO.getName().isEmpty()){
            log.error("registerTag ERROR name: {}", tagDTO.getName());
            throw new RuntimeException("태그 저장 정보에 오류가 있습니다.");
        }

        tagMapper.register(tagDTO);
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO == null){
            log.error("updateTag ERROR dto class is null");
            throw new RuntimeException("태그 수정 정보에 오류가 있습니다.");
        }

        if(tagDTO.getName().isEmpty()){
            log.error("updateTag ERROR name: {}, postNo: {}", tagDTO.getName(), tagDTO.getPostNo());
            throw new RuntimeException("태그 수정 정보에 오류가 있습니다.");
        }

        tagMapper.updateTag(tagDTO);
    }

    @Override
    public void deleteTag(int userNo, int tagNo) {
        if(userNo == 0 || tagNo == 0){
            log.error("deleteTag ERROR userNo: {}, tagNo: {}", userNo, tagNo);
            throw new RuntimeException("태그 삭제 정보에 오류가 있습니다.");
        }
        tagMapper.deleteTag(tagNo);
    }

}
