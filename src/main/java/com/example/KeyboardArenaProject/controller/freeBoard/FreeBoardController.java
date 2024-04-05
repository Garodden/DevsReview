package com.example.KeyboardArenaProject.controller.freeBoard;


import com.example.KeyboardArenaProject.dto.CommentResponse;
import com.example.KeyboardArenaProject.dto.arena.BoardDetailResponse;
import com.example.KeyboardArenaProject.dto.arena.ArenaResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardRecieveForm;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardWriteRequest;
import com.example.KeyboardArenaProject.dto.user.AnonymousUser;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.CommentService;
import com.example.KeyboardArenaProject.service.board.CommonBoardService;

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

import java.util.List;

import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Controller
public class FreeBoardController {
    private final CommonBoardService commonBoardService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());

        // 전체 랭크전 아레나
        List<Board> arenaList = commonBoardService.findAllRankArena();
        List<ArenaResponse> rankArenas = arenaList.stream()
            .map(ArenaResponse::new)
            .toList();

        // 일반 아레나 상위 3개
        List<Board> top3ArenaList = commonBoardService.findTop3ArenaOrderByLikes();
        List<ArenaResponse> top3NormalArenas = top3ArenaList.stream()
            .map(ArenaResponse::new)
            .toList();

        List<String> top3BoardIds = top3ArenaList.stream()
            .map(Board::getBoardId)
            .toList();

        // 나머지 일반 아레나 생성일자 내림차순
        List<Board> otherNormalArenaList = commonBoardService.findNormalArenaOrderByCreatedDate(top3BoardIds);
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

        if(authentication.getPrincipal().equals("anonymousUser")){
            request = new FreeBoardWriteRequest("",recieveForm.getTitle(),recieveForm.getContent(),recieveForm.getBoardRank());
        }else{
            request = new FreeBoardWriteRequest(user.getId(),recieveForm.getTitle(),recieveForm.getContent(),recieveForm.getBoardRank());
        }
        board = request.toEntity();
        commonBoardService.writeFreeBoard(board);
        log.info("board is created id = {}",board.getBoardId());
        return "redirect:/board";

    }
    @GetMapping("/newFreeboard")
    public String newFreeBoard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            model.addAttribute("user",userService.getCurrentUserInfo());
        }else{
            model.addAttribute("user",new AnonymousUser());
        }
//        if(boardId!=null){
//            model.addAttribute("board",freeBoardService.findByBoardId(boardId));
//
//        }else{
//            model.addAttribute("board",new Board());
//        }
        if(!userService.getCurrentUserInfo().getIsActive()){
            return "signoutUserError";
        }
        return "newFreeboard";
    }

    @PutMapping("/board/{board_id}")
    public String updateFreeBoard(@PathVariable String board_id,@ModelAttribute FreeBoardRecieveForm recieveForm){

        commonBoardService.updateBoard(recieveForm.getTitle(),recieveForm.getContent(), recieveForm.getBoardRank(), board_id);
        FreeBoardResponse response = commonBoardService.findByBoardId(board_id).toResponse();
        log.info("/board/{board_id} response {}",response);
        return "redirect:/board/"+board_id;
    }

    @DeleteMapping("/board/{board_id}")
    public void deleteFreeBoard(@PathVariable String board_id){
        commonBoardService.deleteBoard(board_id);
    }

    @GetMapping("/board")
    public String viewAllFreeBoard(Model model){
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        List<Board> freeboardList = commonBoardService.findAllLikeSortedFreeBoard();
        model.addAttribute("freeboard",freeboardList);
        model.addAttribute("loginedUserRank",userService.getCurrentUserInfo().getUserRank());
        model.addAttribute("isShowTop",true);
        return "freeboardList";
    }

    @GetMapping("/board/sort=2")
    public String viewAllFreeBoardSortedByCreated(Model model){
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        List<Board> freeboardList = commonBoardService.findAllCreatedSortedBoard();
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
        Board curFreeBoardInfo = commonBoardService.findByBoardId(boardId);

        User curUser = userService.getCurrentUserInfo();

        User writer = userService.findById(curFreeBoardInfo.getId());

        //조회수 증가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            if(!commonBoardService.isContainsIpAndId(clientIp, boardId, userService.getCurrentUserId())){
                commonBoardService.saveIpAndId(clientIp, boardId,userService.getCurrentUserId());
                commonBoardService.plusView(boardId);
            }
        }else{
            if(!commonBoardService.isContainsIpAndId(clientIp, boardId, "")){
                commonBoardService.saveIpAndId(clientIp, boardId,"");
                commonBoardService.plusView(boardId);
            }
        }

//
        List<Comment> comments = commentService.findCommentsByBoardId(boardId);
        model.addAttribute("writer",commonBoardService.findWriter(boardId));
        model.addAttribute("post",commonBoardService.findByBoardId(boardId));
        model.addAttribute("comments", comments);


        //유저탑바
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());


        if(!authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("loginedUserId",userService.getCurrentUserId());
            model.addAttribute("loginedId",userService.getCurrentUserInfo().getId());
        }else{
            model.addAttribute("loginedUserId","");
            model.addAttribute("loginedId","");
        }

        //CommentResponse DTO
        List<CommentResponse> commentResponseList = comments.stream()
                .map(comment->new CommentResponse(comment.getNickName(),comment.getCommentId(),comment.getId(),
                userService.findById(comment.getId()).getUserRank(),comment.getContent(),comment.getCreatedDate()))
                .toList();
        model.addAttribute("commentResponses",commentResponseList);
//

        //여기부터 내 코드

        BoardDetailResponse postDetails = BoardDetailResponse
                .builder()
                .user(curUser)
                .board(curFreeBoardInfo)
                .ifFirstTry(true)
                .comment(commentService.findCommentsByBoardId(boardId))
                .participates(curFreeBoardInfo.getViews())
//                .writerNickname(writer.getNickname())
                .writerNickname(commonBoardService.findWriter(boardId).getNickname())
//                .writerRank(writer.getUserRank())
                .writerRank(commonBoardService.findWriter(boardId).getUserRank())
                .build();

        postDetails.setCommentResponses(postDetails.getCommentResponses().stream()
                .peek(commentResponse->{
                    String writerId = commentResponse.getWriterId();
                    int writerRank = userService.findById(writerId).getUserRank();
                    commentResponse.setWriterRank(writerRank);
                }).collect(Collectors.toList()));

        model.addAttribute("post", postDetails);

        //탈퇴유저 들어갈 수 없게하기 자기가 쓴 게시글에는 접근 가능하게 하기
        if(!authentication.getPrincipal().equals("anonymousUser")) {
            if (userService.getCurrentUserInfo().getUserId().contains("(탈퇴)") || !userService.getCurrentUserInfo().getIsActive()) {
                if (!commonBoardService.getMyBoards(curUser.getId()).contains(commonBoardService.findByBoardId(boardId))) {
                    return "signoutUserError";
                }
            }
        }

        return "freeboardDetail";
    }

    @GetMapping("/board/{board_id}/update")
    public String updateOneFreeBoard(@PathVariable String board_id,Model model){
        model.addAttribute("user",userService.getCurrentUserInfo());
        model.addAttribute("post", commonBoardService.findByBoardId(board_id));
        return "updateFreeboard";
    }
}