package uk.co.purplemonkeys.F1Calendar;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class F1CalendarService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener
{
    public static final String INTENT_ADD_WIDGET = "uk.co.purplemonkeys.F1Calendar.intent.ACTION_SERVICE_ADD_WIDGET";
    public static final String INTENT_REMOVE_WIDGET = "uk.co.purplemonkeys.F1Calendar.intent.ACTION_SERVICE_REMOVE_WIDGET";
    public static final String INTENT_RESET_ALARMS = "uk.co.purplemonkeys.F1Calendar.intent.ACTION_SERVICE_RESET_ALARMS";
    public static final String INTENT_ALARM_ALERT = "uk.co.purplemonkeys.F1Calendar.intent.ACTION_ALARM_ALERT";
    public static final String INTENT_RESET_WIDGET = "uk.co.purplemonkeys.F1Calendar.intent.ACTION_RESET_WIDGET";

    public static final String INTENT_DATA_WIDGET_ID = "WIDGET_ID";
    public static final String INTENT_DATA_IS_SILENT = "IS_SILENT";
    private static final String VIBRATE_KEY = "CTW_VIBRATE";
    private static final String INSISTENT_KEY = "CTW_INSISTENT";
    private static final String RINGTONE_KEY = "CTW_RINGTONE";
    private static final String REFRESH_INTERVAL_KEY = "CTW_REFRESH_INTERVAL";
    private static final String VOLUME_SOURCE_KEY = "CTW_VOLUME_SOURCE";
    
    private SharedPreferences m_preferences;
	
    @Override
    public void onCreate() 
    {
        m_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        m_preferences.registerOnSharedPreferenceChangeListener(this);
        
        // Initialise the race calendar information
        RaceCalendar.Initialise();
    }

    @Override
    public void onDestroy() 
    {
    	m_preferences.unregisterOnSharedPreferenceChangeListener(this);
    }
    
    @Override
	public IBinder onBind(Intent arg0) {
    	throw new IllegalStateException("This service cannot be bound!");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	}
	
    private void scheduleAlarmEvent() {
        long now = SystemClock.elapsedRealtime();
        
        final Calendar c = Calendar.getInstance();
        Race race = RaceCalendar.getNextRace( c.getTime() );
        if (race != null)
        {
        	AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        	
        	long when = race.getInterval();
        	
        	if (now < when)
        	{
	            Intent intent = new Intent(INTENT_ALARM_ALERT);
	            intent.setComponent(new ComponentName(this, F1CalendarService.class));
	            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	
	            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, when, pendingIntent);
        	}
        }
        else
        {
        	
        }
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
    	return START_STICKY;
    }
}
