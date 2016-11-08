package cn.ypxy.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.hyphenate.easeui.domain.User;

import cn.ypxy.superchat.R;
import cn.ypxy.superwechat.I;
import cn.ypxy.superwechat.ui.FriendProfileActivity;
import cn.ypxy.superwechat.ui.LoginActivity;
import cn.ypxy.superwechat.ui.RegisterActivity;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
          context.startActivity(intent);
          ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoLogin(Activity context){
        startActivity(context, LoginActivity.class);
    }

    public static void gotoRegister(Activity context){
        startActivity(context, RegisterActivity.class);
    }

    public static void gotoFriendProfile(Activity context, User user){
        Intent intent = new Intent();
        intent.setClass(context,FriendProfileActivity.class);
        intent.putExtra(I.User.USER_NAME, (Parcelable) user);
        startActivity(context, intent);
    }
}