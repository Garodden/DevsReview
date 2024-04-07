package com.example.KeyboardArenaProject.controller;

import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.board.CommonBoardService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOPAspect {
    @Autowired
    private CommonBoardService commonBoardService;
    @Around("execution(* com.example.KeyboardArenaProject.controller.*Controller.show*(..))")
    public Object checkMyRankAndSetBoardRank(ProceedingJoinPoint pjp) throws Throwable{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            User loggedinUser = (User)authentication.getPrincipal();
            Integer userRank = loggedinUser.getUserRank();
            commonBoardService.getMyBoards(loggedinUser.getId()).stream()
                    .filter(x->x.getBoardRank()>userRank)
                    .forEach(x->commonBoardService.setBoardRankMax(x,userRank));
        }

        return pjp.proceed();
    }
}
