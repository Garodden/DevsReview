package com.example.KeyboardArenaProject.utils.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateIdUtils {
	public static String generateUserId(LocalDateTime signUpDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
		String date = signUpDate.format(dateFormatter);
		String time = signUpDate.format(timeFormatter);
		return "user_" + date + "_" + time;
	}

	public static String generateBoardId(LocalDateTime signUpDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
		String date = signUpDate.format(dateFormatter);
		String time = signUpDate.format(timeFormatter);
		return "board_" + date + "_" + time;
	}

	public static String generateCommentId(LocalDateTime signUpDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
		String date = signUpDate.format(dateFormatter);
		String time = signUpDate.format(timeFormatter);
		return "Comment_" + date + "_" + time;
	}
}
