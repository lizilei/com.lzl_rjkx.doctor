package com.lzl_rjkx.doctor.utils;

import android.text.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzl_os on 16/3/17.
 */
public class StringUtils {

    public final static String getString(String str) {
        if (str.contains("src")) {
            return str.split("<")[0];
        } else {
            if (str.contains("<"))
                return Html.fromHtml(str).toString().trim();
            else
                return str;
        }
    }

    public final static List<String> getUrlFromString(String str) {
        List<String> imgs = new ArrayList<>();
        String[] ss = str.split("'");
        for (String s : ss) {
            if (s.contains("http")) {
                imgs.add(s);
            }
        }
        return imgs;
    }
}
