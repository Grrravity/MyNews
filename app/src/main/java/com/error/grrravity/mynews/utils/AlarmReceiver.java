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

import static android.content.ContentValues.TAG;

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
        Log.i(TAG, "Notif for article at date yyyyMMdd : " + date +
                " and query is " + keywords);

        Disposable disposable = NYTStreams.streamFetchSearchArticles
                (keywords, categories, date, date)
                .subscribeWith(new DisposableObserver<APISearch>() {
                    @Override
                    public void onNext(APISearch articles) {
                        showNotification(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("Test", "Search is charged");
                    }
                });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void showNotification(APISearch articles) {
        String keywords = mPreferences.getNotifQuery();
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Notification");
        inboxStyle.addLine("Your search '" + keywords + "' on MyNews found "
                + articles.getResponse().getDocs().size() +
                " articles.");

        String chanelID = "chanel_ID";

        NotificationCompat.Builder notifBuilder = new NotificationCompat
                .Builder(mContext, chanelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MyNews")
                .setContentText("Your search '" + keywords + "' on MyNews found "
                        + articles.getResponse().getDocs().size() +
                        " articles.")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(inboxStyle);

        NotificationManager notifManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence chanelName = "MyNews have a message for you";
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
