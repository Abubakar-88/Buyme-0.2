package com.buyme.admin.util;


import com.buyme.common.entity.User;

public class DirectUtil {

	public static String getRedirectURLtoAffectedUser(User user) {
		//String firstPartOfEmail = user.getEmail().split("@")[0];
		String firstPartOfEmail = user.getEmail();
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
	}
}
