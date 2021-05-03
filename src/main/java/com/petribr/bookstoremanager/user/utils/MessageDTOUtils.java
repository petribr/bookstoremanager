package com.petribr.bookstoremanager.user.utils;

import com.petribr.bookstoremanager.user.dto.MessageDTO;
import com.petribr.bookstoremanager.user.entity.User;

public class MessageDTOUtils {
	public static MessageDTO creationMessage(User user) {
		return getActionMessage(user, "created");
	}
	
	public static MessageDTO updateMessage(User user) {
		return getActionMessage(user, "updated");
	}
	
	public static MessageDTO getActionMessage(User user, String action) {
		String createdMessage = String.format("User %s with ID %s sucessfully %s",
					user.getUserName(), user.getId(), action);
		return MessageDTO.builder()
				.message(createdMessage)
				.build();
	}
}
