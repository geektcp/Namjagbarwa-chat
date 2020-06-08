package com.geektcp.alpha.chat.logic.friend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geektcp.alpha.chat.base.Constants;
import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.data.dao.FriendDao;
import com.geektcp.alpha.chat.data.model.User;
import com.geektcp.alpha.chat.data.view.FriendView;
import com.geektcp.alpha.chat.logic.friend.message.res.ResFriendList;
import com.geektcp.alpha.chat.logic.friend.message.res.ResFriendLogin;
import com.geektcp.alpha.chat.logic.friend.message.res.ResFriendLogout;
import com.geektcp.alpha.chat.logic.friend.message.vo.FriendItemVo;
import com.geektcp.alpha.chat.logic.user.UserService;

@Component
public class FriendService {

	@Autowired
	private FriendDao friendDao;
	@Autowired
	private UserService userService;

	public List<FriendItemVo> listMyFriends(long userId) {
		List<FriendItemVo> result = new ArrayList<>();
		List<FriendView> friends = friendDao.getMyFriends(userId);
		for (FriendView f:friends) {
			if (f.getUserId() == userId) {
				continue;
			}
			FriendItemVo item = new FriendItemVo();
			item.setGroup(f.getGroup());
			item.setRemark(f.getRemark());
			item.setSex(f.getSex());
			item.setSignature(f.getSignature());
			item.setUserId(f.getUserId());
			item.setUserName(f.getUserName());
			item.setGroupName(f.getGroupName());
			if (userService.isOnlineUser(f.getUserId())) {
				item.setOnline(Constants.ONLINE_STATUS);
			}

			result.add(item);
		}

		return result;
	}

	public void refreshUserFriends(User user) {
		List<FriendItemVo> myFriends = listMyFriends(user.getUserId());
		ResFriendList friendsPact = new ResFriendList();
		friendsPact.setFriends(myFriends);

		SessionManager.INSTANCE.sendPacketTo(user, friendsPact);

		onUserLogin(user);
	}

	public void onUserLogin(User user) {
		List<FriendItemVo> myFriends = listMyFriends(user.getUserId());
		ResFriendLogin loginPact = new ResFriendLogin();
		loginPact.setFriendId(user.getUserId());
		for (FriendItemVo friend:myFriends) {
			long friendId = friend.getUserId();
			if (userService.isOnlineUser(friendId)) {
				SessionManager.INSTANCE.sendPacketTo(friendId, loginPact);
			}
		}
	}

	public void onUserLogout(long userId) {
		List<FriendItemVo> myFriends = listMyFriends(userId);
		ResFriendLogout logoutPact = new ResFriendLogout();
		logoutPact.setFriendId(userId);
		for (FriendItemVo friend:myFriends) {
			long friendId = friend.getUserId();
			if (userService.isOnlineUser(friendId)) {
				SessionManager.INSTANCE.sendPacketTo(friendId, logoutPact);
			}
		}
	}


}
