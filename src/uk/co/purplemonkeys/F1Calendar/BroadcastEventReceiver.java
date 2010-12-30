package uk.co.purplemonkeys.F1Calendar;

import java.util.ArrayList;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastEventReceiver extends BroadcastReceiver 
{

    @Override
    public void onReceive(Context context, Intent intent) 
    {
        Log.d("ExmampleBroadcastReceiver", "intent=" + intent);

        // We need to update the widget when the timezone changes, or the user or network sets the time.
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_TIMEZONE_CHANGED) || action.equals(Intent.ACTION_TIME_CHANGED)) 
        {
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            ArrayList<Integer> appWidgetIds = new ArrayList<Integer>();
            ArrayList<String> texts = new ArrayList<String>();
//
//            ExampleAppWidgetConfigure.loadAllTitlePrefs(context, appWidgetIds, texts);
//
//            final int N = appWidgetIds.size();
//            for (int i = 0; i < N; i++) 
//            {
//                ExampleAppWidgetProvider.updateAppWidget(context, gm, appWidgetIds.get(i), texts.get(i));
//            }
        }
    }

}