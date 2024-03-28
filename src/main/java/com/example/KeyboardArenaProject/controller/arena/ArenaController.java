package com.example.KeyboardArenaProject.controller.arena;

import com.example.KeyboardArenaProject.dto.arena.ArenaDashBoardResponse;
import com.example.KeyboardArenaProject.dto.arena.ArenaDetailResponse;
import com.example.KeyboardArenaProject.dto.arena.ArenaResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "아레나 CRUD")
@RestController
public class ArenaController {
    private final ArenaService arenaService;
    private final CleaerdService cleaerdService;
    private final CommentService commentService;
    public ArenaController(ArenaService arenaService, CleaerdService cleaerdService, CommentService commentService){
        this.arenaService = arenaService;
        this.cleaerdService = cleaerdService;
        this.commentService =commentService;
    }

    @Operation(summary = "아레나 전체 보기", description = "주간 랭킹 아레나, 좋아요 top 3 아레나, 그리고 나머지 일반 아레나들을 순서대로 보여주는 API")
    @GetMapping("/arenas")
    public List<ArenaResponse> showArena() {

        List<Board> arenaList = arenaService.findAllRankArena();

        arenaList.addAll(arenaService.findTop3ArenaOrderByLikes());

        arenaList.addAll(arenaService.findNormalArenaOrderByCreatedDate());

        List<ArenaResponse> ArenaResponseList = arenaList.stream()
                .map(ArenaResponse::new)
                .toList();
        return ArenaResponseList;
        
    }

    @Operation(summary = "개별 아레나 확인", description = " 유저 탑 네비게이션바, 아레나 정보, 아레나에 적힌 댓글들을 가져오는 API")
    @GetMapping("/arena/{boardId}")
    public ArenaDetailResponse showArenaDetails(@PathVariable String boardId) throws JsonProcessingException {
        Board arenaRawInfo = arenaService.findByBoardId(boardId);

        String id = arenaRawInfo.getId();

        //임시유저. 현재 로그인돼있는 기존 유저를 사용하면 됨.
        User user = UserService.getTemporalUserGet();

        ArenaDetailResponse arenaDetails = ArenaDetailResponse
                                            .builder()
                                            .user(user)
                                            .board(arenaRawInfo)
                                            .comment(commentService.findCommentsByBoardId(boardId))
                                            .participates(cleaerdService.findParticipatesByBoardId(boardId))
                                            .build();
        return arenaDetails;
    }



}


