package cn.ypxy.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ypxy.superchat.R;
import cn.ypxy.superwechat.I;
import cn.ypxy.superwechat.utils.MFGT;

/**
 * Created by 最后时刻 on 2016/11/8.
 */

public class FriendProfileActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.profile_image)
    ImageView mProfileImage;
    @BindView(R.id.tv_userinfo_nick)
    TextView mTvUserinfoNick;
    @BindView(R.id.tv_userinfo_name)
    TextView mTvUserinfoName;
    User user = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        if (user==null){
            MFGT.finish(this);
        }
        initView();
    }

    private void initView() {
        mImgBack.setVisibility(View.VISIBLE);
        mTxtTitle.setVisibility(View.VISIBLE);
        mTxtTitle.setText(getString(R.string.userinfo_txt_profile));
        setUserInfo();
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this,user.getMUserName(),mProfileImage);
        EaseUserUtils.setAppUserNick(user.getMUserName(),mTvUserinfoNick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(),mTvUserinfoName);
    }
    @OnClick(R.id.img_back)
    public void onClick(){

    }
}
