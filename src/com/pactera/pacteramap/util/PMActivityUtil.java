package com.pactera.pacteramap.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pactera.pacteramap.R;

/**
 * 页面跳转管理工具
 * User: Zenos
 * Date: 13-4-24
 */
public class PMActivityUtil {
    public static final int REQUESTCODE_TABACTIVITY_BASE = 1000;

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     */
    public static void next(Activity curActivity, Class<?> nextActivity) {
        next(curActivity, nextActivity, null, -1, -1, R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param extras
     * @param reqCode
     */
    public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode) {
        next(curActivity, nextActivity, extras, reqCode, -1, R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param inAnimId
     * @param outAnimId
     */
    public static void next(Activity curActivity, Class<?> nextActivity, int inAnimId, int outAnimId) {
        next(curActivity, nextActivity, null, -1, -1, inAnimId, outAnimId);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param flag
     * @param inAnimId
     * @param outAnimId
     */
    public static void next(Activity curActivity, Class<?> nextActivity, int flag, int inAnimId, int outAnimId) {
        next(curActivity, nextActivity, null, -1, flag, inAnimId, outAnimId);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param extras
     * @param reqCode
     * @param inAnimId
     * @param outAnimId
     */
    public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode, int inAnimId, int outAnimId) {
        next(curActivity, nextActivity, extras, reqCode, -1, inAnimId, outAnimId);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param extras
     * @param reqCode
     * @param inAnimId
     * @param outAnimId
     */
    public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode, int flag, int inAnimId, int outAnimId) {
    	Intent intent = new Intent(curActivity, nextActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        if (flag != -1) {
            intent.setFlags(flag);
        }
        if (reqCode < 0) {
            curActivity.startActivity(intent);
        } else {
            curActivity.startActivityForResult(intent, reqCode);

        }
        curActivity.overridePendingTransition(inAnimId, outAnimId);
    }


    /**
     * 返回到上一个页面
     *
     * @param curActivity
     */
    public static void goBack(Activity curActivity) {
        goBack(curActivity, R.anim.in_from_left, R.anim.out_to_right);
    }

    /**
     * 返回到上一个页面
     *
     * @param curActivity
     * @param inAnimId
     * @param outAnimId
     */
    public static void goBack(Activity curActivity, int inAnimId, int outAnimId) {
        curActivity.finish();
        curActivity.overridePendingTransition(inAnimId, outAnimId);
    }

    /**
     * 返回到上一个页面并返回值
     *
     * @param curActivity
     * @param retCode
     * @param retData
     */
    public static void goBackWithResult(Activity curActivity, int retCode, Bundle retData) {
        goBackWithResult(curActivity, retCode, retData, R.anim.in_from_left, R.anim.out_to_right);
    }

    /**
     * 返回到上一个页面并返回值
     *
     * @param curActivity
     * @param retCode
     * @param retData
     * @param inAnimId
     * @param outAnimId
     */
    public static void goBackWithResult(Activity curActivity, int retCode, Bundle retData, int inAnimId, int outAnimId) {
    	Intent intent = null;
        intent = new Intent();
        if (null != retData) {
            intent.putExtras(retData);
        }
        curActivity.setResult(retCode, intent);
        curActivity.finish();
        curActivity.overridePendingTransition(inAnimId, outAnimId);
    }

    /**
     * 直接返回到指定的某个页面
     *
     * @param curActivity
     * @param backActivity
     */
    public static void backActivityWithClearTop(Activity curActivity, Class<?> backActivity) {
        backActivityWithClearTop(curActivity, backActivity, null, R.anim.in_from_left, R.anim.out_to_right);
    }

    /**
     * 直接返回到指定的某个页面
     *
     * @param curActivity
     * @param backActivity
     * @param extras
     */
    public static void backActivityWithClearTop(Activity curActivity, Class<?> backActivity, Bundle extras) {
        backActivityWithClearTop(curActivity, backActivity, extras, R.anim.in_from_left, R.anim.out_to_right);
    }

    /**
     * 直接返回到指定的某个页面
     *
     * @param curActivity
     * @param backActivity
     * @param inAnimId
     * @param outAnimId
     */
    public static void backActivityWithClearTop(Activity curActivity, Class<?> backActivity, Bundle extras, int inAnimId, int outAnimId) {
    	Intent intent = new Intent(curActivity, backActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (null != extras) {
            intent.putExtras(extras);
        }
        curActivity.startActivity(intent);
        curActivity.overridePendingTransition(inAnimId, outAnimId);
    }

}
