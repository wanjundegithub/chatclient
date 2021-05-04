package com.company.im.chat.ui;

/*
**界面View
 */
public final class View {

	public static class id {
		public static final String RegisterView = "RegisterView";
		public static final String LoginView = "Login_View";
		public static final String MainView = "MainView";
		public static final String ChatToFriend = "ChatToFriend";
		public static final String PrivateChatItem = "PrivateChatItem";
		public static final String SearchView = "SearchView";
	}

	public static class layout {
		/** 注册界面 */
		public static final String RegisterView = "register/xml/register.fxml";
		/** 登录界面 */
		public static final String LoginView = "login/xml/login.fxml";
		/** 主界面 */
		public static final String MainView = "main/xml/main.fxml";
		/** 主界面 */
		public static final String FriendItem = "main/xml/friendItem.fxml";
		/** 点对点聊天窗口 */
		public static final String ChatToFriend = "main/xml/chatToFriend.fxml";
		/** 点对点聊天记录 */
		public static final String PrivateChatItemLeft = "main/xml/chatItemLeft.fxml";
		public static final String PrivateChatItemRight = "main/xml/chatItemRight.fxml";

		public static final String SearchFriendView = "query/xml/query.fxml";
		public static final String RecommendFriendItem = "query/xml/recommend_item.fxml";
	}

}
