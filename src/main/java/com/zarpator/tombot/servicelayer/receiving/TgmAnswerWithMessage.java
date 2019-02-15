package com.zarpator.tombot.servicelayer.receiving;

import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;

public class TgmAnswerWithMessage extends TgmAnswerNormal {
	private TgmMessage result;

	public TgmMessage getResult() {
		return result;
	}

	public void setResult(TgmMessage result) {
		this.result = result;
	}
}
