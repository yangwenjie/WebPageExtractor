/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

import java.util.ArrayList;

/**
 * @author shixing
 *
 */
public class GeneralizedNode {
	private ArrayList<HNode> attribute=new ArrayList<HNode>();

	/**
	 * @return the attribute
	 */
	public ArrayList<HNode> getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(ArrayList<HNode> attribute) {
		this.attribute = attribute;
	}
	
	public String toString()
	{
		String output="";
		for (int i=0;i<attribute.size();i++)
		{
			HNode node=this.attribute.get(i);
			output+=node.getMatchText()+" ";
		}
		return output;
	}
	
}
