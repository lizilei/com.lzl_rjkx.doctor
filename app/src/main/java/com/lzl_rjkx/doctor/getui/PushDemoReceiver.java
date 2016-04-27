package com.lzl_rjkx.doctor.getui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.lzl_rjkx.doctor.R;
import com.lzl_rjkx.doctor.activity.DoctorMainActivity;
import com.lzl_rjkx.doctor.base.MyApp;
import com.lzl_rjkx.doctor.constants.Flag;
import com.lzl_rjkx.doctor.utils.AppUtils;
import com.lzl_rjkx.doctor.utils.MyAppTokenRequest;
import com.lzl_rjkx.doctor.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));


        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                //MyAppTokenRequest.requestData();

                if (payload != null) {
                    String data = new String(payload);

                    Log.d("GetuiSdkDemo", "receiver payload : " + data);

                    payloadData.append(data);
                    payloadData.append("\n");

                    judgeWithMsgType(context, payloadData.toString(), false);
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                PreferenceUtils.setPrefString(context, "clientId", cid);
                MyApp.getInstance().setCid(cid);
//                if (GetuiSdkDemoActivity.tView != null) {
//                    GetuiSdkDemoActivity.tView.setText(cid);
//                }
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void judgeWithMsgType(Context context, String json, boolean isBackground) {
        try {
            JSONObject o = new JSONObject(json);
            int msgtype = o.getInt("msgtype");
            int title = 0;
            if (o.has("title")) {
                title = o.getInt("title");
            }
            String alertTitle = o.getString("alertTitle");

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = null;
            Intent intent;
            switch (msgtype) {
                case 0:
                    LogUtil.i("强制退出登录");
                    intent = new Intent(context, DoctorMainActivity.class);
                    intent.putExtra("flag", Flag.LOGIN_IN);
                    pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    break;
                case 1:
                    LogUtil.i("医生成功预约，点击消息直接进入我的预约界面");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.ORDER);
                        pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.ORDER);
                        pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
                case 2:
                    LogUtil.i("圈子新话题,内容中直接是圈子的msgId");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.NEWTOPIC);
                        pendingIntent = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.NEWTOPIC);
                        intent.putExtra("msgId", title);
                        pendingIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
                case 3:
                    LogUtil.i("圈子新文章,内容中直接是圈子的msgId");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.NEWARTICLE);
                        pendingIntent = PendingIntent.getBroadcast(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.NEWARTICLE);
                        intent.putExtra("msgId", title);
                        pendingIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
                case 4:
                    LogUtil.i("圈子新视频,内容中直接是圈子的msgId");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.NEWVIDEO);
                        pendingIntent = PendingIntent.getBroadcast(context, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.NEWVIDEO);
                        intent.putExtra("msgId", title);
                        pendingIntent = PendingIntent.getActivity(context, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
                case 5:
                    LogUtil.i("圈子新学术,内容中直接是圈子的msgId");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.NEWACD);
                        pendingIntent = PendingIntent.getBroadcast(context, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.NEWACD);
                        intent.putExtra("msgId", title);
                        pendingIntent = PendingIntent.getActivity(context, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
                case 6:
                    LogUtil.i("圈子新回复，内容中直接是新回复消息圈子的msgId");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.NEWREPLY);
                        pendingIntent = PendingIntent.getBroadcast(context, 6, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.NEWREPLY);
                        intent.putExtra("msgId", title);
                        pendingIntent = PendingIntent.getActivity(context, 6, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
                case 99:
                    LogUtil.i("修改头像,内容中直接是新头像的地址");
                    if (isBackground) {
                        intent = new Intent("com.push.rjkx");
                        intent.putExtra("flag", Flag.CHANGE_PHOTO);
                        pendingIntent = PendingIntent.getBroadcast(context, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        intent = new Intent(context, DoctorMainActivity.class);
                        intent.putExtra("flag", Flag.CHANGE_PHOTO);
                        intent.putExtra("photoUrl", title);
                        pendingIntent = PendingIntent.getActivity(context, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    break;
            }

            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.logo)
                    .setColor(context.getResources().getColor(R.color.top_bg))
                    .setTicker("TickerText:" + "您有新消息啦，请注意查收！")
                    .setContentTitle(alertTitle)
                    .setContentText("点击可立即查看")
                    .setContentIntent(pendingIntent)
                    .getNotification();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            manager.notify(1, notification);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
