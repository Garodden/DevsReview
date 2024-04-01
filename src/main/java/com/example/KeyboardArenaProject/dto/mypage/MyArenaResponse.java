package com.example.KeyboardArenaProject.dto.mypage;
import com.example.KeyboardArenaProject.entity.Board;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyArenaResponse {
	String boardId;
	int boardType;
	String title;
	int boardRank;
	LocalDateTime createdDate;
	int participates;
	int comments;
	int likes;

	@Builder
	MyArenaResponse(Board board, int participates) {
		this.boardId = board.getBoardId();
		this.boardType = board.getBoardType();
		this.title = board.getTitle();
		this.boardRank = board.getBoardRank();
		this.createdDate = board.getCreatedDate();
		this.participates = participates;
		this.comments =board.getComments();
		this.likes = board.getLikes();
	}
}
