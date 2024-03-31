package com.example.KeyboardArenaProject.controller.arena;

import com.example.KeyboardArenaProject.dto.arena.*;
import com.example.KeyboardArenaProject.dto.user.UserTopBarInfo;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Cleared;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.service.CommentService;
import com.example.KeyboardArenaProject.service.arena.ArenaService;
import com.example.KeyboardArenaProject.service.user.ClearedService;
import com.example.KeyboardArenaProject.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "아레나 CRUD")
@Controller
public class ArenaController {
    private final ArenaService arenaService;
    private final ClearedService cleardService;
    private final CommentService commentService;
    private final UserService userService;
    public ArenaController(ArenaService arenaService, ClearedService cleardService, CommentService commentService, UserService userService){
        this.arenaService = arenaService;
        this.cleardService = cleardService;
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

        User user = userService.getCurrentUserInfo();

        ArenaDetailResponse arenaDetails = ArenaDetailResponse
                .builder()
                .user(user)
                .board(arenaRawInfo)
                .comment(commentService.findCommentsByBoardId(boardId))
                .participates(cleardService.findParticipatesByBoardId(boardId))
                .writer(userService.getNickNameById(arenaRawInfo.getId()))
                .build();
        model.addAttribute("arena", arenaDetails);

        UserBoardCompositeKey curUsersClearRecord = new UserBoardCompositeKey(user.getId(), boardId);

        //클리어 보드에 현재 시작한 시간을 기록
        //만약 클리어 기록이 존재하면 해당 기록에서 시간 기록 시작
        //아니라면 새로운 기록 생성, 시간 기록 시작
        if(cleardService.findIfUserClearDataExists(curUsersClearRecord)){
            cleardService.update(curUsersClearRecord);
        }
        else {
            Cleared cleared = Cleared.builder()
                    .boardId(boardId)
                    .id(user.getId())
                    .startTime(LocalDateTime.now())
                    .build();
            cleardService.saveClearStartTime(cleared);
        }

        return "arenaDetail";
    }

    @Operation(summary = "개별 아레나 참전.", description = "개별 아레나에 참전. 시작 시간과 비교하여 시간이 얼마나 걸렸는지 확인하기.")
    @PostMapping("/arenas/{boardId}")
    public String checkArenaResult(@PathVariable String boardId, @RequestBody String userTypedContent){


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

        //콘텐트 양 옆의 whitespace 문자 제거.
        String strippedContent = request.getContent().strip();

        Board arena = Board.builder()
                .id(currentUser.getId())
                .title(request.getTitle())
                .content(strippedContent)
                .board_rank(currentUser.getUserRank())
                .board_type(3)
                .active(false)
                .build();

        arenaService.saveArena(arena);
        return "redirect:/arena"+arena.getBoardId()+"/verify";
    }



}


