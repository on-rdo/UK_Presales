package com.orchestranetworks.presales.utils;

import java.util.Calendar;
import java.util.Date;
/**
 * @author Rich Dobson
 * Static classes to Manage Effective Dates
 */
public class StaticDateUtils {

	
	/**
	 * returns true if date is between state date and end date (inclusive)
	 * @param startdate - Start Date
	 * @param endDate - End Date
	 * @param myDate - date to be evaluated
	 * @return boolean - will return true if both dates are null
	 */
	public static boolean dateinbetweenDate (Date startdate, Date endDate, Date myDate)
	{
		// return true if both dates are null
		if (startdate==null && endDate==null)
		{return true;
				
		}
		// no start date and date is before endDate OR evaluate date is endDate
		if (startdate==null && (myDate.before(endDate)|| myDate.equals(endDate)))
				{
			return true;
				}
		// no end date and date is after start date OR equals end date
		if (endDate==null && (myDate.after(startdate)|| myDate.equals(startdate)))
		{
	return true;
		}

		if (startdate!=null&& endDate!=null&& myDate.equals(startdate) || myDate.equals(endDate))
		{
			return true;
		}
		if (startdate!=null&& endDate!=null&& myDate.after(startdate) && myDate.before(endDate))
		{
			return true;
		}
		

		return false;
	}

	
    /**
     * Adds removes days to a specific date
     * @param date - date to be changed
     * @param days - number of days to be changed NOTE: minus will remove days
     */
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
