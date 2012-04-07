/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

import java.util.ArrayList;

import edu.tsinghua.WebPageExtractor.util.XMLUtil;

/**
 * @author shixing
 *
 */
public class SynonymAttribute extends Attribute {
	private ArrayList<String> keyword=new ArrayList<String>();
	
	public SynonymAttribute()
	{
		super();
	}
	public void initAll(org.w3c.dom.Node node)
	{
		super.initAll(node);
		this.initKeyword(node);
		
	}
	
	public void initKeyword(org.w3c.dom.Node node)
	{
		String content=XMLUtil.getAttributeInXML(node, "keyword", "_content");
		String kk[]=content.split(" ");
		for (int i=0;i<kk.length;i++)
			this.keyword.add(kk[i]);
	}
	
	public String toString()
	{
		String output=super.toString();
		for (int i=0;i<this.keyword.size();i++)
			output+=this.keyword.get(i)+" ";
		output+="\n";
		return output;
	}
	
	
	
	/**
	 * @return the keyword
	 */
	public ArrayList<String> getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(ArrayList<String> keyword) {
		this.keyword = keyword;
	}
	
}
