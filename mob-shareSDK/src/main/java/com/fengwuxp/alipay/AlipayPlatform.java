package com.fengwuxp.alipay;

import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.AuthTask;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * 支付宝登陆
 */
public class AlipayPlatform extends Alipay {

    private static final String TAG = AlipayPlatform.class.getName();

    public static final String NAME = AlipayPlatform.class.getSimpleName();

    @Override
    protected void doAuthorize(String[] strings) {
        final String authSign = strings[0];
        final PlatformActionListener platformActionListener = getPlatformActionListener();
        if (platformActionListener == null) {
            Log.e(TAG, "listener is null");
            return;
        }
        final Platform platform = this;
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(ShareSDK.getAuthActivity());
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authSign, true);
                String resultStatus = result.get("resultStatus");
                String resultCode = result.get("resultCode");
                // 判断resultStatus 为“9000”且result_code 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(resultCode, "200")) {
                    String userInfo = result.get("result");
                    Log.i(TAG,"用户信息："+userInfo);
                    String[] resultValue = userInfo.split("&");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    for (String value : resultValue) {
                        if (value.startsWith("alipay_open_id")) {
                            hashMap.put("alipayOpenId", removeBrackets(getValue("alipay_open_id=", value), true));
                            continue;
                        }
                        if (value.startsWith("auth_code")) {
                            hashMap.put("authCode", removeBrackets(getValue("auth_code=", value), true));
                            continue;
                        }
                        if (value.startsWith("result_code")) {
                            hashMap.put("resultCode", removeBrackets(getValue("result_code=", value), true));
                            continue;
                        }
                        if (value.startsWith("user_id")) {
                            hashMap.put("userId", removeBrackets(getValue("user_id=", value), true));
                            continue;
                        }
                    }


                    platformActionListener.onComplete(platform, 0, hashMap);
                    return;

                }

                // 其他状态值则为授权失败
                String errmsg = "用户取消授权";
                if (TextUtils.equals(resultStatus, "4000")) {
                    errmsg = "系统异常";
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    errmsg = "用户中途取消";
                } else if (TextUtils.equals(resultStatus, "6002")) {
                    errmsg = "网络连接出错";
                } else {
                    if (TextUtils.equals(resultCode, "1005")) {
                        errmsg = "账户已冻结,如有疑问,请联系支付宝技术支持";
                    } else if (TextUtils.equals(resultCode, "713")) {
                        errmsg = "userId 不能转换为 openId,请联系支付宝技术支持";
                    } else if (TextUtils.equals(resultCode, "202")) {
                        errmsg = "系统异常,请联系支付宝技术支持";
                    }
                }

                //最后异常抛出即可
                platformActionListener.onError(platform, -1, new RuntimeException(errmsg));

            }
        };

        // 必须异步调用
        Thread payThread = new Thread(authRunnable);
        payThread.start();
    }

    @Override
    public void showUser(String account) {
        doAuthorize(new String[]{account});
    }

    private String getValue(String header, String data) {
        return data.substring(header.length(), data.length());
    }

    private String removeBrackets(String str, boolean remove) {
        if (remove) {
            if (!TextUtils.isEmpty(str)) {
                if (str.startsWith("\"")) {
                    str = str.replaceFirst("\"", "");
                }
                if (str.endsWith("\"")) {
                    str = str.substring(0, str.length() - 1);
                }
            }
        }
        return str;
    }

}
