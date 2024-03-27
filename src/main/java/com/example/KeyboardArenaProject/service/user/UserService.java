package com.example.KeyboardArenaProject.service.user;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.entity.UserEntity;
import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UserEntity saveg(AddUserRequest dto){
        return repository.save(new UserEntity("aaaabbb","1111","1111","test",1,0,"@","d","d",true));
    }
}
