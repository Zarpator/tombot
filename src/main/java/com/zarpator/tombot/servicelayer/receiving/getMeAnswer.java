package com.zarpator.tombot.servicelayer.receiving;

public class getMeAnswer {
	private boolean ok;
	private TgmPossibleResult result;
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public TgmPossibleResult getResult() {
		return result;
	}

	public void setResult(TgmPossibleResult result) {
		this.result = result;
	}

	@Override
	public String toString(){
		return "" + ok;
	}
}
