package com.geektcp.alpha.chat.logic.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.base.UiBaseService;
import com.geektcp.alpha.chat.logic.chat.message.req.ReqChatToUser;
import com.geektcp.alpha.chat.logic.user.UserManager;
import com.geektcp.alpha.chat.ui.R;
import com.geektcp.alpha.chat.ui.StageController;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatManager {

	private static ChatManager self = new ChatManager();

	private ChatManager() {}

	public static ChatManager getInstance() {
		return self;
	}

	public void sendMessageTo(long friendId, String content) {
		ReqChatToUser request = new ReqChatToUser();
		request.setToUserId(friendId);
		request.setContent(content);

		SessionManager.INSTANCE.sendMessage(request);
	}

	public void receiveFriendPrivateMessage(long sourceId, String content) {
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		Stage stage = stageController.getStageBy(R.id.ChatToPoint);
		VBox msgContainer = (VBox)stage.getScene().getRoot().lookup("#msgContainer");

		UiBaseService.INSTANCE.runTaskInFxThread(()-> {
			Pane pane = null;
			if (sourceId == UserManager.getInstance().getMyUserId()) {
				pane = stageController.load(R.layout.PrivateChatItemRight, Pane.class);
			}else {
				pane = stageController.load(R.layout.PrivateChatItemLeft, Pane.class);
			}

			decorateChatRecord(content, pane);
			msgContainer.getChildren().add(pane);
		});

	}

	private void decorateChatRecord(String message, Pane chatRecord) {
		Hyperlink _nikename = (Hyperlink) chatRecord.lookup("#nameUi");
		_nikename.setText(message);
		_nikename.setVisible(false);
		Label _createTime = (Label) chatRecord.lookup("#timeUi");
		_createTime.setText(new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss").format(new Date()));
		Label _body = (Label) chatRecord.lookup("#contentUi");
		_body.setText(message);
	}

}
