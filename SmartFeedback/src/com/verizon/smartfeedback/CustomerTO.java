package com.verizon.smartfeedback;

public class CustomerTO {
	String mobileNumber,goodFeedback,goodComments,badFeedback,badComments,ideas;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGoodFeedback() {
		return goodFeedback;
	}

	public void setGoodFeedback(String goodFeedback) {
		this.goodFeedback = goodFeedback;
	}

	public String getGoodComments() {
		return goodComments;
	}

	public void setGoodComments(String goodComments) {
		this.goodComments = goodComments;
	}

	public String getBadFeedback() {
		return badFeedback;
	}

	public void setBadFeedback(String badFeedback) {
		this.badFeedback = badFeedback;
	}

	public String getBadComments() {
		return badComments;
	}

	public void setBadComments(String badComments) {
		this.badComments = badComments;
	}

	public String getIdeas() {
		return ideas;
	}

	public void setIdeas(String ideas) {
		this.ideas = ideas;
	}
	
}
