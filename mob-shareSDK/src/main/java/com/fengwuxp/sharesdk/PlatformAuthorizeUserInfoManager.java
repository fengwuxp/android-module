package com.fengwuxp.sharesdk;

import android.app.Activity;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * 平台授权管理器
 */
public class PlatformAuthorizeUserInfoManager {


    private Activity activity;

    private PlatformActionListener platformActionListener;


    public PlatformAuthorizeUserInfoManager() {
    }


    public void aliPayAuthorize() {
        doAuthorize(SocialType.ALI_PAY, this.activity, this.platformActionListener);
    }


    public void weChatAuthorize() {
        Platform weiXin = ShareSDK.getPlatform(Wechat.NAME);
        doAuthorize(SocialType.WE_CHAT, this.activity, this.platformActionListener);
    }

    public void sinaAuthorize() {
        doAuthorize(SocialType.SINA_WEI_BO, this.activity, this.platformActionListener);
    }

    public void tencentWeiBoAuthorize() {
        doAuthorize(SocialType.TENCENT_WEI_BO, this.activity, this.platformActionListener);
    }


    public void qqShareAuthorize() {
        doAuthorize(SocialType.QQ, this.activity, this.platformActionListener);
    }


    public void emailAuthorize() {
        doAuthorize(SocialType.E_MAIL, this.activity, this.platformActionListener);
    }

    public void textAuthorize() {
        doAuthorize(SocialType.SHORE_MESSAGE, this.activity, this.platformActionListener);
    }

    /**
     * 授权
     *
     * @param socialType
     * @param activity
     * @param listener
     */
    public void doAuthorize(SocialType socialType, Activity activity, PlatformActionListener listener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        platform.setPlatformActionListener(listener);
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
            listener.onCancel(platform, -1);
            return;
        }
        ShareSDK.setActivity(activity);
        platform.authorize();
    }


    /**
     * 获取用户信息
     *
     * @param socialTyp
     * @param activity
     * @param account   可以为空
     * @param listener
     */
    public void doUserInfo(SocialType socialTyp, Activity activity, String account, PlatformActionListener listener) {
        Platform platform = ShareSDK.getPlatform(socialTyp.getName());
        ShareSDK.setActivity(activity);
        platform.setPlatformActionListener(listener);
        platform.showUser(account);
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPlatformActionListener(PlatformActionListener platformActionListener) {
        this.platformActionListener = platformActionListener;
    }
}
