package com.erizeez.genshinimpactdamagecalculatorserver.service;

import com.erizeez.genshinimpactdamagecalculatorserver.entity.User;
import com.erizeez.genshinimpactdamagecalculatorserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User selectUserByUID(Integer uID) {
        return userRepository.selectUserByUID(uID);
    }

    public User selectUserByUserName(String userName) {
        return userRepository.selectUserByUserName(userName);
    }

    public User selectUserByNickName(String nickName) {
        return userRepository.selectUserByNickName(nickName);
    }

    public void insertUser(User user) {
        userRepository.insertUser(user.getUserName(),
                user.getPassWord(), user.getNickName());
    }

    public void updateUser(User user) {
        userRepository.updateUser(user.getuID(), user.getUserName(),
                user.getPassWord(), user.getNickName());
    }

    public void deleteUser(Integer uID) {
        userRepository.deleteUser(uID);
    }
}
