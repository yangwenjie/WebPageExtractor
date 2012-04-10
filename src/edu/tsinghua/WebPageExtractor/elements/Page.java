/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

import edu.tsinghua.WebPageExtractor.filter.PageType;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;

/**
 * @author shixing
 *
 */
public abstract class Page {
	private String url=null;
	private String content=null;
	private PageType pageType=null;
	private HTree tree=null;
	private String crawler=null;
	private PageOutput po=new PageOutput();
	/**
	 * @return the po
	 */
	public PageOutput getPo() {
		return po;
	}
	
	/**
	 * @return the crawler
	 */
	public String getCrawler() {
		return crawler;
	}
	/**
	 * @param crawler the crawler to set
	 */
	public void setCrawler(String crawler) {
		this.crawler = crawler;
	}
	/**
	 * @return the tree
	 */
	public HTree getTree() {
		return tree;
	}
	/**
	 * @param tree the tree to set
	 */
	public void setTree(HTree tree) {
		this.tree = tree;
	}
	/**
	 * @return the pageType
	 */
	public PageType getPageType() {
		return pageType;
	}
	/**
	 * @param pageType the pageType to set
	 */
	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}
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
