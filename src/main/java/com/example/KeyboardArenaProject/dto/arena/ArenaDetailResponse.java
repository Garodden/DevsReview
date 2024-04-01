package com.example.KeyboardArenaProject.dto.arena;

import com.example.KeyboardArenaProject.dto.CommentResponse;
import com.example.KeyboardArenaProject.dto.user.UserTopBarInfo;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArenaDetailResponse {
    UserTopBarInfo userTopBarInfo;
    String boardId;
    String title;
    String content;
    String writer;
    int boardRank;
    LocalDateTime createdDate;
    boolean ifFirstTry;
    int participates;
    int comments;
    int likes;
    int boardType;
    List<CommentResponse> commentResponses;

    @Builder
    ArenaDetailResponse(User user, Board board, List<Comment> comment, int participates,boolean ifFirstTry, String writer){
        this.userTopBarInfo = new UserTopBarInfo(user);
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.ifFirstTry = ifFirstTry;
        this.writer =writer;
        this.boardRank = board.getBoardRank();
        this.createdDate = board.getCreatedDate();
        this.participates = participates;
        this.comments =board.getComments();
        this.likes = board.getLikes();
        this.boardType = board.getBoardType();

        this.commentResponses = comment
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}
