/**
 * 
 */
package edu.tsinghua.WebPageExtractor.webPageFinder;

import edu.tsinghua.WebPageExtractor.elements.LocalPage;
import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.elements.SimplePage;

/**
 * @author shixing
 * 基本的得到
 */
public class PageFactory {
	public Page getPage(String url)
	{
		Page p=new LocalPage();
		p.setUrl(url);
		p.initAll();
		return p;
	}
	
	public Page getPage(String url,String content)
	{
		Page p=new SimplePage();
		p.setUrl(url);
		p.setContent(content);
		return p;
	}
	
}
