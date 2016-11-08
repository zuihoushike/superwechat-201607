package com.hyphenate.easeui.domain;

import java.io.Serializable;

public class Member extends User implements Serializable {
	private Integer mmemberId;
	private Integer mmemberGroupId;
	private String mmemberGroupHxid;
	private Integer mmemberPermission;

	public Member(String username, String mmemberGroupHxid, Integer mmemberGroupId, Integer mmemberId, Integer mmemberPermission) {
		super(username);
		this.mmemberGroupHxid = mmemberGroupHxid;
		this.mmemberGroupId = mmemberGroupId;
		this.mmemberId = mmemberId;
		this.mmemberPermission = mmemberPermission;
	}

	public Member(String username, String initialLetter, Integer mavatarId, String mavatarLastUpdateTime, String mavatarPath, String mavatarSuffix, Integer mavatarType, String muserName, String muserNick, String mmemberGroupHxid, Integer mmemberGroupId, Integer mmemberId, Integer mmemberPermission) {
		super(username, initialLetter, mavatarId, mavatarLastUpdateTime, mavatarPath, mavatarSuffix, mavatarType, muserName, muserNick);
		this.mmemberGroupHxid = mmemberGroupHxid;
		this.mmemberGroupId = mmemberGroupId;
		this.mmemberId = mmemberId;
		this.mmemberPermission = mmemberPermission;
	}

	public Integer getMMemberId() {
		return mmemberId;
	}
	public void setMMemberId(Integer mmemberId) {
		this.mmemberId = mmemberId;
	}
	public Integer getMMemberGroupId() {
		return mmemberGroupId;
	}
	public void setMMemberGroupId(Integer mmemberGroupId) {
		this.mmemberGroupId = mmemberGroupId;
	}
	public String getMMemberGroupHxid() {
		return mmemberGroupHxid;
	}
	public void setMMemberGroupHxid(String mmemberGroupHxid) {
		this.mmemberGroupHxid = mmemberGroupHxid;
	}
	public Integer getMMemberPermission() {
		return mmemberPermission;
	}
	public void setMMemberPermission(Integer mmemberPermission) {
		this.mmemberPermission = mmemberPermission;
	}
	@Override
	public String toString() {
		return "MemberUserAvatar [mmemberId=" + mmemberId + ", mmemberGroupId=" + mmemberGroupId + ", mmemberGroupHxid="
				+ mmemberGroupHxid + ", mmemberPermission=" + mmemberPermission + "]";
	}
	
}
