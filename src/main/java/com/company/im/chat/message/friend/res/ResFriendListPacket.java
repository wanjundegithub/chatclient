package com.company.im.chat.message.friend.res;

import com.company.im.chat.common.PacketType;
import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.friend.FriendItemBean;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友列表信息-服务器应答
 */
public class ResFriendListPacket extends AbstractPacket {

	private List<FriendItemBean> friends=new ArrayList<>();

	public ResFriendListPacket(List<FriendItemBean> friends) {
		this.friends = friends;
	}

	public ResFriendListPacket() {
	}

	@Override
	public int getPacketID() {
		return PacketType.ResFriendList;
	}


	@Override
	public void writeBody(ByteBuf byteBuf) {
		int size=friends.size();
		byteBuf.writeInt(size);
		for(var item:friends){
			item.writeBody(byteBuf);
		}
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
