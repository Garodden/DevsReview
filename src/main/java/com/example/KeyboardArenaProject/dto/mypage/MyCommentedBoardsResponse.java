package com.example.KeyboardArenaProject.dto.mypage;

import java.time.LocalDateTime;
import com.example.KeyboardArenaProject.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyCommentedBoardsResponse {
	String boardId;
	int boardType;
	String title;
	String author;
	LocalDateTime createdDate;
	int likes;
	// List<CommentResponse> commentResponses;

	@Builder
	MyCommentedBoardsResponse(Board board, String author) {
		this.boardId = board.getBoardId();
		this.boardType = board.getBoardType();
		this.title = board.getTitle();
		this.author = author;
		this.createdDate = board.getCreatedDate();
		this.likes = board.getLikes();
		// this.commentResponses = getCommentResponses();
	}
}
