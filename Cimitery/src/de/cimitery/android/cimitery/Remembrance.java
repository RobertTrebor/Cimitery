package de.cimitery.android.cimitery;

public class Remembrance {
	
	private long remembranceID;
	private long graveID;
	private int visitorCount;
	private int candleCount;
	private int wreathCount;
	
	
	
	public int getVisitorCount() {
		return visitorCount;
	}
	public void setVisitorCount(int visitorCount) {
		this.visitorCount = visitorCount;
	}
	public int getCandleCount() {
		return candleCount;
	}
	public void setCandleCount(int candleCount) {
		this.candleCount = candleCount;
	}
	public int getWreathCount() {
		return wreathCount;
	}
	public void setWreathCount(int wreathCount) {
		this.wreathCount = wreathCount;
	}
	public long getRemembranceID() {
		return remembranceID;
	}
	public long getGraveID() {
		return graveID;
	}

}
