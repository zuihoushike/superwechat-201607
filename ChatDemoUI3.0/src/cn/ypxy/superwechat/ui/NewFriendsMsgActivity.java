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

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ypxy.superchat.R;
import cn.ypxy.superwechat.adapter.NewFriendsMsgAdapter;
import cn.ypxy.superwechat.db.InviteMessgeDao;
import cn.ypxy.superwechat.domain.InviteMessage;
import cn.ypxy.superwechat.utils.MFGT;

import java.util.List;

/**
 * Application and notification
 *
 */
public class NewFriendsMsgActivity extends BaseActivity {
	@BindView(R.id.img_back)
	ImageView mImgBack;
	@BindView(R.id.txt_title)
	TextView mTxtTitle;
	@BindView(R.id.list)
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_new_friends_msg);
		ButterKnife.bind(this);
		mImgBack.setVisibility(View.VISIBLE);
		mTxtTitle.setVisibility(View.VISIBLE);
		mTxtTitle.setText(getString(R.string.recommended_friends));
		InviteMessgeDao dao = new InviteMessgeDao(this);
		List<InviteMessage> msgs = dao.getMessagesList();

		NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
		listView.setAdapter(adapter);
		dao.saveUnreadMessageCount(0);


	}

	@OnClick(R.id.img_back)
	public void onClick() {
		MFGT.finish(this);
	}
}
