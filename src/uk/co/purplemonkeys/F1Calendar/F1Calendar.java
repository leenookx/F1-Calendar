package uk.co.purplemonkeys.F1Calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class F1Calendar extends AppWidgetProvider
{
	        @Override
	        public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                                                                   int[] appWidgetIds) {
	               Timer timer = new Timer();
	               timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 60000);
	          }
	          private class MyTime extends TimerTask {
	                 RemoteViews remoteViews;
	                 AppWidgetManager appWidgetManager;
	                 ComponentName thisWidget;
	                 public MyTime(Context context, AppWidgetManager appWidgetManager) {
	                 this.appWidgetManager = appWidgetManager;
	                 remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
	                 thisWidget = new ComponentName(context, F1Calendar.class);
	                 }
	            @Override
	            public void run() {
	                        Date date1 = new Date();
	                        Calendar calendar = new GregorianCalendar(2010, 11,25);
	                        long days = (((calendar.getTimeInMillis()- date1.getTime())/1000))/86400;
	                        remoteViews.setTextViewText(R.id.xmas,""+ days);
	                        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
	             }
	          }
	        }