package com.orchestranetworks.presales.utils;

import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.schema.Path;

public class ExistInAssocTable {

	/**
	 * Examines if a record exists in an association table
	 * @param pathtoMe - path to table being examined
	 * @param assTable - Association Table Object
	 * @param myPK - pk of Object to be examined
	 * @return boolean
	 */
	public static boolean existInAsstable(Path pathtoMe,AdaptationTable assTable, String myPK)
	{
		Request req = assTable.createRequest();
		req.setXPathFilter(pathtoMe.format() + "='"+myPK+"'");
		RequestResult res = req.execute();
		if (res.isSizeGreaterOrEqual(0)) return true;
		return false;
	}
}
