package com.fastcamp.boardserver.controller;

import com.fastcamp.boardserver.dto.UserDTO;
import com.fastcamp.boardserver.dto.UserDTO.Status;
import com.fastcamp.boardserver.dto.request.LoginRequest;
import com.fastcamp.boardserver.dto.request.UserDeletedId;
import com.fastcamp.boardserver.dto.request.UserUpdatePasswordRequest;
import com.fastcamp.boardserver.dto.response.LoginResponse;
import com.fastcamp.boardserver.dto.response.UserInfoResponse;
import com.fastcamp.boardserver.service.UserService;
import com.fastcamp.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullDataBeforeRegister(userDTO)) {
            throw new RuntimeException("회원가입 정보를 확인해주세요.");
        }
        userService.register(userDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = request.getUserId();
        String password = request.getPassword();
        UserDTO userInfo = userService.login(id, password);

        if (userInfo == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND) ;
        }

        LoginResponse response = LoginResponse.success(userInfo);

        if (userInfo.getStatus().equals(Status.ADMIN)) {
            SessionUtil.setLoginAdminId(session, userInfo.getUserId());
        } else {
            SessionUtil.setLoginMemberId(session, userInfo.getUserId());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String userId = SessionUtil.getLoginMemberId(session);
        if (userId == null) {
            userId = SessionUtil.getLoginAdminId(session);
        }
        UserDTO userInfo = userService.getUserInfo(userId);
        return new UserInfoResponse(userInfo);
    }

    @PutMapping("/logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("/password")
    public ResponseEntity<LoginResponse> updateUserPassword(
        @RequestBody UserUpdatePasswordRequest request, HttpSession session) {

        ResponseEntity<LoginResponse> responseEntity;

        String userId = SessionUtil.getLoginMemberId(session);
        String beforePassword = request.getBeforePassword();
        String afterPassword = request.getAfterPassword();

        try {
            userService.updatePassword(userId, beforePassword, afterPassword);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeletedId deletedId,
        HttpSession session) {

        String userId = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(userId, deletedId.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException e){
            log.error("deleteId 실패");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
