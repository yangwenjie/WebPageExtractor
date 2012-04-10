/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

import edu.tsinghua.WebPageExtractor.util.XMLUtil;

/**
 * @author shixing
 *
 */
public class Wrapper {
	private String urlPattern=null;
	private String contentPattern=null;
	
	public void initAll(org.w3c.dom.Node node)
	{
		this.urlPattern=XMLUtil.getAttributeInXML(node, "urlPattern", "_content");
		this.contentPattern=XMLUtil.getAttributeInXML(node, "contentPattern", "_content");
	}
	
	public String toString()
	{
		String output="";
		output="[wrapper] "+this.urlPattern+" "+this.contentPattern;
		return output;
		
	}
	
	/**
	 * @return the urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}
	/**
	 * @param urlPattern the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	/**
	 * @return the contentPattern
	 */
	public String getContentPattern() {
		return contentPattern;
	}
	/**
	 * @param contentPattern the contentPattern to set
	 */
	public void setContentPattern(String contentPattern) {
		this.contentPattern = contentPattern;
	}
	
}
