package com.fastcamp.boardserver.dto.response;

import com.fastcamp.boardserver.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private UserDTO userDTO;
}
