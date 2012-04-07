/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

/**
 * @author shixing
 *
 */
public abstract class Page {
	private String url=null;
	private String content=null;
	
	public abstract void initAll();
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
