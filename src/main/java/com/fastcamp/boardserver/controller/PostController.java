package com.fastcamp.boardserver.controller;

import com.fastcamp.boardserver.aop.LoginCheck;
import com.fastcamp.boardserver.aop.LoginCheck.UserType;
import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.UserDTO;
import com.fastcamp.boardserver.dto.response.CommonResponse;
import com.fastcamp.boardserver.service.PostService;
import com.fastcamp.boardserver.service.UserService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> register(String userId,
        @RequestBody PostDTO postDTO) {
        postService.register(userId, postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS",
            "registerPost", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("/my-posts")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> getMyPosts(String userId) {
        UserDTO userInfo = userService.getUserInfo(userId);
        List<PostDTO> myPosts = postService.getMyPosts(userInfo.getId());
        CommonResponse<List<PostDTO>> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS",
            "myPostInfo", myPosts);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postNo}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> updatePosts(String userId,
        @PathVariable("postNo") int postNo, @RequestBody PostRequest postRequest) {
        PostDTO postDTO = PostDTO.builder()
            .id(postNo)
            .name(postRequest.getName())
            .contents(postRequest.getContents())
            .views(postRequest.getViews())
            .categoryNo(postRequest.getCategoryNo())
            .userNo(postRequest.getUserNo())
            .updateTime(new Date())
            .build();

        if(postDTO.getUserNo() == 0){
            UserDTO userInfo = userService.getUserInfo(userId);
            postDTO.setUserNo(userInfo.getId());
        }

        postService.updatePosts(postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS",
            "updatePosts", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("/{postNo}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<Void>> deletePosts(String userId,
        @PathVariable("postNo") int postNo,
        @RequestBody PostDeleteRequest request) {
        postService.deletePosts(userId, postNo);
        CommonResponse<Void> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS",
            "deletePosts", null);
        return ResponseEntity.ok(response);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class PostResponse {

        private List<PostDTO> postDTOS;
    }

    @Getter
    @Setter
    private static class PostRequest {

        private String name;
        private String contents;
        private int views;
        private int categoryNo;
        private int userNo;
        private Date updateTime;
    }

    @Getter
    @Setter
    private static class PostDeleteRequest {

        private int postNo;
        private int userNo;
    }

}
