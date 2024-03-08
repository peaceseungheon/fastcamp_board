package com.fastcamp.boardserver.mapper;

import com.fastcamp.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(@Param("userId") String userId);

    int insertUserProfile(@Param("id") String id, @Param("password") String password,
        @Param("nickname") String nickname, @Param("isAdmin") boolean isAdmin,
        @Param("createTime") String createTime, @Param("updateTime") String updateTime);

    int deleteUserProfile(@Param("id") String id);

    UserDTO findByIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    int idCheck(@Param("userId") String userId);

    int updatePassword(UserDTO user);

    int register(UserDTO userProfile);
}
