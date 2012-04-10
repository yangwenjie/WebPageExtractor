/**
 * 
 */
package edu.tsinghua.WebPageExtractor.extractor;


import java.io.*;
import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import edu.tsinghua.WebPageExtractor.config.Config;
import edu.tsinghua.WebPageExtractor.elements.HTree;
import edu.tsinghua.WebPageExtractor.elements.LocalPage;
import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.ontology.Attribute;
import edu.tsinghua.WebPageExtractor.ontology.DataRecord;
import edu.tsinghua.WebPageExtractor.ontology.Ontology;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;
import edu.tsinghua.WebPageExtractor.ontology.SynonymAttributeFactory;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageFactory;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageIterator;

/**
 * @author shixing
 *
 */
public class ExtactprocessTest {
	PageFactory wpf=null;
	HTree tree=null;
	public void extractDataRecords()
	{
		System.out.println("start");
		Config config=Config.getNewInstance();
		//getPage
		PageFactory wpf=new PageFactory();
		Page p=wpf.getPage("temp/douban.htm");
		int matchStringDepth=Integer.parseInt(config.getValue("matchStringDepth"));
		tree=new HTree(matchStringDepth);
		try {
			Parser parser=new Parser();
			parser.setInputHTML(p.getContent());
			tree.buildTree(parser);
			BufferedWriter fout=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("temp/output.txt")));
			fout.write(tree.toTabString());
			fout.flush();
			fout.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("generalize");
		tree.generalizeTree();
		System.out.println("outputGeneralizeString");
		String output=tree.toRecordString();
		try
		{
			BufferedWriter fout=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("temp/record.txt")));
			fout.write(output);
			fout.flush();
			fout.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void initOntology()
	{
		Ontology o=new Ontology("ontology/news.ontology",new SynonymAttributeFactory());
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
	public void testPageInterator()
	{
		PageIterator pi=new PageIterator("rawData/crawled_results.00000.filepart");
		ArrayList<Page> pages=new ArrayList<Page>();
		while(pi.hasNext())
		{
			Page p=pi.next();
			pages.add(p);
		}
		System.out.println(pages.size());
		System.out.println(pages.get(0).getContent());
	}
	public static void main(String argv[])
	{
		ExtactprocessTest ep=new ExtactprocessTest();
		ep.initOntology();
	}
}
