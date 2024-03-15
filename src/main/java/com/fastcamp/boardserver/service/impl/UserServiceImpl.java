package com.fastcamp.boardserver.service.impl;

import com.fastcamp.boardserver.dto.UserDTO;
import com.fastcamp.boardserver.exception.DuplicateIdException;
import com.fastcamp.boardserver.mapper.UserProfileMapper;
import com.fastcamp.boardserver.service.UserService;
import com.fastcamp.boardserver.utils.SHA256Util;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserProfileMapper userProfileMapper;

    @Override
    public int register(UserDTO userProfile) {
        boolean duplicatedId = isDuplicatedId(userProfile.getUserId());

        if (duplicatedId) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }

        userProfile.setCreateTime(new Date());
        userProfile.setPassword(SHA256Util.encryptionSHA256(userProfile.getPassword()));
        int insertCount = userProfileMapper.register(userProfile);

        if (insertCount != 1) {
            log.error("insertMember ERROR!: {}", userProfile);
            throw new RuntimeException(
                "insertUser ERROR! 회원가입 메서드를 확인해주세요 \n" + "Params : " + userProfile);
        }
        return insertCount;
    }

    @Override
    public UserDTO login(String id, String password) {
        String encryptedPassword= SHA256Util.encryptionSHA256(password);
        return userProfileMapper.findByIdAndPassword(id, encryptedPassword);
    }

    @Override
    public boolean isDuplicatedId(String userId) {
        return userProfileMapper.idCheck(userId) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        UserDTO userProfile = userProfileMapper.getUserProfile(userId);
        if(userProfile == null){
            log.error("getUserInfo ERROR {}", userId);
            throw new RuntimeException("회원정보가 조회되지 않았습니다." + userId);
        }
        return userProfile;
    }

    @Override
    public void updatePassword(String userId, String beforePassword, String afterPassword) {

        String encryptedPassword = SHA256Util.encryptionSHA256(beforePassword);
        UserDTO userProfile = userProfileMapper.findByIdAndPassword(userId, encryptedPassword);

        if(userProfile == null){
            log.error("updatePassword ERROR!: {}", userId);
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        userProfile.setPassword(SHA256Util.encryptionSHA256(afterPassword));
        userProfileMapper.updatePassword(userProfile);

    }

    @Override
    public void deleteId(String id, String password) {
        String encryptedPassword = SHA256Util.encryptionSHA256(password);
        UserDTO userProfile = userProfileMapper.findByIdAndPassword(id, encryptedPassword);

        if(userProfile == null){
            log.error("deleted ERROR!: {}", id);
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        userProfileMapper.deleteUserProfile(id);
    }
}
