package com.lzl_rjkx.doctor.constants;

import android.content.Context;

import com.lzl_rjkx.doctor.utils.PreferenceUtils;

/**
 * Created by lzl_os on 16/3/9.
 */
public class Interface {

    public static String getUserId(Context context) {
        return PreferenceUtils.getPrefInt(context, "docId", 10) + "";
    }
}
