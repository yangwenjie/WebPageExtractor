/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

import java.util.ArrayList;

/**
 * @author shixing
 *
 */
public class ChildrenOrganize {
	private ArrayList<ArrayList<GeneralizedNode>> records=new ArrayList<ArrayList<GeneralizedNode>>();

	/**
	 * @return the records
	 */
	public ArrayList<ArrayList<GeneralizedNode>> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(ArrayList<ArrayList<GeneralizedNode>> records) {
		this.records = records;
	}
	
	public String toString(int itab)
	{
		String stab="";
		for (int i=0;i<itab;i++)
		{
			stab+=" ";
		}
		String output="";
		for (int i=0;i<this.records.size();i++)
		{
			ArrayList<GeneralizedNode> drs=this.records.get(i);
			output+=stab+drs.get(0).toString()+" "+drs.size()+"\n";
		}
		return output;
	}

	
		
}
