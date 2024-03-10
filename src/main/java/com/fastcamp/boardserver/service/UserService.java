package com.fastcamp.boardserver.service;

import com.fastcamp.boardserver.dto.UserDTO;

public interface UserService {

    int register(UserDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String userId);

    UserDTO getUserInfo(String userId);

    void updatePassword(String userId, String beforePassword, String afterPassword);

    void deleteId(String id, String password);

}
