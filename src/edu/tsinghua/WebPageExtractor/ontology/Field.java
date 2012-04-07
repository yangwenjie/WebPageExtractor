/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

/**
 * @author shixing
 *
 */
public class Field {
	private Attribute attr=null;
	private String content=null;
	private String typeName=null;
	
	public Field(Attribute attr)
	{
		this.attr=attr;
		this.typeName=attr.getTypeName();
	}
	
	public String toXML()
	{
		String output="<field>\n";
		output+="<typeName value=\""+this.typeName+"\"/>\n";
		if (this.content==null)
			output+="<content></content>\n";
		else
			output+="<content>"+this.content+"</content>\n";
		output+="</field>\n";
		return output;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @return the attr
	 */
	public Attribute getAttr() {
		return attr;
	}
	/**
	 * @param attr the attr to set
	 */
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
