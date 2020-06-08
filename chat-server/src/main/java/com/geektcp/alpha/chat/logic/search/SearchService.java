package com.geektcp.alpha.chat.logic.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.data.dao.SearchDao;
import com.geektcp.alpha.chat.data.view.FriendView;
import com.geektcp.alpha.chat.logic.search.message.res.ResSearchFriends;
import com.geektcp.alpha.chat.logic.search.message.vo.RecommendFriendItem;
import com.geektcp.alpha.chat.net.IoSession;

@Component
public class SearchService {

	@Autowired
	private SearchDao searchDao;

	public void search(IoSession session, String key) {
		List<FriendView> users = searchDao.queryByName(key);

		List<RecommendFriendItem> vos = new ArrayList<>();

		for (FriendView item : users) {
			RecommendFriendItem vo = new RecommendFriendItem();
			vo.setUserId(item.getUserId());
			vo.setNickName(item.getUserName());
			vos.add(vo);
		}

		ResSearchFriends friends = new ResSearchFriends();
		friends.setFriends(vos);
		SessionManager.INSTANCE.sendPacketTo(session, friends);
	}

}
