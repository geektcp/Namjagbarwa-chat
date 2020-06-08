package com.geektcp.alpha.chat.logic.user;

import com.geektcp.alpha.chat.base.Constants;
import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.base.UiBaseService;
import com.geektcp.alpha.chat.logic.user.message.req.ReqUserRegister;
import com.geektcp.alpha.chat.logic.user.message.res.ResUserInfo;
import com.geektcp.alpha.chat.logic.user.model.UserModel;
import com.geektcp.alpha.chat.ui.R;
import com.geektcp.alpha.chat.ui.StageController;
import com.geektcp.alpha.chat.util.I18n;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserManager {

	private static UserManager instance = new UserManager();

	private UserModel profile = new UserModel();

	public static UserManager getInstance() {
		return instance;
	}

	public void updateMyProfile(ResUserInfo userInfo) {
		profile.setSex(userInfo.getSex());
		profile.setSignature(userInfo.getSignature());
		profile.setUserId(userInfo.getUserId());
		profile.setUserName(userInfo.getUserName());
	}

	public UserModel getMyProfile() {
		return this.profile;
	}

	public long getMyUserId() {
		return this.profile.getUserId();
	}

	public void registerAccount(byte sex, String nickName, String password) {
		ReqUserRegister request = new ReqUserRegister();
		request.setNickName(nickName);
		request.setPassword(password);
		request.setSex(sex);

		System.err.println("向服务端发送注册请求");
		SessionManager.INSTANCE.sendMessage(request);
	}

	public void handleRegisterResponse(byte resultCode, String message) {
		boolean isSucc = resultCode == Constants.TRUE;
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		Stage stage = stageController.getStageBy(R.id.RegisterView);
		Label errorTips = (Label)stage.getScene().getRoot().lookup("#errorText");
		if (isSucc) {
			UiBaseService.INSTANCE.runTaskInFxThread(() -> {
				errorTips.setVisible(true);
				errorTips.setText(I18n.get("register.operateSucc"));
				long userId = Long.parseLong(message);
				gotoLoginPanel(userId);
			});
		}else {
			UiBaseService.INSTANCE.runTaskInFxThread(() -> {
				errorTips.setVisible(true);
				errorTips.setText("register.nickUsed");
			});
		}
	}

	private void gotoLoginPanel(long userId) {
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		stageController.switchStage(R.id.LoginView, R.id.RegisterView);
		Stage stage = stageController.getStageBy(R.id.LoginView);
		TextField userIdField = (TextField)stage.getScene().getRoot().lookup("#userId");
		userIdField.setText(String.valueOf(userId));
	}

}
