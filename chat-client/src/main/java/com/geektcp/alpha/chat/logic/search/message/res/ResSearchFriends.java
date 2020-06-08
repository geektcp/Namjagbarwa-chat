package com.geektcp.alpha.chat.logic.search.message.res;

import java.util.ArrayList;
import java.util.List;

import com.geektcp.alpha.chat.base.UiBaseService;
import com.geektcp.alpha.chat.logic.search.SearchManager;
import com.geektcp.alpha.chat.logic.search.model.RecommendFriendItem;
import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.PacketType;

import io.netty.buffer.ByteBuf;

public class ResSearchFriends extends AbstractPacket {

	private List<RecommendFriendItem> friends;

	@Override
	public PacketType getPacketType() {
		return PacketType.ResSearchFriends;
	}

	@Override
	public void writeBody(ByteBuf buf) {
		buf.writeInt(friends.size());
		for (RecommendFriendItem item:friends) {
			item.writeBody(buf);
		}

	}

	@Override
	public void readBody(ByteBuf buf) {
		int size = buf.readInt();
		this.friends = new ArrayList<>(size);
		for (int i=0;i<size;i++) {
			RecommendFriendItem item = new RecommendFriendItem();
			item.readBody(buf);
			friends.add(item);
		}
	}

	@Override
	public void execPacket() {
		UiBaseService.INSTANCE.runTaskInFxThread(() -> {
			SearchManager.getInstance().refreshRecommendFriends(friends);
		});
	}

}
