package uk.co.purplemonkeys.F1Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.RemoteViews;

public class F1Calendar extends AppWidgetProvider
{
    // log tag
    private static final String TAG = "F1Calendar";
    
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
	{
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++)
        {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch the service
            Intent intent = new Intent(context, F1Calendar.class);
            intent.setAction(F1CalendarService.INTENT_ADD_WIDGET);
            intent.putExtra(F1CalendarService.INTENT_DATA_WIDGET_ID, appWidgetId);
            context.startService(intent);
            
            // Get the layout for the App Widget and attach an on-click listener to it.
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
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
                new ComponentName("uk.co.purplemonkeys.F1Calendar", "BroadcastEventReceiver"),
                					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                					PackageManager.DONT_KILL_APP);
        
        // Initialise the race calendar information
        RaceCalendar.Initialise();
        
        Intent intent = new Intent(context, F1CalendarService.class);
        intent.setAction(F1CalendarService.INTENT_RESET_ALARMS);
        
        context.startService(intent);
    }
    
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) 
    {
        final int n = appWidgetIds.length;

        for (int i = 0; i < n; i++) 
        {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, F1CalendarService.class);
            intent.setAction(F1CalendarService.INTENT_REMOVE_WIDGET);
            intent.putExtra(F1CalendarService.INTENT_DATA_WIDGET_ID, appWidgetId);
            context.startService(intent);
        }
    }
}