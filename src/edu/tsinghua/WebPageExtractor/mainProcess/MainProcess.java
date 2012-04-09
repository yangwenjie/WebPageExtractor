/**
 * 
 */
package edu.tsinghua.WebPageExtractor.mainProcess;

import edu.tsinghua.WebPageExtractor.blogNews.BlogNewsExtractor;
import edu.tsinghua.WebPageExtractor.config.Config;
import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.filter.PageType;
import edu.tsinghua.WebPageExtractor.filter.URLClassifier;
import edu.tsinghua.WebPageExtractor.ontology.AttributeFactory;
import edu.tsinghua.WebPageExtractor.ontology.Ontology;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageIterator;

/**
 * @author shixing
 *
 */
public class MainProcess {
	Ontology blogOntology=null;
	Ontology newsOntology=null;
	Ontology MusicOntology=null;
	
	public static void main(String argc[])
	{
		MainProcess mp=new MainProcess();
		//init config
		Config config=Config.getNewInstance();
		//init ontology
		mp.initOntology(config);
		//init filter
		URLClassifier ucla=new URLClassifier(config.getConfigValue("filterFile"));
		//call different extractor based on different PageType
		PageIterator pi=new PageIterator(config.getConfigValue("rawDataFile"));
		while (pi.hasNext())
		{
			Page page=pi.next();
			PageOutput po=null;
			//judge PageType based on url
			String url=page.getUrl();
			PageType pageType=ucla.judgePageType(url);
			page.setPageType(pageType);
			switch(pageType)
			{
			case BLOG:
				BlogNewsExtractor bnExtractor=new BlogNewsExtractor();
				po=bnExtractor.extract(page, mp.blogOntology);
				break;
			case NEWS:
				bnExtractor=new BlogNewsExtractor();
				po=bnExtractor.extract(page, mp.blogOntology);
				break;
			default:
				break;
			}
			//add into the bufferedStorer
		}
		
		
	}
	
	private void initOntology(Config config)
	{
		AttributeFactory af=new AttributeFactory();
		this.blogOntology=new Ontology(config.getConfigValue("blogOntologyFile"),af);
		this.newsOntology=new Ontology(config.getConfigValue("newsOntologyFile"),af);
	}
}
