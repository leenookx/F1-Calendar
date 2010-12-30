package uk.co.purplemonkeys.F1Calendar;

import java.util.Date;

public class RaceCalendar
{
	private static java.util.ArrayList<Race> races;
	
	public static void Initialise()
	{
		races.add( new Race("Bahrain", 
								new Date(2011, 03, 13, 13, 00), 
								"bahrain.jpg") );
	}
}
