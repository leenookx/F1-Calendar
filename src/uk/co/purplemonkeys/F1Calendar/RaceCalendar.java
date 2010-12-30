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
}
