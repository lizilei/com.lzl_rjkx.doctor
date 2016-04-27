package com.lzl_rjkx.doctor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lzl_rjkx.doctor.R;
import com.lzl_rjkx.doctor.activity.AdviceActivity;
import com.lzl_rjkx.doctor.activity.CollectActivity;
import com.lzl_rjkx.doctor.activity.GoldIconsActivity;
import com.lzl_rjkx.doctor.activity.LoginActivity;
import com.lzl_rjkx.doctor.activity.SelfInfoActivity;
import com.lzl_rjkx.doctor.customview.CircleImageView;
import com.lzl_rjkx.doctor.utils.AppUtils;
import com.lzl_rjkx.doctor.utils.FileUtils;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzl_os on 16/1/27.
 */
public class MineFragment extends Fragment {
    private ListView lv;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> data = new ArrayList<>();
    private TextView btn_alter;
    private CircleImageView iv_icon;

    private TextView tv_userName, tv_gender, tv_age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_mine, null);
        lv = (ListView) v.findViewById(R.id.common_lv);
        btn_alter = (TextView) v.findViewById(R.id.btn_alter);
        iv_icon = (CircleImageView) v.findViewById(R.id.iv_icon);
        tv_userName = (TextView) v.findViewById(R.id.tv_userName);
        tv_gender = (TextView) v.findViewById(R.id.tv_gender);
        tv_age = (TextView) v.findViewById(R.id.tv_age);
        btn_alter.setBackgroundResource(R.drawable.common_shape_input);

        Bitmap bm = FileUtils.getBitmap(PreferenceUtils.getPrefString(getActivity(), "iconUrl", ""));
        iv_icon.setImageBitmap(bm);
        tv_userName.setText(PreferenceUtils.getPrefString(getActivity(), "docName", ""));
        tv_gender.setText(PreferenceUtils.getPrefString(getActivity(), "docSex", ""));
        tv_age.setText(PreferenceUtils.getPrefString(getActivity(), "docAge", ""));

        IntentFilter filter = new IntentFilter("com.broadcast.icon");
        getActivity().registerReceiver(receiver, filter);

        final String[] items = getResources().getStringArray(R.array.mine);
        int[] imgs = {R.mipmap.mine_collect, R.mipmap.mine_clinic, R.mipmap.mine_money, R.mipmap.mine_advice, R.mipmap.mine_about};
        for (int i = 0; i < imgs.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", items[i]);
            map.put("img", imgs[i]);
            data.add(map);
        }

        adapter = new SimpleAdapter(getActivity(), data, R.layout.mine_item, new String[]{"name", "img"}, new int[]{R.id.mine_textView, R.id.mine_former});

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Intent intent;
                if (position == 0) {
                    intent = new Intent(getActivity(), CollectActivity.class);
                } else if (position == 2) {
                    intent = new Intent(getActivity(), GoldIconsActivity.class);
                } else if (position == 3) {
                    intent = new Intent(getActivity(), AdviceActivity.class);
                } else if (position == 4) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    PreferenceUtils.setPrefBoolean(getActivity(), "firstLogin", true);
                    getActivity().finish();
                } else {
                    intent = new Intent(getActivity(), SelfInfoActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("title", items[position]);
                }

                if (AppUtils.isFastDoubleClick()) {
                    return;
                } else {
                    startActivity(intent);
                }
            }
        });

        btn_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelfInfoActivity.class);
                intent.putExtra("title", "个人中心");
                intent.putExtra("position", 5);
                if (AppUtils.isFastDoubleClick()) {
                    return;
                } else {
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.broadcast.icon"))
                iv_icon.setImageBitmap(FileUtils.getBitmap(PreferenceUtils.getPrefString(getActivity(), "iconUrl", "")));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
