package com.example.KeyboardArenaProject.controller.freeBoard;


import com.example.KeyboardArenaProject.dto.arena.BoardDetailResponse;
import com.example.KeyboardArenaProject.dto.arena.ArenaResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardRecieveForm;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardWriteRequest;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.CommentService;
import com.example.KeyboardArenaProject.service.arena.ArenaService;
import com.example.KeyboardArenaProject.service.freeBoard.FreeBoardService;

import com.example.KeyboardArenaProject.service.user.UserService;
import com.example.KeyboardArenaProject.utils.user.UserTopBarInfoUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Controller
public class FreeBoardController {
    private final ArenaService arenaService;
    private final FreeBoardService freeBoardService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());

        // 전체 랭크전 아레나
        List<Board> arenaList = arenaService.findAllRankArena();
        List<ArenaResponse> rankArenas = arenaList.stream()
            .map(ArenaResponse::new)
            .toList();

        // 일반 아레나 상위 3개
        List<Board> top3ArenaList = arenaService.findTop3ArenaOrderByLikes();
        List<ArenaResponse> top3NormalArenas = top3ArenaList.stream()
            .map(ArenaResponse::new)
            .toList();

        List<String> top3BoardIds = top3ArenaList.stream()
            .map(Board::getBoardId)
            .toList();

        // 나머지 일반 아레나 생성일자 내림차순
        List<Board> otherNormalArenaList = arenaService.findNormalArenaOrderByCreatedDate(top3BoardIds);
        List<ArenaResponse> otherNormalArenas = otherNormalArenaList.stream()
            .map(ArenaResponse::new)
            .toList();

        model.addAttribute("rankArenas", rankArenas);
        model.addAttribute("top3Arenas", top3NormalArenas);
        model.addAttribute("otherNormalArenas", otherNormalArenas);

        return "index";
    }

    @PostMapping("/board")
    public String writeFreeBoard(@ModelAttribute FreeBoardRecieveForm recieveForm){
        FreeBoardWriteRequest request;
        Board board;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        log.warn("유저정보 id {}",user.getId());

        if(user == null){
            request = new FreeBoardWriteRequest("unknown",recieveForm.getTitle(),recieveForm.getContent(),recieveForm.getBoardRank());
        }else{
            request = new FreeBoardWriteRequest(user.getId(),recieveForm.getTitle(),recieveForm.getContent(),recieveForm.getBoardRank());
        }
        board = request.toEntity();
        freeBoardService.writeFreeBoard(board);
        log.info("board is created id = {}",board.getBoardId());
        return "redirect:/board";

    }
    @GetMapping("/newFreeboard")
    public String newFreeBoard(Model model){
        model.addAttribute("user",userService.getCurrentUserInfo());
//        if(boardId!=null){
//            model.addAttribute("board",freeBoardService.findByBoardId(boardId));
//
//        }else{
//            model.addAttribute("board",new Board());
//        }
        return "newFreeboard";
    }

    @PutMapping("/board/{board_id}")
    public String updateFreeBoard(@PathVariable String board_id,@ModelAttribute FreeBoardRecieveForm recieveForm){

        freeBoardService.updateBoard(recieveForm.getTitle(),recieveForm.getContent(), recieveForm.getBoardRank(), board_id);
        FreeBoardResponse response = freeBoardService.findByBoardId(board_id).toResponse();
        log.info("/board/{board_id} response {}",response);
        return "redirect:/board/"+board_id;
    }

    @DeleteMapping("/board/{board_id}")
    public void deleteFreeBoard(@PathVariable String board_id){
        freeBoardService.deleteBoard(board_id);
    }

    @GetMapping("/board")
    public String viewAllFreeBoard(Model model){
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        List<Board> freeboardList = freeBoardService.findAllSortedFreeBoard();
        model.addAttribute("freeboard",freeboardList);
        model.addAttribute("loginedUserRank",userService.getCurrentUserInfo().getUserRank());

        return "freeboardList";
    }

    @GetMapping("/board/{boardId}")
    public String viewOneFreeBoard(@PathVariable String boardId, Model model, HttpServletRequest request){
        //ip
        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        //현재 보드, 유저 정보
        Board curFreeBoardInfo = freeBoardService.findByBoardId(boardId);

        User curUser = userService.getCurrentUserInfo();

        User writer = userService.findById(curFreeBoardInfo.getId());

        //조회수 증가
        if(!freeBoardService.isContainsIpAndId(clientIp, boardId,userService.getCurrentUserId())){
            freeBoardService.saveIpAndId(clientIp, boardId,userService.getCurrentUserId());
            freeBoardService.plusView(boardId);
        }
/*
        model.addAttribute("writer",freeBoardService.findWriter(boardId));
        model.addAttribute("post",freeBoardService.findByBoardId(boardId));
        model.addAttribute("comments",commentService.findCommentsByBoardId(boardId));
        model.addAttribute("loginedId",userService.getCurrentUserInfo().getId());
        //유저탑바
        User user = userService.getCurrentUserInfo();
        UserTopBarInfo userTopBarInfo = new UserTopBarInfo(user);
        model.addAttribute("userTopBarInfo", userTopBarInfo);

        List<Integer> commentWritersRank = new ArrayList<>();
        for (int i = 0; i < commentService.findCommentsByBoardId(boardId).size(); i++) {
            commentWritersRank.add(userService.findById(commentService.findCommentsByBoardId(boardId).get(i).getId()).getUserRank());
        }
        model.addAttribute("commentWritersRanks",commentWritersRank);



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("loginedUserId",userService.getCurrentUserId());
        }else{
            model.addAttribute("loginedUserId","");
        }
*/

        //여기부터 내 코드

        BoardDetailResponse postDetails = BoardDetailResponse
                .builder()
                .user(curUser)
                .board(curFreeBoardInfo)
                .ifFirstTry(true)
                .comment(commentService.findCommentsByBoardId(boardId))
                .participates(curFreeBoardInfo.getViews())
                .writerNickname(writer.getNickname())
                .writerRank(writer.getUserRank())
                .build();

        postDetails.setCommentResponses(postDetails.getCommentResponses().stream()
                .peek(commentResponse->{
                    String writerId = commentResponse.getWriterId();
                    int writerRank = userService.findById(writerId).getUserRank();
                    commentResponse.setWriterRank(writerRank);
                }).collect(Collectors.toList()));

        model.addAttribute("post", postDetails);

        return "freeboardDetail";
    }

    @GetMapping("/board/{board_id}/update")
    public String updateOneFreeBoard(@PathVariable String board_id,Model model){
        model.addAttribute("user",userService.getCurrentUserInfo());
        model.addAttribute("post", freeBoardService.findByBoardId(board_id));
        return "updateFreeboard";
    }
}