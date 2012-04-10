/**
 * 
 */
package edu.tsinghua.WebPageExtractor.mainProcess;

import java.io.*;

import edu.tsinghua.WebPageExtractor.blogNews.BlogNewsExtractor;
import edu.tsinghua.WebPageExtractor.config.Config;
import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.filter.PageType;
import edu.tsinghua.WebPageExtractor.filter.URLClassifier;
import edu.tsinghua.WebPageExtractor.generater.Generator;
import edu.tsinghua.WebPageExtractor.ontology.Attribute;
import edu.tsinghua.WebPageExtractor.ontology.AttributeFactory;
import edu.tsinghua.WebPageExtractor.ontology.Ontology;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;
import edu.tsinghua.WebPageExtractor.ontology.Wrapper;
import edu.tsinghua.WebPageExtractor.storer.BufferedStorer;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageIterator;

/**
 * @author shixing
 * 目前做为测试，实际的写法应该支持ontology和Storer的对应
 */
public class MainProcess {
	Ontology blogOntology=null;
	Ontology bloggerOntology=null;
	Ontology movieOntology=null;
	Ontology musicOntology=null;
	Ontology newsOntology=null;
	Ontology postOntology=null;
	Ontology reviewOntology=null;
	Ontology twitterOntology=null;
	Ontology userOntology=null;
	BufferedStorer blogStorer=null;
	BufferedStorer newsStorer=null;
	BufferedStorer postStorer=null;
	
	public static void main(String argc[])
	{
		long start=System.currentTimeMillis();
		MainProcess mp=new MainProcess();
		mp.testDR();
		long end=System.currentTimeMillis();
		long ss=(end-start)/1000;
		System.out.println(ss/60+"min"+ss%60+"s");
		
	}
	private void testOntology()
	{
		this.initOntology(Config.getNewInstance());
		for (Attribute a:this.blogOntology.getAttributes())
		{
			System.out.println(a.getTypeName());
			for (Wrapper w:a.getWrappers())
			{
				System.out.println(w.getUrlPattern());
				System.out.println(w.getContentPattern());
			}
		}
	}
	
	
	private void go()
	{
		//init config
		Config config=Config.getNewInstance();
		//init ontology
		this.initOntology(config);
		//init filter
		URLClassifier ucla=new URLClassifier(config.getValue("filterFile"));
		//call different extractor based on different PageType
		PageIterator pi=new PageIterator(config.getValue("rawDataFile"));
		int count=0;
		int breaknum=0;
		while (pi.hasNext())
		{

			count++;
			if (count%20==0)
			{
				System.out.println(count+" "+breaknum);
			}
			Page page=pi.next();
			//if(!page.getUrl().equals("http://blog.sina.com.cn/s/blog_44491d9d0102e29n.html?tj=1"))
			//	continue;
			//judge PageType based on url
			String url=page.getUrl();
			PageType pageType=ucla.judgePageType(url);
			page.setPageType(pageType);
			if (pageType==PageType.BLOG || pageType==PageType.NEWS || pageType==PageType.POST)
				System.out.println(pageType.toString()+" "+page.getUrl());
			
			switch(pageType)
			{
			case BLOG:
				BlogNewsExtractor bnExtractor=new BlogNewsExtractor();
				bnExtractor.extract(page, this.blogOntology);
				if (page.getPo().getDataRecords().size()>0)
					this.blogStorer.addPageOutput(page.getPo());
				break;
			case NEWS:
				bnExtractor=new BlogNewsExtractor();
				bnExtractor.extract(page, this.newsOntology);
				if (page.getPo().getDataRecords().size()>0)
					this.newsStorer.addPageOutput(page.getPo());
				break;
			case POST:
				bnExtractor=new BlogNewsExtractor();
				bnExtractor.extract(page, this.postOntology);
				if (page.getPo().getDataRecords().size()>0)
					this.postStorer.addPageOutput(page.getPo());
				break;
			default:
				breaknum++;
				break;
			}
			//add into the bufferedStorer
		}
		//close the BufferedStorer;
		this.newsStorer.close();
		this.blogStorer.close();
		this.postStorer.close();
	}
	
	private void testDR()
	{
		//init config
		Config config=Config.getNewInstance();
		Generator g=new Generator();
		int maxDepth=Integer.parseInt(config.getValue("matchStringDepth"));
		BufferedStorer drStorer=new BufferedStorer(20, "dataRecord", "dr", "utf8");
		//init ontology
		this.initOntology(config);
		//init filter
		URLClassifier ucla=new URLClassifier(config.getValue("filterFile"));
		//call different extractor based on different PageType
		PageIterator pi=new PageIterator(config.getValue("rawDataFile"));
		int count=0;
		int breaknum=0;
		while (pi.hasNext())
		{

			count++;
			if (count%80==0)
			{
				System.out.println(count+" "+breaknum);
			}
			Page page=pi.next();
			//if(!page.getUrl().equals("http://blog.sina.com.cn/s/blog_44491d9d0102e29n.html?tj=1"))
			//	continue;
			//judge PageType based on url
			String url=page.getUrl();
			PageType pageType=ucla.judgePageType(url);
			page.setPageType(pageType);
			if (pageType==PageType.BLOG || pageType==PageType.NEWS || pageType==PageType.POST)
				System.out.println(pageType.toString()+" "+page.getUrl());
			try{
			g.generate(page, maxDepth);
			}catch (Exception e)
			{
				e.printStackTrace();
				System.out.println(page.getUrl());
				try {
					BufferedWriter fout=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("temp/errorPage.txt")));
					fout.write(page.getUrl());
					fout.write(page.getContent());
					return;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			switch(pageType)
			{
			case BLOG:
				BlogNewsExtractor bnExtractor=new BlogNewsExtractor();
				bnExtractor.extract(page, this.blogOntology);
				if (page.getPo().getDataRecords().size()>0)
					this.blogStorer.addPageOutput(page.getPo());
				drStorer.addString(page.getUrl()+"\n");
				drStorer.addString(page.getTree().toRecordString());
				drStorer.addString("\n");
				break;
			case NEWS:
				bnExtractor=new BlogNewsExtractor();
				bnExtractor.extract(page, this.newsOntology);
				if (page.getPo().getDataRecords().size()>0)
					this.newsStorer.addPageOutput(page.getPo());
				break;
			case POST:
				bnExtractor=new BlogNewsExtractor();
				bnExtractor.extract(page, this.postOntology);
				if (page.getPo().getDataRecords().size()>0)
					this.postStorer.addPageOutput(page.getPo());
				break;
			default:
				breaknum++;
				break;
			}
			//add into the bufferedStorer
		}
		//close the BufferedStorer;
		this.newsStorer.close();
		this.blogStorer.close();
		this.postStorer.close();
	}
	
	
	
	
	
	
	
	
	
	private void initOntology(Config config)
	{
		AttributeFactory af=new AttributeFactory();
		this.blogOntology=new Ontology(config.getValue("blogOntologyFile"),af);
		this.newsOntology=new Ontology(config.getValue("newsOntologyFile"),af);
		this.postOntology=new Ontology(config.getValue("postOntologyFile"),af);
		//BufferedStorer init
		int fileLimit=Integer.parseInt(config.getValue("outputFileSize"));
		String encoding=config.getValue("fileEncoding");
		this.blogStorer=new BufferedStorer(fileLimit, config.getValue("blogDir"), config.getValue("blogPrefix"), encoding);
		this.postStorer=new BufferedStorer(fileLimit, config.getValue("postDir"), config.getValue("postPrefix"), encoding);
		this.newsStorer=new BufferedStorer(fileLimit, config.getValue("newsDir"), config.getValue("newsPrefix"), encoding);
	}
}
