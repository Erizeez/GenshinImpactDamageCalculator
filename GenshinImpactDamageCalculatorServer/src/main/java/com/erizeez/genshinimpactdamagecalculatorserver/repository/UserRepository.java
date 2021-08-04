package com.erizeez.genshinimpactdamagecalculatorserver.repository;

import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User selectUserByUID(@Param("uID") Integer uID);

    User selectUserByUserName(@Param("userName") String userName);

    User selectUserByNickName(@Param("nickName") String nickName);

    void insertUser(@Param("userName") String userName,
                    @Param("passWord") String passWord,
                    @Param("nickName") String nickName);

    void updateUser(@Param("uID") Integer uID,
                    @Param("userName") String userName,
                    @Param("passWord") String passWord,
                    @Param("nickName") String nickName);

    void deleteUser(@Param("uID") Integer uID);
}
