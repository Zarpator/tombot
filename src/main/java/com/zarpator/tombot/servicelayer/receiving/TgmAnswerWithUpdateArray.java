package com.zarpator.tombot.servicelayer.receiving;

import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;

public class TgmAnswerWithUpdateArray extends TgmAnswerArray{
	private TgmUpdate[] result;

	public TgmUpdate[] getResult() {
		return result;
	}
}
