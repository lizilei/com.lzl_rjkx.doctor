package com.lzl_rjkx.doctor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzl_rjkx.doctor.R;
import com.lzl_rjkx.doctor.base.MyApp;
import com.lzl_rjkx.doctor.bean.Academic;
import com.lzl_rjkx.doctor.bean.Article;
import com.lzl_rjkx.doctor.bean.Topic;
import com.lzl_rjkx.doctor.bean.Video;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.utils.MyParams;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzl_os on 16/3/3.
 */
public class CollectFragment extends Fragment {
    private ListView lv;
    private String appToken;
    private int index;

    private List<Topic.DataEntity.ListEntity> topicList = new ArrayList<>();
    private List<Article.DataEntity.ListEntity> articleList = new ArrayList<>();
    private List<Video.DataEntity.ListEntity> videoList = new ArrayList<>();
    private List<Academic.DataEntity.ListEntity> academicList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        index = getArguments().getInt("index", 0);
        View view = inflater.inflate(R.layout.common_listview, null);
        lv = (ListView) view.findViewById(R.id.common_lv);
        appToken = MyApp.getInstance().getAppToken();

        queryNewsFavList(appToken, index);
        return view;
    }

    public void queryNewsFavList(String appToken, int index) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_QUERYNEWSFAVLIST, appToken);
        params.addBodyParameter("userid", Interface.getUserId(getActivity()));
        params.addBodyParameter("pager", "1");
        params.addBodyParameter("msgtype", index + "");
        params.addBodyParameter("usertype", "3");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
