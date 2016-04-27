package com.lzl_rjkx.doctor.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzl_rjkx.doctor.R;
import com.lzl_rjkx.doctor.activity.ColumnDesActivity;
import com.lzl_rjkx.doctor.activity.VideoDetilsActicity;
import com.lzl_rjkx.doctor.adapter.CommonAdapter;
import com.lzl_rjkx.doctor.adapter.MyHeadPagerAdapter;
import com.lzl_rjkx.doctor.adapter.ViewHolder;
import com.lzl_rjkx.doctor.base.MyApp;
import com.lzl_rjkx.doctor.bean.Advert;
import com.lzl_rjkx.doctor.bean.HotArticle;
import com.lzl_rjkx.doctor.bean.HotTopic;
import com.lzl_rjkx.doctor.bean.HotTopic.DataEntity.ListEntity;
import com.lzl_rjkx.doctor.bean.HotVideo;
import com.lzl_rjkx.doctor.bean.Patient;
import com.lzl_rjkx.doctor.bean.Patient.DataEntity.OrderEntity;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.customview.AutoScrollViewPager;
import com.lzl_rjkx.doctor.utils.AppUtils;
import com.lzl_rjkx.doctor.utils.FileUtils;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.MyRequestUtils;
import com.lzl_rjkx.doctor.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzl_os on 16/1/27.
 */
public class HeadFragment extends Fragment implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.head_vp)
    private AutoScrollViewPager vp;
    private ArrayList<ImageView> data = new ArrayList<>();
    private MyHeadPagerAdapter pagerAdapter;

    @ViewInject(R.id.grid_topic)
    private GridView gridView;
    @ViewInject(R.id.lv_order)
    private ListView lv_order;
    @ViewInject(R.id.lv_article)
    private ListView lv_article;
    @ViewInject(R.id.lv_video)
    private ListView lv_video;
    @ViewInject(R.id.myLayout)
    private LinearLayout myLayout;
    @ViewInject(R.id.mText)
    private TextView mText;
    @ViewInject(R.id.text1)
    private TextView text1;
    @ViewInject(R.id.text2)
    private TextView text2;
    @ViewInject(R.id.text)
    private TextView text;
    private int prePosition = 0;

    private List<Advert.DataBean.AdvBean> bList = new ArrayList<>();
    private List<OrderEntity> tList = new ArrayList<>();
    private CommonAdapter<OrderEntity> adapter;

    private List<ListEntity> hotList = new ArrayList<>();
    private List<HotArticle.DataBean.ListBean> hotArticle = new ArrayList<>();
    private List<HotVideo.DataEntity.ListEntity> hotVideo = new ArrayList<>();
    private CommonAdapter<ListEntity> hotAdapter;
    private CommonAdapter<HotArticle.DataBean.ListBean> articleAdapter;
    private CommonAdapter<HotVideo.DataEntity.ListEntity> videoAdapter;

    private String appToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_head, null);
        x.view().inject(this, v);
        EventBus.getDefault().register(this);
        appToken = MyApp.getInstance().getAppToken();

        pagerAdapter = new MyHeadPagerAdapter(data);
        vp.setAdapter(pagerAdapter);
        vp.setInterval(3000);
        vp.startAutoScroll();
        vp.addOnPageChangeListener(this);
        vp.setFocusable(true);


        adapter = new CommonAdapter<OrderEntity>(getActivity(), R.layout.order_item) {
            @Override
            public void convert(ViewHolder helper, OrderEntity item) {
                helper.setText(R.id.patient_name, item.getPName());
                helper.setText(R.id.patient_vip, item.getVip() == 0 ? "精准预约" : "vip会诊");
                String state = null;
                int a = item.getOStatus();
                if (a == 3)
                    state = "待就诊";
                if (a == 4)
                    state = "已就诊";
                if (a == 5)
                    state = "已评价";
                helper.setText(R.id.patient_state, state);
                helper.setText(R.id.patient_time, "预约时间：" + item.getSTime());
                helper.setText(R.id.patient_disease, "疾病：" + item.getPSick());
                helper.setText(R.id.patient_describe, "病情描述：" + item.getPDesc());
            }
        };
        lv_order.setAdapter(adapter);
        lv_order.setFocusable(false);
        lv_order.setEmptyView(mText);

        hotAdapter = new CommonAdapter<ListEntity>(getActivity(), R.layout.headgrid_item) {
            @Override
            public void convert(ViewHolder helper, ListEntity item) {
                helper.setImageByUrl(R.id.grid_img, item.getMsgImg());
                helper.setText(R.id.grid_content, StringUtils.getString(item.getMsgContent()));
            }
        };
        gridView.setAdapter(hotAdapter);
        gridView.setFocusable(false);
        gridView.setEmptyView(text);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ColumnDesActivity.class);
                intent.putExtra("msgId", hotList.get(position).getMsgId());
                if (AppUtils.isFastDoubleClick()) {
                    return;
                } else {
                    startActivity(intent);
                }
            }
        });

        articleAdapter = new CommonAdapter<HotArticle.DataBean.ListBean>(getActivity(), R.layout.article_item) {
            @Override
            public void convert(ViewHolder helper, HotArticle.DataBean.ListBean item) {
                helper.setImageByUrl(R.id.article_icon, item.getMsgImg());
                helper.setText(R.id.article_author, item.getOpName());
                helper.setText(R.id.article_pubTime, item.getSTime());
                helper.setText(R.id.article_title, item.getMsgTitle());
                helper.setText(R.id.article_content, StringUtils.getString(item.getMsgContent()));
            }
        };
        lv_article.setAdapter(articleAdapter);
        lv_article.setEmptyView(text1);
        lv_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ColumnDesActivity.class);
                intent.putExtra("msgId", hotArticle.get(position).getMsgId());
                if (AppUtils.isFastDoubleClick()) {
                    return;
                } else {
                    startActivity(intent);
                }
            }
        });

        videoAdapter = new CommonAdapter<HotVideo.DataEntity.ListEntity>(getActivity(), R.layout.article_item) {
            @Override
            public void convert(ViewHolder helper, HotVideo.DataEntity.ListEntity item) {
                helper.setImageByUrl(R.id.article_icon, item.getMsgImg());
                helper.setText(R.id.article_author, item.getOpName());
                helper.setText(R.id.article_pubTime, item.getSTime());
                helper.setText(R.id.article_title, item.getMsgTitle());
                helper.setText(R.id.article_content, StringUtils.getString(item.getMsgContent()));
            }
        };
        lv_video.setAdapter(videoAdapter);
        lv_video.setEmptyView(text2);
        lv_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoDetilsActicity.class);
                intent.putExtra("msgId", hotVideo.get(position).getMsgId());
                if (AppUtils.isFastDoubleClick()) {
                    return;
                } else {
                    startActivity(intent);
                }
            }
        });


        getBanner(appToken);
        getTodayOrder(appToken);
        getHotsNews(appToken);
        getHotsArticle(appToken);
        getHotsVideo(appToken);

        return v;
    }

    @Event(value = {R.id.article_all, R.id.video_all})
    private void onClick(View v) {
        Intent intent = new Intent("com.action.all");
        switch (v.getId()) {
            case R.id.article_all:
                break;
            case R.id.video_all:
                break;
        }
        getActivity().sendBroadcast(intent);
    }

    public void getBanner(String appToken) {
        RequestParams advParams = MyParams.getParams(HttpConstants.HTTP_QUERYNEWSADVERT, appToken);
        advParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        advParams.addBodyParameter("usertype", "3");
        x.http().post(advParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Advert advert = new Gson().fromJson(result, Advert.class);
                bList = advert.getData().getAdv();
                int i = 0;
                for (final Advert.DataBean.AdvBean bean : bList) {
                    final ImageView iv = new ImageView(getActivity());
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);

                    File file = new File(FileUtils.SDPATH + FileUtils.getFileName(bean.getNewsImg()));
                    if (file.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        if (bitmap != null && FileUtils.getFileSize(file.getName()) != 0) {
                            iv.setImageBitmap(bitmap);
                        } else {
                            iv.setImageResource(R.mipmap.fail_loading);
                        }
                    } else {
                        x.image().loadDrawable(bean.getNewsImg(), null, new CommonCallback<Drawable>() {
                            @Override
                            public void onSuccess(Drawable result) {
                                if (result != null) {
                                    iv.setImageDrawable(result);
                                    FileUtils.saveBitmap(((BitmapDrawable) result).getBitmap(), FileUtils.getFileName(bean.getNewsImg()));
                                } else {
                                    iv.setImageResource(R.mipmap.fail_loading);
                                }
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

                    data.add(iv);
                    pagerAdapter.notifyDataSetChanged();

                    View view = new View(getActivity());
                    LayoutParams params = new LayoutParams(20, 20);
                    params.leftMargin = 5;
                    view.setLayoutParams(params);
                    myLayout.addView(view);
                    if (i == 0) {
                        myLayout.getChildAt(i).setBackgroundResource(R.drawable.dot_focused);
                    } else {
                        myLayout.getChildAt(i).setBackgroundResource(R.drawable.dot_normal);
                    }
                    i++;
                }
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

    public void getTodayOrder(String appToken) {
        RequestParams patParams = MyParams.getParams(HttpConstants.HTTP_QUERYDOCTOR_TODAYORDER, appToken);
        patParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        MyRequestUtils.requestAsObject(patParams, new Patient());
    }

    public void getHotsNews(String appToken) {
        RequestParams hotParams = MyParams.getParams(HttpConstants.HTTP_QUERYHOTSNEWS, appToken);
        hotParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        hotParams.addBodyParameter("usertype", "3");
        hotParams.addBodyParameter("msgtype", "1");
        MyRequestUtils.requestAsObject(hotParams, new HotTopic());
    }

    public void getHotsArticle(String appToken) {
        RequestParams hotParams = MyParams.getParams(HttpConstants.HTTP_QUERYHOTSNEWS, appToken);
        hotParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        hotParams.addBodyParameter("usertype", "3");
        hotParams.addBodyParameter("msgtype", "2");
        x.http().get(hotParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                HotArticle article = new Gson().fromJson(result, HotArticle.class);
                for (int i = 0; i < 3; i++) {
                    hotArticle.add(article.getData().getList().get(i));
                    articleAdapter.getDatas(hotArticle);
                    articleAdapter.notifyDataSetChanged();
                }
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

    public void getHotsVideo(String appToken) {
        RequestParams hotParams = MyParams.getParams(HttpConstants.HTTP_QUERYHOTSNEWS, appToken);
        hotParams.addBodyParameter("userid", Interface.getUserId(getActivity()));
        hotParams.addBodyParameter("usertype", "3");
        hotParams.addBodyParameter("msgtype", "3");
        x.http().get(hotParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                hotVideo = new Gson().fromJson(result, HotVideo.class).getData().getList();
                videoAdapter.getDatas(hotVideo);
                videoAdapter.notifyDataSetChanged();
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


    @Subscribe
    public void onEvent(Patient patient) {
        tList = patient.getData().getOrder();
        adapter.getDatas(tList);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(HotTopic topic) {
        for (int i = 0; i < 2; i++) {
            hotList.add(topic.getData().getList().get(i));
            hotAdapter.getDatas(hotList);
            hotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    /**
     * ViewPager注册监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        myLayout.getChildAt(position).setBackgroundResource(R.drawable.dot_focused);
        myLayout.getChildAt(prePosition).setBackgroundResource(R.drawable.dot_normal);
        prePosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}