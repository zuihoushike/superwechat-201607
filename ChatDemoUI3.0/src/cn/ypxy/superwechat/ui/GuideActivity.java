package cn.ypxy.superwechat.ui;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ypxy.superchat.R;
import cn.ypxy.superwechat.utils.MFGT;


public class GuideActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.img_login, R.id.img_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_login:
                MFGT.gotoLogin(this);
                break;
            case R.id.img_register:
                MFGT.gotoRegister(this);
                break;
        }
    }

}
