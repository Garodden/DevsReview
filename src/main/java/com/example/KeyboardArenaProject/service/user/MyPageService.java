package com.example.KeyboardArenaProject.service.user;

import com.example.KeyboardArenaProject.repository.MyPageRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageService(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

}