package com.hyphenate.easeui.domain;

import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.io.Serializable;


public class User extends EaseUser implements Serializable {
	private String muserName;
	private String muserNick;
	private Integer mavatarId;
	private String mavatarPath;
	private String mavatarSuffix;
	private Integer mavatarType;
	private String mavatarLastUpdateTime;

	/**
	 * initial letter for nickname
	 */
	protected String initialLetter;



	public User(String username) {
		super(username);
	}

	public User(String username, String initialLetter, Integer mavatarId, String mavatarLastUpdateTime, String mavatarPath, String mavatarSuffix, Integer mavatarType, String muserName, String muserNick) {
		super(username);
		this.initialLetter = initialLetter;
		this.mavatarId = mavatarId;
		this.mavatarLastUpdateTime = mavatarLastUpdateTime;
		this.mavatarPath = mavatarPath;
		this.mavatarSuffix = mavatarSuffix;
		this.mavatarType = mavatarType;
		this.muserName = muserName;
		this.muserNick = muserNick;
	}

	public String getMUserName() {
		return muserName;
	}

	public void setMUserName(String muserName) {
		this.muserName = muserName;
	}

	public String getMUserNick() {
		return muserNick;
	}

	public void setMUserNick(String muserNick) {
		this.muserNick = muserNick;
	}
	
	public Integer getMAvatarId() {
		return mavatarId;
	}

	public void setMAvatarId(Integer mavatarId) {
		this.mavatarId = mavatarId;
	}
	public String getMAvatarPath() {
		return mavatarPath;
	}

	public void setMAvatarPath(String mavatarPath) {
		this.mavatarPath = mavatarPath;
	}

	public Integer getMAvatarType() {
		return mavatarType;
	}

	public void setMAvatarType(Integer mavatarType) {
		this.mavatarType = mavatarType;
	}

	public String getMAvatarLastUpdateTime() {
		return mavatarLastUpdateTime;
	}

	public void setMAvatarLastUpdateTime(String mavatarLastUpdateTime) {
		this.mavatarLastUpdateTime = mavatarLastUpdateTime;
	}
	
	public void setMAvatarSuffix(String mavatarSuffix) {
		this.mavatarSuffix = mavatarSuffix;
	}

	public String getMAvatarSuffix() {
		return mavatarSuffix;
	}

//	public String getInitialLetter() {
//		if(initialLetter == null){
//			UserUtils.setUserInitialLetter(this);
//		}
//		return initialLetter;
//	}
//
//	public void setInitialLetter(String initialLetter) {
//		this.initialLetter = initialLetter;
//	}

	@Override
	public String toString() {
		return "User{" +
				"muserName='" + muserName + '\'' +
				", muserNick='" + muserNick + '\'' +
				", mavatarId=" + mavatarId +
				", mavatarPath='" + mavatarPath + '\'' +
				", mavatarSuffix='" + mavatarSuffix + '\'' +
				", mavatarType=" + mavatarType +
				", mavatarLastUpdateTime='" + mavatarLastUpdateTime + '\'' +
				", initialLetter='" + initialLetter + '\'' +
				'}';
	}
	public String getInitialLetter(){
		if (initialLetter == null){
			EaseCommonUtils.setAppUserInitialletter(this);
		}
		return initialLetter;
	}
}
