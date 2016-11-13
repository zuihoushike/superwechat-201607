/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ypxy.superwechat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ypxy.superchat.R;
import cn.ypxy.superwechat.Constant;
import cn.ypxy.superwechat.SuperWeChatHelper;
import cn.ypxy.superwechat.utils.MFGT;

import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.widget.EaseSidebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GroupPickContactsActivity extends BaseActivity {
	/** if this is a new group */
	protected boolean isCreatingNewGroup;
	@BindView(R.id.img_back)
	ImageView mImgBack;
	@BindView(R.id.txt_title)
	TextView mTxtTitle;
	@BindView(R.id.txt_right)
	TextView mTxtRight;
	@BindView(R.id.list)
	ListView mList;
	@BindView(R.id.sidebar)
	EaseSidebar mSidebar;
	private PickContactAdapter contactAdapter;
	/** members already in the group */
	private List<String> existMembers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_group_pick_contacts);
		ButterKnife.bind(this);

		String groupId = getIntent().getStringExtra("groupId");
		if (groupId == null) {// create new group
			isCreatingNewGroup = true;
		} else {
			// get members of the group
			EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
			existMembers = group.getMembers();
		}
		if (existMembers == null)
			existMembers = new ArrayList<String>();
		initView();
		initData();
	}

	private void initData() {
		// get contact list
		final List<User> alluserList = new ArrayList<User>();
		for (User user : SuperWeChatHelper.getInstance().getAppContactList().values()) {
			if (!user.getMUserName().equals(Constant.NEW_FRIENDS_USERNAME) &
					!user.getMUserName().equals(Constant.GROUP_USERNAME) &
					!user.getMUserName().equals(Constant.CHAT_ROOM) &
					!user.getMUserName().equals(Constant.CHAT_ROBOT) &
					!user.getMUserName().equals(EMClient.getInstance().getCurrentUser()))
				alluserList.add(user);
		}
		// sort the list
		// sort the list
		Collections.sort(alluserList, new Comparator<User>() {

            @Override
			public int compare(User lhs, User rhs) {
                if(lhs.getInitialLetter().equals(rhs.getInitialLetter())){
					return lhs.getMUserNick().compareTo(rhs.getMUserNick());
                }else{
                    if("#".equals(lhs.getInitialLetter())){
                        return 1;
                    }else if("#".equals(rhs.getInitialLetter())){
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }
                
            }
        });


		contactAdapter = new PickContactAdapter(this, R.layout.em_row_contact_with_checkbox, alluserList);
		mList.setAdapter(contactAdapter);
		mSidebar.setListView(mList);
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
				               checkBox.toggle();
			}
		});
	}
	private void initView() {
		mImgBack.setVisibility(View.VISIBLE);
		mTxtTitle.setVisibility(View.VISIBLE);
		mTxtTitle.setText(getString(R.string.Select_the_contact));
		mTxtRight.setVisibility(View.VISIBLE);
		mTxtRight.setText(getString(R.string.button_save));
	}

	public void save() {
		List<String> var = getToBeAddMembers();
		setResult(RESULT_OK, new Intent().putExtra("newmembers", var.toArray(new String[var.size()])));
		finish();
	}

	/**
	 +     * get selected members
	 +     *
	 +     * @return
	 +     */
	private List<String> getToBeAddMembers() {
		List<String> members = new ArrayList<String>();
		int length = contactAdapter.isCheckedArray.length;
		for (int i = 0; i < length; i++) {
			String username = contactAdapter.getItem(i).getUsername();
			if (contactAdapter.isCheckedArray[i] && !existMembers.contains(username)) {
				members.add(username);
			}
		}

		return members;
	}

	@OnClick({R.id.img_back, R.id.txt_right})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.img_back:
				MFGT.finish(this);
				break;
			case R.id.txt_right:
				save();
				break;
		}
	}

	/**
	 +     * adapter
	 +     */
	private class PickContactAdapter extends EaseContactAdapter {
		private boolean[] isCheckedArray;
		public PickContactAdapter(Context context, int resource, List<User> users) {
			super(context, resource, users);
			isCheckedArray = new boolean[users.size()];
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			final String username = getItem(position).getUsername();
			final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
			ImageView avatarView = (ImageView) view.findViewById(R.id.avatar);
			TextView nameView = (TextView) view.findViewById(R.id.name);
			if (checkBox != null) {
				if (existMembers != null && existMembers.contains(username)) {
					checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_gray_selector);
				} else {
                    checkBox.setButtonDrawable(R.drawable.em_checkbox_bg_gray_selector);
                }


				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// check the exist members
						if (existMembers.contains(username)) {
							isChecked = true;
							checkBox.setChecked(true);
						}
						isCheckedArray[position] = isChecked;
					}
				});
				// keep exist members checked
				if (existMembers.contains(username)) {
					checkBox.setChecked(true);
					isCheckedArray[position] = true;
				} else {
					checkBox.setChecked(isCheckedArray[position]);
				}
			}

			return view;
		}
	}

	public void back(View view) {
		finish();
	}
}
