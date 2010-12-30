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
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.RemoteViews;

//Need the following import to get access to the app resources, since this
//class is in a sub-package.
import uk.co.purplemonkeys.F1Calendar.R;

public class F1Calendar extends AppWidgetProvider
{
    // log tag
    private static final String TAG = "ExampleAppWidgetProvider";
    
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
	{
		Log.d(TAG, "onUpdate");
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 60000);
	}
	
    @Override
    public void onEnabled(Context context) 
    {
        Log.d(TAG, "onEnabled");
        // When the first widget is created, register for the TIMEZONE_CHANGED and TIME_CHANGED
        // broadcasts.  We don't want to be listening for these if nobody has our widget active.
        // This setting is sticky across reboots, but that doesn't matter, because this will
        // be called after boot if there is a widget instance for this provider.
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("uk.co.purplemonkeys", ".F1Calendar.BroadcastEventReceiver"),
                					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                					PackageManager.DONT_KILL_APP);
    }
    
    @Override
    public void onDisabled(Context context) 
    {
        // When the first widget is created, stop listening for the TIMEZONE_CHANGED and
        // TIME_CHANGED broadcasts.
        Log.d(TAG, "onDisabled");
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("uk.co.purplemonkeys", ".F1Calendar.BroadcastEventReceiver"),
                					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                					PackageManager.DONT_KILL_APP);
    }
	
	private class MyTime extends TimerTask 
	{
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;

		public MyTime(Context context, AppWidgetManager appWidgetManager) 
		{
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
			thisWidget = new ComponentName(context, F1Calendar.class);
		}
	    
		@Override
	    public void run() 
		{
			Date date1 = new Date();
			Calendar calendar = new GregorianCalendar(2010, 11,25);
			long days = (((calendar.getTimeInMillis()- date1.getTime())/1000))/86400;
			remoteViews.setTextViewText(R.id.xmas, "" + days);
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		}
	}
}