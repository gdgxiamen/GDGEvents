package com.xmgdg.gdgevents.Tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xmgdg.gdgevents.DataBase.DataBaseAct;
import com.xmgdg.gdgevents.MainActivity;
import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.app.App;
import com.xmgdg.gdgevents.model.Topic;
import com.xmgdg.gdgevents.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yulan on 2015/10/29.
 */
public class AlarmRecever extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        final String[] cityIDs = context.getResources().getStringArray(R.array.city_ids);
        int posistion = Integer.parseInt(App.getPrefer(AppStat.Preferences.InitPosition));
        final List<Topic> NewTopics = new ArrayList<>();
        RequestManager.getInstance().getTopicInfo(RequestManager.getUrl(cityIDs[posistion]), new Response.Listener<List<Topic>>() {
            @Override
            public void onResponse(List<Topic> response) {
                //写入数据库
                for (Topic topic : response) {
                    if (!DataBaseAct.getDataBaseAct().isEventAlreadyExist(topic.getId())) {

                        NewTopics.add(topic);
                        DataBaseAct.getDataBaseAct().addEvent(topic);
                    }
                }
                if (NewTopics.size() > 0) {
                    int mId = 1;//set Notifi ID

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context);
                    mBuilder = setBuildStyle(mBuilder, NewTopics, context);
                    mBuilder = setBuildEvent(mBuilder, context);

                    ((NotificationManager) context.
                            getSystemService(Context.NOTIFICATION_SERVICE)).notify(mId, mBuilder.build());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, context.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 设置通知的事件
     *
     * @param mBuilder 建立事件前的 NotificationCompat.Builder
     * @return 事件建立完成的 NotificationCompat.Builder
     */
    private NotificationCompat.Builder setBuildEvent(NotificationCompat.Builder mBuilder, Context context) {

        /*click*/
        Intent clickIntent = new Intent(context, MainActivity.class);
        mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT));
        return mBuilder;
    }


    /**
     * 建立通知的样式与内容
     *
     * @param mBuilder 建立样式前的 NotificationCompat.Builder
     * @return 样式建立完成的 NotificationCompat.Builder
     */
    private NotificationCompat.Builder setBuildStyle(NotificationCompat.Builder mBuilder, List<Topic> newTopics, Context context) {
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setNumber(newTopics.size())
                .setLights(Color.WHITE, 1000, 500)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{200, 200})
                .setColor(context.getResources().getColor(R.color.Apptheme_500));

        if (newTopics.size() == 1) {
            Topic topic = newTopics.get(0);

            /*单个活动*/
            mBuilder.setContentTitle(topic.getTitle())
                    .setContentText(topic.getSummary());
            /*BigTextStyle*/
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setSummaryText("GDG Events");
            bigTextStyle.setBigContentTitle(topic.getTitle());
            bigTextStyle.bigText(topic.getDescription());
            mBuilder.setStyle(bigTextStyle);
        } else {
            /*多条消息*/
            mBuilder.setContentTitle("GDG活动")
                    .setContentText("收到" + newTopics.size() + "个新活动");
            /*inbox*/
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("GDG活动");
            for (Topic topic : newTopics) {
                inboxStyle.addLine(topic.getTitle());
            }
            inboxStyle.setSummaryText("收到" + newTopics.size() + "个新活动");
            mBuilder.setStyle(inboxStyle);
        }

        return mBuilder;
    }
}
