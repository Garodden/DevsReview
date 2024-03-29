package com.example.KeyboardArenaProject.controller.arena;

import com.example.KeyboardArenaProject.dto.arena.*;
import com.example.KeyboardArenaProject.dto.user.UserTopBarInfo;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.CommentService;
import com.example.KeyboardArenaProject.service.arena.ArenaService;
import com.example.KeyboardArenaProject.service.user.CleaerdService;
import com.example.KeyboardArenaProject.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "아레나 CRUD")
@Controller
public class ArenaController {
    private final ArenaService arenaService;
    private final CleaerdService cleaerdService;
    private final CommentService commentService;
    private final UserService userService;
    public ArenaController(ArenaService arenaService, CleaerdService cleaerdService, CommentService commentService, UserService userService){
        this.arenaService = arenaService;
        this.cleaerdService = cleaerdService;
        this.commentService =commentService;
        this.userService = userService;
    }

    @Operation(summary = "아레나 전체 보기", description = "주간 랭킹 아레나, 좋아요 top 3 아레나, 그리고 나머지 일반 아레나들을 순서대로 보여주는 API")
    @GetMapping("/arenas")
    public String showArena(Model model) {

        List<Board> arenaList = arenaService.findAllRankArena();

        arenaList.addAll(arenaService.findTop3ArenaOrderByLikes());

        arenaList.addAll(arenaService.findNormalArenaOrderByCreatedDate());

        List<ArenaResponse> ArenaResponseList = arenaList.stream()
                .map(ArenaResponse::new)
                .toList();

        model.addAttribute("arenas", ArenaResponseList);

        return "arenaList";
    }


    @Operation(summary = "개별 아레나 확인", description = " 유저 탑 네비게이션바, 아레나 정보, 아레나에 적힌 댓글들을 가져오는 API")
    @GetMapping("/arenas/{boardId}")
    public String showArenaDetails(@PathVariable String boardId, Model model) throws JsonProcessingException {
        Board arenaRawInfo = arenaService.findByBoardId(boardId);

        String id = arenaRawInfo.getId();

        //임시유저. 현재 로그인돼있는 기존 유저를 사용하면 됨.
        User user = userService.getCurrentUserInfo();

        ArenaDetailResponse arenaDetails = ArenaDetailResponse
                .builder()
                .user(user)
                .board(arenaRawInfo)
                .comment(commentService.findCommentsByBoardId(boardId))
                .participates(cleaerdService.findParticipatesByBoardId(boardId))
                .writer(userService.getNickNameById(arenaRawInfo.getId()))
                .build();
        model.addAttribute("arena", arenaDetails);
        return "arenaDetail";
    }

    @Operation(summary = "아레나 제작", description = "아레나 개장 페이지 get 메소드 API")
    @GetMapping("/newArena")
    public String typeNewArena(Model model) {
        User currentUser = userService.getCurrentUserInfo();
        model.addAttribute("userTopBarInfo", new UserTopBarInfo(currentUser));
    return "newArena";
    }

    @Operation(summary = "아레나 제작", description = "아레나 개장 Post 메소드 API")
    @PostMapping("/newArena")
    public String addNewArena(@RequestBody ArenaReceiveForm request) {
        User currentUser = userService.getCurrentUserInfo();
        Board arena = Board.builder()
                .id(currentUser.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .board_rank(currentUser.getUserRank())
                .board_type(3)
                .active(false)
                .build();

        arenaService.saveArena(arena);
        return "redirect:/arena"+arena.getBoardId()+"/verify";
    }



}


