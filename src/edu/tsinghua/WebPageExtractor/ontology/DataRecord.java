/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

import java.util.ArrayList;

/**
 * @author shixing
 *
 */
public class DataRecord {
	private String ontologyFile=null;
	private ArrayList<Field> fields=new ArrayList<Field>();
	private Ontology ontology=null;
	
	public DataRecord(Ontology ontology)
	{
		this.ontologyFile=ontology.getOntologyFile();
		this.ontology=ontology;
	}
	
	public String toXML()
	{
		String output="<dataRecord>\n";
		output+="<ontologyFile value=\""+this.ontologyFile+"\"/>\n";
		for (int i=0;i<this.fields.size();i++)
		{
			output+=this.fields.get(i).toXML();
		}
		output+="</dataRecord>\n";
		return output;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @return the ontologyFile
	 */
	public String getOntologyFile() {
		return ontologyFile;
	}
	/**
	 * @param ontologyFile the ontologyFile to set
	 */
	public void setOntologyFile(String ontologyFile) {
		this.ontologyFile = ontologyFile;
	}
	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	
}
