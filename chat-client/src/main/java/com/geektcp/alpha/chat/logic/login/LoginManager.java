package com.geektcp.alpha.chat.logic.login;

import com.geektcp.alpha.chat.base.Constants;
import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.base.UiBaseService;
import com.geektcp.alpha.chat.logic.login.message.req.ReqHeartBeat;
import com.geektcp.alpha.chat.logic.login.message.req.ReqUserLogin;
import com.geektcp.alpha.chat.logic.login.message.res.ResUserLogin;
import com.geektcp.alpha.chat.ui.R;
import com.geektcp.alpha.chat.ui.StageController;
import com.geektcp.alpha.chat.util.I18n;
import com.geektcp.alpha.chat.util.SchedulerManager;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginManager {

	private static LoginManager instance = new  LoginManager();

	private LoginManager() {}

	public static LoginManager getInstance() {
		return instance;
	}

	public void beginToLogin(long userId, String password) {
		ReqUserLogin reqLogin= new ReqUserLogin();
		reqLogin.setUserId(userId);
		reqLogin.setUserPwd(password);
		System.err.println("向服务端发送登录请求");
		SessionManager.INSTANCE.sendMessage(reqLogin);
	}

	public void handleLoginResponse(ResUserLogin resp) {
		boolean isSucc = resp.getIsValid() == Constants.TRUE;
		if (isSucc) {
			UiBaseService.INSTANCE.runTaskInFxThread(() -> {
				redirecToMainPanel();
			});

			registerHeartTimer();
		}else {
			UiBaseService.INSTANCE.runTaskInFxThread(() -> {
				StageController stageController = UiBaseService.INSTANCE.getStageController();
				Stage stage = stageController.getStageBy(R.id.LoginView);
				Pane errPane = (Pane)stage.getScene().getRoot().lookup("#errorPane");
				errPane.setVisible(true);
				Label errTips = (Label)stage.getScene().getRoot().lookup("#errorTips");
				errTips.setText(I18n.get("login.operateFailed"));
			});
		}
	}

	private void redirecToMainPanel() {
		StageController stageController = UiBaseService.INSTANCE.getStageController();
		stageController.switchStage(R.id.MainView, R.id.LoginView);
	}

	/**
	 * 注册心跳事件
	 */
	private void registerHeartTimer() {
		SchedulerManager.INSTANCE.scheduleAtFixedRate("HEART_BEAT", () -> {
			SessionManager.INSTANCE.sendMessage(new ReqHeartBeat());
		}, 0, 5*1000);
	}

}
