package com.fastcamp.boardserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class UserUpdatePasswordRequest {

    @NonNull
    private String beforePassword;
    @NonNull
    private String afterPassword;

}
