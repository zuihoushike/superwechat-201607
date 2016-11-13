/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
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
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ypxy.superchat.R;
import cn.ypxy.superwechat.Constant;
import cn.ypxy.superwechat.adapter.GroupAdapter;
import cn.ypxy.superwechat.utils.MFGT;

public class GroupsActivity extends BaseActivity {
	public static final String TAG = "GroupsActivity";
	public static GroupsActivity instance;
	protected List<EMGroup> grouplist;
	@BindView(R.id.img_back)
	ImageView mImgBack;
	@BindView(R.id.txt_title)
	TextView mTxtTitle;
	@BindView(R.id.list)
	ListView mList;
	@BindView(R.id.swipe_layout)
	SwipeRefreshLayout mSwipeLayout;
	private GroupAdapter groupAdapter;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mSwipeLayout.setRefreshing(false);
			switch (msg.what) {
				case 0:
					refresh();
					break;
				case 1:
					Toast.makeText(GroupsActivity.this, R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
					break;

				default:
					break;
			}
		}
	};
	private InputMethodManager inputMethodManager;
	private View progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_fragment_groups);
		ButterKnife.bind(this);

		instance = this;
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		grouplist = EMClient.getInstance().groupManager().getAllGroups();

		initView();
		setListener();
	}

	private void setListener() {
		//pull down to refresh
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new Thread() {
					@Override
					public void run() {
						try {
							EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
							handler.sendEmptyMessage(0);
						} catch (HyphenateException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(1);
						}
					}
				}.start();
			}
		});

		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1) {
					// create a new group
					MFGT.gotoCreateNewGroup(GroupsActivity.this);
				} else if (position == 2) {
					// join a public group
					MFGT.gotoPublicGroup(GroupsActivity.this);
				} else {
					// enter group chat
					Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
					// it is group chat
					intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
					intent.putExtra("userId", groupAdapter.getItem(position - 3).getGroupId());
					startActivityForResult(intent, 0);
				}
			}

		});
		mList.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getCurrentFocus() != null)
						inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});
	}

	private void initView() {
		//show group list
		groupAdapter = new GroupAdapter(this, 1, grouplist);
		mList.setAdapter(groupAdapter);

		mSwipeLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);
		mImgBack.setVisibility(View.VISIBLE);
		mTxtTitle.setVisibility(View.VISIBLE);
		mTxtTitle.setText(getString(R.string.group_chat));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		refresh();
		super.onResume();
	}

	private void refresh() {
		grouplist = EMClient.getInstance().groupManager().getAllGroups();
		groupAdapter = new GroupAdapter(this, 1, grouplist);
		mList.setAdapter(groupAdapter);
		groupAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

	@OnClick(R.id.img_back)
	public void onClick() {
		MFGT.finish(this);
	}
}