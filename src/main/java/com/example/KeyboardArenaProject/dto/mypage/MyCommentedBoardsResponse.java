package com.example.KeyboardArenaProject.dto.mypage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.KeyboardArenaProject.dto.CommentResponse;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Comment;

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
	List<CommentResponse> commentResponses;

	@Builder
	MyCommentedBoardsResponse(Board board, String author, List<Comment> myComments) {
		this.boardId = board.getBoardId();
		this.boardType = board.getBoardType();
		this.title = board.getTitle();
		this.author = author;
		this.createdDate = board.getCreatedDate();
		this.likes = board.getLikes();
		this.commentResponses = myComments
			.stream()
			.map(CommentResponse::new)
			.collect(Collectors.toList());
	}
}
