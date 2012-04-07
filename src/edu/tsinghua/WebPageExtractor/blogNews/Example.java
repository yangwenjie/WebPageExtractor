/**
 * 
 */
package edu.tsinghua.WebPageExtractor.blogNews;

import java.util.ArrayList;



import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.ontology.*;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageFactory;

/**
 * @author shixing
 *
 */
public class Example {
	public static void main(String argv[])
	{
		
	}
	
	public void initOntology()
	{
		Ontology o=new Ontology("ontology/news.ontology",new AttributeFactory());
		o.initAll();
		ArrayList<Attribute> as=o.getAttributes();
		for (Attribute atr: as)
		{
			System.out.println(atr.toString());
		}
		PageOutput po=new PageOutput();
		DataRecord dr=o.generateDataRecord();
		po.getDataRecords().add(dr);
		
		System.out.println(po.toXML());
	}
	
	public void initPage()
	{
		PageFactory wpf=new PageFactory();
		Page p=wpf.getPage("temp/douban.htm");
		System.out.println(p.getUrl());
		System.out.println(p.getContent());
	}
	
	
}
