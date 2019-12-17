package com.fengwuxp.sharesdk;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;


/**
 * 平台分享管理器
 */
public class PlatformShareManager {


    /**
     * 分享
     *
     * @param socialType
     * @param shareParams
     * @param platformActionListener
     */
    public void share(SocialType socialType,
                      Platform.ShareParams shareParams,
                      PlatformActionListener platformActionListener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        shareParams.setScence(0);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }


    /**
     * 分享文字
     *
     * @param socialType
     * @param title
     * @param text
     * @param platformActionListener
     */
    public void shareText(SocialType socialType,
                          String title,
                          String text,
                          PlatformActionListener platformActionListener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(title);
        shareParams.setText(text);
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setScence(0);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

    /**
     * 分享本地图片
     *
     * @param socialType
     * @param title
     * @param text
     * @param imagePath
     * @param platformActionListener
     */
    public void shareLocalImage(SocialType socialType,
                                String title,
                                String text,
                                String imagePath,
                                PlatformActionListener platformActionListener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(title);
        shareParams.setText(text);
        shareParams.setImagePath(imagePath);
        shareParams.setShareType(Platform.SHARE_IMAGE);
        shareParams.setScence(0);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }


    /**
     * 分享远程图片
     *
     * @param socialType
     * @param title
     * @param text
     * @param imageUrl
     * @param platformActionListener
     */
    public void shareRemoteImage(SocialType socialType,
                                 String title,
                                 String text,
                                 String imageUrl,
                                 PlatformActionListener platformActionListener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(title);
        shareParams.setText(text);
        shareParams.setImageUrl(imageUrl);
        shareParams.setShareType(Platform.SHARE_IMAGE);
        shareParams.setScence(0);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

    /**
     * 分享网页
     *
     * @param socialType
     * @param title
     * @param text
     * @param url
     * @param imageUrl
     * @param platformActionListener
     */
    public void shareWebPage(SocialType socialType,
                             String title,
                             String text,
                             String url,
                             String imageUrl,
                             PlatformActionListener platformActionListener) {
        Platform platform = ShareSDK.getPlatform(socialType.getName());
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(title);
        shareParams.setText(text);
        shareParams.setUrl(url);
        shareParams.setImageUrl(imageUrl);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setScence(0);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }


}
