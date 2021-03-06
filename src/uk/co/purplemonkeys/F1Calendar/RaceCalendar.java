package uk.co.purplemonkeys.F1Calendar;

import java.util.Date;

public class RaceCalendar
{
	private static java.util.ArrayList<Race> races;
	
	public static void Initialise()
	{
		races.add( new Race("Bahrain", 
								new Date(2011, 03, 11, 10, 00), 
								"bahrain.jpg",
								"Practice 1") );
		races.add( new Race("Bahrain", 
								new Date(2011, 03, 11, 14, 00), 
								"bahrain.jpg",
								"Practice 1") );
		races.add( new Race("Bahrain", 
								new Date(2011, 03, 12, 9, 00), 
								"bahrain.jpg",
								"Practice 3") );
		races.add( new Race("Bahrain", 
								new Date(2011, 03, 12, 13, 00), 
								"bahrain.jpg",
								"Qualifying") );
		races.add( new Race("Bahrain", 
								new Date(2011, 03, 13, 13, 00), 
								"bahrain.jpg",
								"Race") );
	}
	
	/**
	 * Gets the next race based on the date passed in.
	 * @param now The current date and time.
	 * @return The next race to be run.
	 */
	public static Race getNextRace(Date now)
	{
		boolean found = false;
		Race result = null;
		int index = 0;
		while (index < races.size() && !found)
		{
			Race r = races.get( index );
			if (r.getInterval() > now.getTime())
			{
				found = true;
				result = r;
			}
		}
		
		return result;
	}
}
