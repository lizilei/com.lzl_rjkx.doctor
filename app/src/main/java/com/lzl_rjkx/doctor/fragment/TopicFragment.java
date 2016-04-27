package com.lzl_rjkx.doctor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzl_rjkx.doctor.R;
import com.lzl_rjkx.doctor.activity.ColumnDesActivity;
import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.base.MyApp;
import com.lzl_rjkx.doctor.bean.Topic;
import com.lzl_rjkx.doctor.bean.Topic.DataEntity.ListEntity;
import com.lzl_rjkx.doctor.bean.UserDao;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.customview.RefreshLayout;
import com.lzl_rjkx.doctor.utils.AppUtils;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;
import com.lzl_rjkx.doctor.utils.StringUtils;
import com.lzl_rjkx.doctor.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzl_os on 16/3/14.
 */
public class TopicFragment extends Fragment {
    @ViewInject(R.id.swipe_container)
    private RefreshLayout mRefreshLayout;
    @ViewInject(R.id.common_lv)
    private ListView lv;

    private List<ListEntity> tList = new ArrayList<>();
    private CommonAdapter<ListEntity> adapter;
    private int currentPage = 1;
    private String appToken;
    private boolean isRefresh = false;
    private View rootView;
    private UserDao dao = new UserDao(x.getDb(MyApp.getDaoConfig()));

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("type", 0) == 1) {
                currentPage = 1;
                initData(appToken, currentPage);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.refresh_listview, null, false);
        x.view().inject(this, rootView);
        appToken = MyApp.getInstance().getAppToken();
        initData(appToken, currentPage);

        IntentFilter filter = new IntentFilter("com.rjkx.public");
        getActivity().registerReceiver(receiver, filter);

        mRefreshLayout.setChildView(lv);
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);

        adapter = new CommonAdapter<ListEntity>(getActivity(), R.layout.article_item) {
            @Override
            public void convert(final ViewHolder helper, final ListEntity item) {
                ImageView article_del = helper.getView(R.id.article_del);
                if (item.getOpName().equals(PreferenceUtils.getPrefString(getActivity(), "docName", ""))) {
                    article_del.setVisibility(View.VISIBLE);
                    article_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("确定要删除吗？");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delNews(item.getMsgId(), helper.getPosition());
                                    dialog.dismiss();
                                    dao.deleteByid(item.getMsgId());

                                }
                            });

                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.create().show();
                        }
                    });
                } else {
                    article_del.setVisibility(View.GONE);
                }

                helper.setImageByUrl(R.id.article_icon, item.getMsgImg());
                helper.setText(R.id.article_author, item.getOpName());
                helper.setText(R.id.article_pubTime, item.getSTime());
                helper.setText(R.id.article_title, item.getMsgTitle());
                helper.setText(R.id.article_content, StringUtils.getString(item.getMsgContent()));
            }
        };

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ColumnDesActivity.class);
                intent.putExtra("msgId", tList.get(position).getMsgId());
                if (AppUtils.isFastDoubleClick()) {
                    return;
                } else {
                    startActivity(intent);
                }
            }
        });


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                currentPage++;
                initData(appToken, currentPage);
            }
        });
        return rootView;
    }

    public void delNews(final int msgId, final int position) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_DELNEWS, appToken);
        params.addBodyParameter("userid", Interface.getUserId(getActivity()));
        params.addBodyParameter("usertype", "3");
        params.addParameter("msgId", msgId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject o = new JSONObject(result).getJSONObject("data");
                    String code = o.getString("msg");
                    if (code.equals("OK")) {
                        tList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.i(ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void initData(final String appToken, final int currentPage) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_QUERYNEWSLIST, appToken);
        params.addBodyParameter("usertype", "3");
        params.addBodyParameter("msgtype", "1");
        params.addParameter("page", currentPage);
        LogUtil.d("--" + appToken);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Topic topic = new Gson().fromJson(result, Topic.class);

                if (topic.getCode() == -10) {
                    mRefreshLayout.setRefreshing(false);
                    ToastUtils.showToast(getActivity(), "没有更多数据了...");
                    return;
                }

                if (topic.getCode() == -6) {
                    mRefreshLayout.setRefreshing(false);
                    initData(MyApp.getInstance().getAppToken(), currentPage);
                    return;
                }

                if (isRefresh) {
                    tList.addAll(0, topic.getData().getList());
                } else {
                    if (tList.size() != 0) {
                        tList.clear();
                    }
                    tList.addAll(topic.getData().getList());
                }
                handler.sendEmptyMessage(1);
                adapter.getDatas(tList);
                adapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
