package com.error.grrravity.mynews.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.models.APISearch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class AlarmReceiver extends BroadcastReceiver {

    private static Preferences mPreferences;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mPreferences = Preferences.getInstance(mContext);
        executeRequest();
    }

    public void executeRequest() {
        String keywords = mPreferences.getNotifQuery();
        List<String> categories = mPreferences.getNotifCategories();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());
        Log.i(getClass().getSimpleName(),
                keywords + mContext.getResources().getString(R.string.alarm_settings)+ date
                        + mContext.getResources().getString(R.string.my_log));

        Disposable disposable = NYTStreams.streamFetchSearchArticles
                (keywords, categories, date, date)
                .subscribeWith(new DisposableObserver<APISearch>() {
                    @Override
                    public void onNext(APISearch articles) {
                        showNotification(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(getClass().getSimpleName(),
                                mContext.getResources().getString(R.string.onErrorNotif)
                                        + mContext.getResources().getString(R.string.my_log));
                }

                    @Override
                    public void onComplete() {
                        Log.e(getClass().getSimpleName(),
                                mContext.getResources().getString(R.string.onCompleteNotif)
                                        + mContext.getResources().getString(R.string.my_log));
                    }
                });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void showNotification(APISearch articles) {
        String keywords = mPreferences.getNotifQuery();
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(mContext.getResources()
                .getString(R.string.notification_title));
        inboxStyle.addLine(mContext.getResources().getString(R.string.notif_pre_keyword)
                + keywords +
                mContext.getResources().getString(R.string.notif_post_keyword)
                + articles.getResponse().getDocs().size() +
                mContext.getResources().getString(R.string.notif_post_result));

        String chanelID = mContext.getResources().getString(R.string.chanel_id);

        NotificationCompat.Builder notifBuilder = new NotificationCompat
                .Builder(mContext, chanelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(mContext.getResources()
                        .getString(R.string.notification_title))
                .setContentText(mContext.getResources().getString(R.string.notif_pre_keyword)
                        + keywords +
                        mContext.getResources().getString(R.string.notif_post_keyword)
                        + articles.getResponse().getDocs().size() +
                        mContext.getResources().getString(R.string.notif_post_result))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(inboxStyle);

        NotificationManager notifManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence chanelName = mContext.getResources().getString(R.string.notif_other_sdk);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notifChanel = new NotificationChannel(chanelID,
                    chanelName,
                    importance);
            assert notifManager != null;
            notifManager.createNotificationChannel(notifChanel);

            notifManager.notify(0, notifBuilder.build());
        }
    }
}
