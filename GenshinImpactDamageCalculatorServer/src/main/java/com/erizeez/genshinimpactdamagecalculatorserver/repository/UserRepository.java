package com.erizeez.genshinimpactdamagecalculatorserver.repository;

import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository {
    User selectUserByUID(@Param("uID") Integer uID);

    User selectUserByUserName(@Param("userName") String userName);

    User selectUserByNickName(@Param("nickName") String nickName);

    void insertUser(@Param("userName") String userName,
                    @Param("passWord") String passWord,
                    @Param("nickName") String nickName,
                    @Param("loginTime") Date loginTime);

    void updateUser(@Param("uID") Integer uID,
                    @Param("userName") String userName,
                    @Param("passWord") String passWord,
                    @Param("nickName") String nickName,
                    @Param("loginTime") Date loginTime);

    void deleteUser(@Param("uID") Integer uID);
}
