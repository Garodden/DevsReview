package com.example.KeyboardArenaProject.controller.freeBoard;

import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardRecieveJson;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardWriteRequest;
import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.UserEntity;
import com.example.KeyboardArenaProject.service.freeBoard.FreeBoardService;
import com.example.KeyboardArenaProject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final UserService userService;

    @GetMapping("/")
    public String indexPage(Model model){
        return "index";
    }

//    @PostMapping("/board")
//    public ResponseEntity<String> writeFreeBoard(@RequestBody FreeBoardRecieveJson recieveJson, @AuthenticationPrincipal UserEntity user){
//        FreeBoardWriteRequest request;
//        Board board;
//        if(user!=null){
//            request = new FreeBoardWriteRequest(user.id,recieveJson.getTitle(),recieveJson.getContent(), recieveJson.getBoard_rank());
//            board = request.toEntity();
//            freeBoardService.writeFreeBoard(board);
//        }else{
//            request = new FreeBoardWriteRequest("unknown", recieveJson.getTitle(), recieveJson.getContent(), recieveJson.getBoard_rank());
//            board = request.toEntity();
//            freeBoardService.writeFreeBoard(board);
//        }
//        return ResponseEntity.ok(board.getBoardId());
//
//    }

//    @PutMapping("/board/{board_id}")
//    public ResponseEntity<FreeBoardRecieveJson> updateFreeBoard(@RequestBody FreeBoardRecieveJson recieveJson,Principal principal){
//        FreeBoardWriteRequest request = new FreeBoardWriteRequest(recieveJson.getTitle(), recieveJson.getContent(), recieveJson.getBoardRank(),principal,freeBoardService);
//        return ResponseEntity.ok(recieveJson);
//    }

    @GetMapping("/board")
    public String viewAllFreeBoard(Model model){
        List<Board> freeboard = freeBoardService.findAllSortedFreeBoard();
        model.addAttribute("freeboard",freeboard);
        return "index";
    }

    @GetMapping("/board/{board_id}")
    public String viewOneFreeBoard(@PathVariable String board_id,Model model){
        model.addAttribute("writer",freeBoardService.findWriter(board_id));
        model.addAttribute("post",freeBoardService.findByBoardId(board_id));
        return "freeboardDetail";
    }
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/board";
//    }

//    @ResponseBody
//    @PostMapping("/user")
//    public UserEntity signup(@RequestBody AddUserRequest request){   //폼데이터를 받을 때는 @RequestBody를 안 쓴다.
//        UserEntity userEntity = userService.saveg(request);
//        return userEntity;
//    }
//
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//    @GetMapping("/signup")
//    public String signup(){
//        return "signup";
//    }
//


}