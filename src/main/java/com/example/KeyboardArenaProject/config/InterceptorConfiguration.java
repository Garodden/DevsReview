package com.example.KeyboardArenaProject.config;

import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.interceptor.Interceptor;
import com.example.KeyboardArenaProject.service.freeBoard.FreeBoardService;
import com.example.KeyboardArenaProject.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor());
//                .addPathPatterns("/board/{board_id}");
    }
}
