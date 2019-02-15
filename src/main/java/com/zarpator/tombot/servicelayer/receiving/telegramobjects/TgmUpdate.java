package com.zarpator.tombot.servicelayer.receiving.telegramobjects;

import com.zarpator.tombot.servicelayer.receiving.TgmPossibleResult;

public class TgmUpdate extends TgmPossibleResult{
	private int update_id;
	private TgmMessage message;
	public int getUpdate_id() {
		return update_id;
	}
	public void setUpdate_id(int update_id) {
		this.update_id = update_id;
	}
	public TgmMessage getMessage() {
		return message;
	}
	public void setMessage(TgmMessage message) {
		this.message = message;
	}
	public TgmMessage getEdited_message() {
		return edited_message;
	}
	public void setEdited_message(TgmMessage edited_message) {
		this.edited_message = edited_message;
	}
	public TgmMessage getChannel_post() {
		return channel_post;
	}
	public void setChannel_post(TgmMessage channel_post) {
		this.channel_post = channel_post;
	}
	public TgmMessage getEdited_channel_post() {
		return edited_channel_post;
	}
	public void setEdited_channel_post(TgmMessage edited_channel_post) {
		this.edited_channel_post = edited_channel_post;
	}
	private TgmMessage edited_message;
	private TgmMessage channel_post;
	private TgmMessage edited_channel_post;
}
