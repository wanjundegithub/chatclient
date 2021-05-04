package com.company.im.chat.message.friend.res;

import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.FriendItemBean;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ResFriendListPacket extends AbstractPacket {

	private List<FriendItemBean> friends;

	@Override
	public int getPacketID() {
		return 0;
	}


	@Override
	public void writeBody(ByteBuf byteBuf) {
		//empty
	}

	@Override
	public void readBody(ByteBuf buf) {
		int size = buf.readInt();
		this.friends = new ArrayList<>(size);
		for (int i=0;i<size;i++) {
			FriendItemBean item = new FriendItemBean();
			item.readBody(buf);
			friends.add(item);
		}
	}

	public List<FriendItemBean> getFriends() {
		return friends;
	}

}
