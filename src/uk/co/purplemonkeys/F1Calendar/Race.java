package uk.co.purplemonkeys.F1Calendar;

import java.util.Date;

public class Race 
{
	private String race_name;
	private Date race_date;
	private String race_flag_filename;
	
	public Race(String name, Date d, String flag_filename)
	{
		race_name = name;
		race_date = d;
		race_flag_filename = flag_filename;
	}
}
