/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

import java.util.ArrayList;

/**
 * @author shixing
 *
 */
public class PageOutput {
	private String pageURL=null;
	private ArrayList<DataRecord> dataRecords=new ArrayList<DataRecord>();
	
	public String toXML()
	{
		String output="<output>\n";
		output+="<pageURL value=\""+this.pageURL+"\"/>\n";
		for (int i=0;i<dataRecords.size();i++)
		{
			output+=this.dataRecords.get(i).toXML();
		}
		output+="</output>\n";
		return output;
	}

	/**
	 * @return the pageURL
	 */
	public String getPageURL() {
		return pageURL;
	}
	/**
	 * @param pageURL the pageURL to set
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	/**
	 * @return the dataRecords
	 */
	public ArrayList<DataRecord> getDataRecords() {
		return dataRecords;
	}
	/**
	 * @param dataRecords the dataRecords to set
	 */
	public void setDataRecords(ArrayList<DataRecord> dataRecords) {
		this.dataRecords = dataRecords;
	}
}
