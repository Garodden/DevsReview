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
import com.example.KeyboardArenaProject.utils.user.UserTopBarInfoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "아레나 CRUD")
@Controller
public class ArenaController {
    private final ArenaService arenaService;
    private final ClearedService clearedService;
    private final CommentService commentService;
    private final UserService userService;
    public ArenaController(ArenaService arenaService, ClearedService cleardService, CommentService commentService, UserService userService){
        this.arenaService = arenaService;
        this.clearedService = cleardService;
        this.commentService =commentService;
        this.userService = userService;
    }

    @Operation(summary = "아레나 전체 보기", description = "주간 랭킹 아레나, 좋아요 top 3 아레나, 그리고 나머지 일반 아레나들을 순서대로 보여주는 API")
    @GetMapping("/arenas")
    public String showArena(Model model) {

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

        return "arenaList";
    }


    @Operation(summary = "개별 아레나 확인", description = " 유저 탑 네비게이션바, 아레나 정보, 아레나에 적힌 댓글들을 가져오는 API")
    @GetMapping("/arenas/{boardId}")
    public String showArenaDetails(@PathVariable String boardId, Model model) throws JsonProcessingException {

        Board arenaRawInfo = arenaService.findByBoardId(boardId);

        if(!arenaRawInfo.getIfActive()){
            return "redirect:/arena/"+boardId+"/verify";
        }
        User curUser = userService.getCurrentUserInfo();
        User writer = userService.findById(arenaRawInfo.getId());
        UserBoardCompositeKey curKey = UserBoardCompositeKey
                .builder()
                .id(curUser.getId())
                .boardId(arenaRawInfo.getBoardId())
                .build();


        boolean ifFirstTry = !(clearedService.findIfUserDataExists(curKey) &&
                clearedService.findIfUserClearDataExists(curKey));

        List<ArenaBestUserResponse> top5Users = userService.findTop5UsersOfBoard(boardId);

        BoardDetailResponse arenaDetails = BoardDetailResponse
                .builder()
                .user(curUser)
                .board(arenaRawInfo)
                .ifFirstTry(ifFirstTry)
                .comment(commentService.findCommentsByBoardId(boardId))
                .participates(clearedService.findParticipatesByBoardId(boardId))
                .writerNickname(writer.getNickname())
                .writerRank(writer.getUserRank())
                .build();

        arenaDetails.setCommentResponses(arenaDetails.getCommentResponses().stream()
                        .peek(commentResponse->{
                            String writerId = commentResponse.getWriterId();
                            int writerRank = userService.findById(writerId).getUserRank();
                            commentResponse.setWriterRank(writerRank);
        }).collect(Collectors.toList()));

        model.addAttribute("arena", arenaDetails);
        model.addAttribute("bestUsers", top5Users);

        return "arenaDetail";

    }

    @Operation(summary = "시작 시간 기록", description = "챌린지 시작 시간을 기록하는 용도, 시작 시간을 기록함")
    @GetMapping("/arenas/{boardId}/start")
    @ResponseBody
    public ResponseEntity<ArenaStartTimeResponse> markArenaStartTime(@PathVariable String boardId){

        User user = userService.getCurrentUserInfo();
        UserBoardCompositeKey curUsersClearRecord = UserBoardCompositeKey.builder().id(user.getId()).boardId(boardId).build();
        //클리어 보드에 현재 시작한 시간을 기록
        //만약 클리어 기록이 존재하면 해당 기록에서 시간 기록 시작
        //아니라면 새로운 기록 생성, 시간 기록 시작
        if(clearedService.findIfUserDataExists(curUsersClearRecord)){
            clearedService.updateStartTime(curUsersClearRecord);
        }
        else {
            clearedService.saveCleared(user.getId(), boardId);
        }

        return ResponseEntity.ok(new ArenaStartTimeResponse(LocalDateTime.now()));
    }

    @Operation(summary = "개별 아레나 참전.", description = "개별 아레나에 참전. 시작 시간과 비교하여 시간이 얼마나 걸렸는지 알려줌" +
            "팝업 출력용 문자열 json으로 리턴. 주간랭킹 첫 클리어시 1000포인트 지급")
    @PostMapping("/arenas/{boardId}")
    @ResponseBody
    public String checkArenaResult(@PathVariable String boardId, @RequestParam String userTypedText){

        Board curBoard = arenaService.findByBoardId(boardId);
        User curUser = userService.getCurrentUserInfo();
        UserBoardCompositeKey curKey = UserBoardCompositeKey.builder()
                .id(curUser.getId())
                .boardId(boardId)
                .build();

        String originalContent = curBoard.getContent().trim();
        String userTypedContent= userTypedText.trim();

        ArenaResultResponse result = new ArenaResultResponse();

        if(!originalContent.equals(userTypedContent)){
            return result.resultPopupText(false);
        }
        else {

            if (curBoard.getBoardType() == 2 && !clearedService.findIfUserClearDataExists(curKey)){//주간랭크전 첫 클리어시
                userService.givePoints(curUser.getId(), 1000);
            }
            if(!clearedService.findIfUserClearDataExists(curKey)) {
                arenaService.updateParticipates(curBoard.getBoardId());
            }

            List<Cleared> participantList = clearedService.findAllByBoardId(boardId);
            Long participantSize = (long) participantList.size();
            Long ranking = (long) clearedService.findRanking(participantList, curUser.getId());
            LocalTime newClearTime= clearedService.updateClearTime(curKey);


            result.setTitle(curBoard.getTitle());
            result.setParticipants(participantSize);
            result.setNthPlace(ranking);
            result.setTime(newClearTime);
            result.setPercentage((double) ranking/participantSize);

            return result.resultPopupText(true);
        }
    }

    @Operation(summary = "아레나 삭제 API", description = "아레나 boardId를 받고 삭제")
    @DeleteMapping("/arenas/{boardId}")
    @ResponseBody
    public ResponseEntity<Void> deleteArena(@PathVariable String boardId){
        arenaService.deleteBy(boardId);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "아레나 제작", description = "아레나 개장 페이지 get 메소드 API")
    @GetMapping("/newArena")
    public String typeNewArena(Model model) {
        User currentUser = userService.getCurrentUserInfo();
        model.addAttribute("userTopBarInfo", new UserTopBarInfo(currentUser));
        return "newArena";
    }

    @Operation(summary = "아레나 제작", description = "아레나 개장 Post 메소드 API 개장한 arena의 boardId를 알려준다")
    @PostMapping("/newArena")
    @ResponseBody
    public String addNewArena(@RequestBody ArenaReceiveForm request) {

        User curUser = userService.getCurrentUserInfo();

        //콘텐트 양 옆의 whitespace 문자 제거.
        String strippedContent = request.getContent().strip();

        Board arena = Board.builder()
                .id(curUser.getId())
                .title(request.getTitle())
                .content(strippedContent)
                .board_rank(request.getBoardRank())
                .board_type(3)
                .active(false)
                .build();

        arenaService.saveArena(arena);
        return arena.getBoardId();
    }


    @Operation(summary = "제작한 아레나 활성화 전 검증 사이트 API", description = "아레나 개장 Post 메소드 API")
    @GetMapping("/arena/{boardId}/verify")
    public String addNewArena(@PathVariable String boardId, Model model ) {
        User currentUser = userService.getCurrentUserInfo();
        Board currentBoard = arenaService.findByBoardId(boardId);

        ArenaVerifyResponse verifyResponse = ArenaVerifyResponse
                .builder()
                .board(currentBoard)
                .user(currentUser)
                .writer(userService.getNickNameById(currentBoard.getId()))
                .build();

        model.addAttribute("arena", verifyResponse);

        return "arenaVerify";
    }

    @Operation(summary = "아레나 검증 시작 시간 기록", description = "검증 시작 시간을 기록하는 용도, 시작 시간을 기록함")
    @GetMapping("/arenas/{boardId}/verify/start")
    @ResponseBody
    public ResponseEntity<ArenaStartTimeResponse> markArenaVerifyStartTime(@PathVariable String boardId){

        User user = userService.getCurrentUserInfo();
        UserBoardCompositeKey curUsersClearRecord = UserBoardCompositeKey.builder().id(user.getId()).boardId(boardId).build();
        //클리어 보드에 현재 시작한 시간을 기록
        //만약 클리어 기록이 존재하면 해당 기록에서 시간 기록 시작
        //아니라면 새로운 기록 생성, 시간 기록 시작
        if(clearedService.findIfUserDataExists(curUsersClearRecord)){
            clearedService.updateStartTime(curUsersClearRecord);
        }
        else {
            clearedService.saveCleared(user.getId(), boardId);
        }

        return ResponseEntity.ok(new ArenaStartTimeResponse(LocalDateTime.now()));
    }



    @Operation(summary = "제작한 아레나 게시 전 검증 사이트 API", description = "유저가 해당 아레나를 클리어한 시간이 120초 이하면 아레나를 활성화시켜준다.")
    @PostMapping("/arena/{boardId}/verify")
    @ResponseBody
    public String addNewArenaverify(@PathVariable String boardId, @RequestParam String userTypedText) {

        Board curBoard = arenaService.findByBoardId(boardId);
        User curUser = userService.getCurrentUserInfo();

        UserBoardCompositeKey curKey = UserBoardCompositeKey.builder()
                .id(curUser.getId())
                .boardId(boardId)
                .build();

        String originalContent = curBoard.getContent().trim();
        String userTypedContent= userTypedText.trim();

        clearedService.getOnlyClearTime(curKey);

        ArenaVerifyResultResponse result = ArenaVerifyResultResponse
                .builder()
                .clearTimeBySeconds(clearedService.getOnlyClearTime(curKey))
                .title(curBoard.getTitle())
                .build();

        if(originalContent.equals(userTypedContent)&&
                result.getClearTimeBySeconds()<=120){
            clearedService.updateClearTime(curKey);
            arenaService.updateActive(curBoard.getBoardId());
            return result.resultPopupText(true);
        }
        else {
            return result.resultPopupText(false);
        }
    }



}


