package com.whut.ein3614.appwidgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "MyAppWidgetProvider";
    public static final String CLICK_ACTION = "com.appwidget.action.CLICK";
    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: ");
        if(intent.getAction().equals(CLICK_ACTION)){//如果是自己的Action
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.timg);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    //旋转
                    for(int i=0;i<37;i++){
                        float degree = (i*10)%360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
                        //设置旋转后的图片
                        remoteViews.setImageViewBitmap(R.id.iv_icon,rotateBitmap(srcBitmap,degree));

                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
                        remoteViews.setOnClickPendingIntent(R.id.iv_icon,pendingIntent);//设置点击事件监听

                        appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }
    /**
     * 每次桌面小工具更新时都会调用一次该方法
     * */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int counter = appWidgetIds.length;
        for(int i = 0;i<counter;i++){
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context,appWidgetManager,appWidgetId);
        }
    }

    /**
     * 更新桌面小工具
     * */
    private void onWidgetUpdate(Context context,AppWidgetManager appWidgetManager,int appWidgetId){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
        remoteViews.setOnClickPendingIntent(R.id.iv_icon,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }

    /**
     * 旋转图片
     * */
    private Bitmap rotateBitmap(Bitmap bitmap,float degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
}
