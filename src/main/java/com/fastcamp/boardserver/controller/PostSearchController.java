package com.fastcamp.boardserver.controller;

import com.fastcamp.boardserver.aop.LoginCheck;
import com.fastcamp.boardserver.aop.LoginCheck.UserType;
import com.fastcamp.boardserver.dto.PostDTO;
import com.fastcamp.boardserver.dto.request.PostSearchRequest;
import com.fastcamp.boardserver.dto.response.CommonResponse;
import com.fastcamp.boardserver.service.PostSearchService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @PostMapping
    public ResponseEntity<CommonResponse<PostSearchResponse>> search(
        @RequestBody PostSearchRequest request) {
        List<PostDTO> posts = postSearchService.getPosts(request);
        PostSearchResponse searchResponse = new PostSearchResponse(posts);
        CommonResponse<PostSearchResponse> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS",
            "getPosts", searchResponse);
        return ResponseEntity.ok(response);
    }


    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {

        private List<PostDTO> postDTOList;
    }

}
