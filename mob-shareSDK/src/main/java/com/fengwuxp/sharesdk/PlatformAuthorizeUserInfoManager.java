package com.fengwuxp.sharesdk;

import android.app.Activity;
import android.text.TextUtils;

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


    public void aliPayAuthorize(String authorizeInfo) {
        doAuthorize(SocialType.ALI_PAY, authorizeInfo, this.activity, this.platformActionListener);
    }


    public void weChatAuthorize(String authorizeInfo) {
        Platform weiXin = ShareSDK.getPlatform(Wechat.NAME);
        doAuthorize(SocialType.WE_CHAT, authorizeInfo, this.activity, this.platformActionListener);
    }

    public void sinaAuthorize(String authorizeInfo) {
        doAuthorize(SocialType.SINA_WEI_BO, authorizeInfo, this.activity, this.platformActionListener);
    }

    public void tencentWeiBoAuthorize(String authorizeInfo) {
        doAuthorize(SocialType.TENCENT_WEI_BO, authorizeInfo, this.activity, this.platformActionListener);
    }


    public void qqShareAuthorize(String authorizeInfo) {
        doAuthorize(SocialType.QQ, authorizeInfo, this.activity, this.platformActionListener);
    }


    public void emailAuthorize(String authorizeInfo) {
        doAuthorize(SocialType.E_MAIL, authorizeInfo, this.activity, this.platformActionListener);
    }

    public void textAuthorize(String authorizeInfo) {
        doAuthorize(SocialType.SHORE_MESSAGE, authorizeInfo, this.activity, this.platformActionListener);
    }

    /**
     * 授权
     *
     * @param socialType
     * @param authorizeInfo
     * @param listener
     */
    public void doAuthorize(SocialType socialType,
                            String authorizeInfo,
                            Activity activity, PlatformActionListener listener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        platform.setPlatformActionListener(listener);
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
            listener.onCancel(platform, -1);
            return;
        }
        ShareSDK.setActivity(activity);
        ShareSDK.setEnableAuthTag(true);
        if (TextUtils.isEmpty(authorizeInfo)) {
            platform.authorize();
        } else {
            platform.authorize(new String[]{authorizeInfo});
        }
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
