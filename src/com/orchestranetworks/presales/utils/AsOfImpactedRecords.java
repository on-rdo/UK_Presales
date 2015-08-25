package com.orchestranetworks.presales.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.schema.Path;

public class AsOfImpactedRecords {

	private AdaptationTable table;
	private String id;
	private Date startdate;
	private Date enddate;
	
	private Path path_to_id;
	private Path path_to_start;
	private Path path_to_end;
	private final boolean showit = false;
	private Adaptation beforeRecord = null;
	private Date newEndDate;
	private Date newStartDate;
	private Adaptation afterRecord= null;
	private Adaptation newRecord;
	private Adaptation recordToSplit = null;
	
	public Adaptation getRecordToSplit ()
	{
		return recordToSplit;
	}
	
	public Adaptation getBeforeRecord() {
		return beforeRecord;
	}



	public Date getNewEndDate() {
		return newEndDate;
	}



	public Date getNewStartDate() {
		return newStartDate;
	}

	

	public Adaptation getAfterRecord() {
		return afterRecord;
	}

	public Adaptation getNewRecord()
	{
		return newRecord;
	}

	public void setVals (Adaptation newRecord,AdaptationTable table, String id,Date startdate, Date enddate, 	
 Path path_to_id, Path path_to_start, Path path_to_end)
 {
		this.newRecord = newRecord;
		this.table = table;
		this.id = id;
		this.startdate = startdate;
		this.enddate = enddate;
	
		this.path_to_id = path_to_id;
		this.path_to_start = path_to_start;
		this.path_to_end = path_to_end;
 }
	
	private void show(String param)
	{
		if (showit)
		{
			//System.out.println("************ START ****************");
			System.out.println(param);
			//System.out.println("************ END ****************");
		}
	}
	
	
	    public Date addDays(Date date, int days)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days); //minus number would decrement the days
	        return cal.getTime();
	    }
	
	
	public void generateImpatedRecords ()
	{
		
		// get all relevent records
		Request req = table.createRequest();
		req.setXPathFilter(path_to_id.format()+"='" + id +"'");
		RequestResult res = req.execute();
		show("got results " + res.getSize() );
		Adaptation ada = res.nextAdaptation();
		show ("STARTDATE: " + startdate + " ENDDATE: " + enddate);
		show("________________________________________________");
		while (ada!=null)
		{ 
			
			Date adaStartDate = ada.getDate(path_to_start);
			Date adaEndDate = ada.getDate(path_to_end);
			//check im not me!
			if (!(ada.getOccurrencePrimaryKey().format().equals(newRecord.getOccurrencePrimaryKey().format())))
					{
					// the record has been updated but date has not changed
				if (enddate!=null&&adaEndDate!=null&&(adaStartDate.equals(startdate)&&adaEndDate.equals(enddate)))
				{
					
					show("The Record needs Spliting");
					recordToSplit = ada;
					newEndDate = addDays(startdate,-1);
					beforeRecord = ada;
					newStartDate = addDays(enddate,+1);
					afterRecord = ada;
				}
				else if (enddate!=null&&adaEndDate!=null&&(adaStartDate.before(startdate)&&adaEndDate.after(enddate)))
						{
							
							show("The Record needs Spliting");
							recordToSplit = ada;
							newEndDate = addDays(startdate,-1);
							beforeRecord = ada;
							newStartDate = addDays(enddate,+1);
							afterRecord = ada;
						}
				else if (adaStartDate == null && adaEndDate == null)
				{
					show ("set end date on existing record");
					beforeRecord= ada;
					newEndDate = addDays (startdate, -1);
				}
						else if (adaStartDate.before(startdate)&& adaEndDate==null)
						{
							show ("set end date on existing record");
							beforeRecord= ada;
							newEndDate = addDays (startdate, -1);
						}
						
						else if (adaStartDate.before(startdate) && adaEndDate.after(startdate))
						{
							show ("need to move existing end date");
							beforeRecord= ada;
							newEndDate = addDays (startdate, -1);
						}
						else if (enddate!=null&&enddate==null&&adaStartDate.after(enddate))
						{
							show ("need to end date new record");
							beforeRecord = newRecord;
							newEndDate = addDays(adaStartDate, -1);
					}
			
		}
		
			ada = res.nextAdaptation();
	}
	
	
		
		
		
		
	}
	
}
