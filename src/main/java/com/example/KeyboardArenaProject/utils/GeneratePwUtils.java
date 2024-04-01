package com.example.KeyboardArenaProject.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class GeneratePwUtils {
	public static String generateNewPassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
