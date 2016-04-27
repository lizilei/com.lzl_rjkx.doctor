package com.lzl_rjkx.doctor.fragment;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzl_rjkx.doctor.R;
import com.lzl_rjkx.doctor.base.MyApp;
import com.lzl_rjkx.doctor.constants.HttpConstants;
import com.lzl_rjkx.doctor.constants.Interface;
import com.lzl_rjkx.doctor.utils.MyParams;
import com.lzl_rjkx.doctor.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/4/1.
 */
public class AdviceFragment extends Fragment {

    private EditText et_advice;
    private Button btn_advice;
    private String appToken;
    private TextView tv_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_advice, null);
        et_advice = (EditText) v.findViewById(R.id.et_advice);
        btn_advice = (Button) v.findViewById(R.id.btn_advice);
        appToken = MyApp.getInstance().getAppToken();
        et_advice.setBackgroundResource(R.drawable.common_shape_input);
        tv_number = (TextView) v.findViewById(R.id.number_our);

        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String number = "4009958591";
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("拨打" + number + "?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                        startActivity(intent);
                        dialog.dismiss();
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

        btn_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = et_advice.getText().toString().trim();
                if (content.equals("")) {
                    ToastUtils.showToast(getActivity(), "说点什么吧...");
                } else {
                    addFeedBack(appToken, content);
                }
            }
        });

        return v;
    }

    public void addFeedBack(String appToken, String content) {
        RequestParams params = MyParams.getParams(HttpConstants.HTTP_ADDFEEDBACK, appToken);
        params.addBodyParameter("userid", Interface.getUserId(getActivity()));
        params.addBodyParameter("usertype", "3");
        params.addBodyParameter("content", content);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String msg = new JSONObject(result).getJSONObject("data").getString("msg");
                    if (msg.equals("OK")) {
                        ToastUtils.showToast(getActivity(), "您的反馈我们已经收到");
                        et_advice.setText(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
}
