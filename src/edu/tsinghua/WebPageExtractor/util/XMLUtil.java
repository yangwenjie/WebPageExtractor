/**
 * 
 */
package edu.tsinghua.WebPageExtractor.util;

import org.w3c.dom.Element;

/**
 * @author shixing
 *
 */
public class XMLUtil {
	
	public static String getAttributeInXML(org.w3c.dom.Document doc,String tagName, String attributeName)
	{
		String output="";
		org.w3c.dom.NodeList ontologyTypes=doc.getElementsByTagName(tagName);
		output=((Element)ontologyTypes.item(0)).getAttribute(attributeName);
		
		return output;
	}
	public static String getAttributeInXML(org.w3c.dom.Node node,String tagName, String attributeName)
	{
		String output="";
		org.w3c.dom.NodeList nodes=node.getChildNodes();
		if (!attributeName.equals("_content"))
		for (int i=0;i<nodes.getLength();i++)
		{
			if(nodes.item(i).getNodeName().equals(tagName))
			{
				output=((Element)nodes.item(i)).getAttribute(attributeName);
			}
		}
		else
			for (int i=0;i<nodes.getLength();i++)
			{
				if(nodes.item(i).getNodeName().equals(tagName))
				{
					output=(nodes.item(i)).getTextContent();
				}
			}
		return output;
	}
}
