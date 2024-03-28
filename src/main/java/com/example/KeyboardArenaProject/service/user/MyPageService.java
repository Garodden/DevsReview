package com.example.KeyboardArenaProject.service.user;

import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.MyPageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageService(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

    public MyPageInformation getUserInfo(String userId) {
        Optional<User> userOptional = myPageRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new MyPageInformation(user);
//            return MyPageInformation.builder()
//                    .userId(user.getUserId())
//                    .nickname(user.getNickname())
//                    .userRank(user.getUserRank())
//                    .point(user.getPoint())
//                    .email(user.getEmail())
//                    .build();
        } else {
            return null;
        }
    }

}