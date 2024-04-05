package com.example.KeyboardArenaProject.dto.arena;

import com.example.KeyboardArenaProject.dto.user.UserTopBarInfo;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArenaVerifyResponse {
    UserTopBarInfo userTopBarInfo;
    String boardId;
    String title;
    String content;
    String writerNickName;
    String writerId;
    int writerRank;
    LocalDateTime createdDate;
    int boardRank;

    @Builder ArenaVerifyResponse(User user, Board board){
        this.userTopBarInfo = new UserTopBarInfo(user);
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardId = board.getBoardId();
        this.writerNickName = user.getNickname();
        this.writerId = user.getUserId();
        this.writerRank = user.getUserRank();
        this.boardRank = board.getBoardRank();
        this.createdDate = board.getCreatedDate();

    }
}
