package com.fastcamp.boardserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDeletedId {

    private String userId;
    private String password;

}
