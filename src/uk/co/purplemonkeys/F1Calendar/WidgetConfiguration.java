package uk.co.purplemonkeys.F1Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WidgetConfiguration extends Activity
{
    // log tag
    private static final String TAG = "WidgetConfiguration";

    public static final String PREFS_NAME = "F1CalendarPrefs";
    public static final String PREFS_UPDATE_RATE_FIELD_PATTERN = "UpdateRate-%d";
    private static final int PREFS_UPDATE_RATE_DEFAULT = 5;

	private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate");
		
        // get any data we were launched with
        Intent launchIntent = getIntent();
        Bundle extras = launchIntent.getExtras();
        if (extras != null) 
        {
        	Log.d(TAG, "Launched with extras!");
        	
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            Intent cancelResultValue = new Intent();
            cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_CANCELED, cancelResultValue);
        } 
        else 
        {
        	Log.d(TAG, "Launched without extras!");
        	
            // only launch if it's for configuration
            // Note: when you launch for debugging, this does prevent this
            // activity from running. We could also turn off the intent
            // filtering for main activity.
            // But, to debug this activity, we can also just comment the
            // following line out.
            finish();
        }
        
        setContentView(R.layout.configuration);

        final SharedPreferences config = getSharedPreferences(PREFS_NAME, 0);
        final EditText updateRateEntry = (EditText) findViewById(R.id.update_rate_entry);

        updateRateEntry.setText(String.valueOf(config.getInt(String.format(PREFS_UPDATE_RATE_FIELD_PATTERN, appWidgetId), PREFS_UPDATE_RATE_DEFAULT)));

        Button saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
                int updateRateSeconds = Integer.parseInt(updateRateEntry.getText().toString());

                // store off the user setting for update timing
                SharedPreferences.Editor configEditor = config.edit();

                configEditor.putInt(String.format(PREFS_UPDATE_RATE_FIELD_PATTERN, appWidgetId), updateRateSeconds);
                configEditor.commit();

                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) 
                {
                    // tell the app widget manager that we're now configured
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    setResult(RESULT_OK, resultValue);

                    Intent widgetUpdate = new Intent();
                    widgetUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    widgetUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { appWidgetId });

                    // make this pending intent unique
//                    widgetUpdate.setData(Uri.withAppendedPath(Uri.parse(F1Calendar.URI_SCHEME + "://widget/id/"), String.valueOf(appWidgetId)));
                    PendingIntent newPending = PendingIntent.getBroadcast(getApplicationContext(), 0, widgetUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

                    // schedule the new widget for updating
                    AlarmManager alarms = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), updateRateSeconds * 1000, newPending);
                }

                // activity is now done
                finish();
            }
        });
	}
}