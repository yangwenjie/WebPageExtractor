/**
 * 
 */
package edu.tsinghua.WebPageExtractor.blogNews;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.tsinghua.WebPageExtractor.blogNews.WrapperProcessor.AttributeType;
import edu.tsinghua.WebPageExtractor.config.Config;
import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.extractor.Extractor;
import edu.tsinghua.WebPageExtractor.filter.PageType;
import edu.tsinghua.WebPageExtractor.filter.URLClassifier;
import edu.tsinghua.WebPageExtractor.ontology.*;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageFactory;
import edu.tsinghua.WebPageExtractor.webPageFinder.PageIterator;

/**
 * @author Qiu Junpeng
 * 
 */
public class WrapperProcessor{

	private ArrayList<AttributeType> attrTypeList;
	private ArrayList<Attribute> as;
	
	private Attribute[] attrIndex;
	
	private PageFactory wpf = new PageFactory();

	private Ontology ontology; 

	private BufferedWriter bufw;
	private URLClassifier urlc;
	private PageType pageType;
	
	//!!!should modify this one
	private static String debugPath = "/home/qjp/Downloads/crawled_results.00000";	
	
	public WrapperProcessor(PageType type,
			ArrayList<AttributeType> attrTypeList) {
		this.pageType = type;
		ontology = new Ontology("ontology/" + pageType2str(type)
				+ ".ontology",new AttributeFactory());

		this.as = ontology.getAttributes();
		this.attrTypeList = attrTypeList;
		this.attrIndex = new Attribute[attrTypeList.size()];
		for (Attribute attr : this.as) {
			int index = getAttributeIndex(attr, attrTypeList);
			if (index == -1)
				continue;
			attrIndex[index] = attr;
		}
		Config config = Config.getNewInstance();
		String filename = config.getConfigValue("filterFile");
		urlc = new URLClassifier(filename);
		try {
			bufw = new BufferedWriter(new FileWriter("/home/qjp/tmp/pageout.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processPage(String blockPath) {
		PageIterator pi = new PageIterator(blockPath);
		while (pi.hasNext()) {
			Page page = pi.next();
			String pageURL = page.getUrl();
			//System.out.println(urlc.judgePageType(pageURL));
			if (urlc.judgePageType(pageURL) != this.pageType) {
				continue;
			}			
			String pageContent = page.getContent();
			PageOutput po = new PageOutput();
			DataRecord dr = this.ontology.generateDataRecord();
			//HashMap<AttributeType, String> retMap = new HashMap<AttributeType, String>();
			for (int i = 0; i < attrIndex.length; ++i) {
				Field f = getFieldByAttrType(attrTypeList.get(i), dr);				
				Attribute attr = attrIndex[i];
				ArrayList<Wrapper> wrapperList = attr.getWrappers();
				String res = "";
				for (Wrapper wrapper : wrapperList) {
					//System.out.println("try match url: "+pageURL+" with pattern "+wrapper.getUrlPattern());							
					if (!pageURL.matches(wrapper.getUrlPattern()))
						continue;
					String contentPattern = wrapper.getContentPattern();
					//System.out.println("try match contentPattern:" + contentPattern);
					Pattern p = Pattern.compile(contentPattern, Pattern.DOTALL);					
					Matcher m = p.matcher(pageContent);
					while (m.find()) {
						res += m.group().replaceAll("<.*?>|\\n|\\s", "");					
					}
				}
				if (res.length() > 0) {
					System.out.println("pageURL: "+pageURL + "\nres: "+res);
					f.setContent(res);
				}
				//retMap.put(attrTypeList.get(i), res);
			}
			po.getDataRecords().add(dr);
			try {
				bufw.write(po.toXML());
				bufw.newLine();
				bufw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Field getFieldByAttrType(AttributeType attrType, DataRecord dr) {
		String typeName = attributeType2str(attrType);
		for (Field f : dr.getFields()) {
			if (f.getTypeName().equals(typeName)) {
				return f;
			}
		}
		return null;
	}
	public int getAttributeIndex(Attribute attr,
			ArrayList<AttributeType> typeList) {
		String typeName = attr.getTypeName();
		for (int i = 0; i < typeList.size(); ++i) {
			if (typeName.equals(attributeType2str(typeList.get(i)))) {
				return i;
			}
		}
		return -1;
	}
	public static void main(String argv[]) {
		ArrayList<AttributeType> attrTypeList = new ArrayList<AttributeType>();
		attrTypeList.add(AttributeType.TITLE);
		WrapperProcessor processor = new WrapperProcessor(PageType.BLOG,
				attrTypeList);
		processor.processPage(debugPath);
//		dump(retMap);
	}

	private static void dump(HashMap<AttributeType, String> retMap) {
		// for DEBUG purpose
		Iterator<Entry<AttributeType, String>> iter = retMap.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<AttributeType, String> entry = iter.next();
			System.out.println("Key: " + attributeType2str(entry.getKey()));
			System.out.println("Val: " + entry.getValue());
		}
	} //

	public enum AttributeType {
		TITLE, BODY, TIMESTAMP, KEYWORD
	}


	public String pageType2str(PageType type) {
		switch (type) {
		case NEWS:
			return "news";
		case BLOG:
			return "blog";
		default:
			return null;
		}
	}

	public static String attributeType2str(AttributeType type) {
		switch (type) {
		case TITLE:
			return "title";
		case BODY:
			return "body";
		case TIMESTAMP:
			return "timeStamp";
		case KEYWORD:
			return "keyword";
		default:
			return null;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//edit by shixing
	public WrapperProcessor(PageType type,
			ArrayList<AttributeType> attrTypeList,Ontology ontology) {
		this.pageType = type;
		this.ontology=ontology;
		this.as = this.ontology.getAttributes();
		this.attrTypeList = attrTypeList;
		this.attrIndex = new Attribute[attrTypeList.size()];
		for (Attribute attr : this.as) {
			int index = getAttributeIndex(attr, attrTypeList);
			if (index == -1)
				continue;
			attrIndex[index] = attr;
		}
		Config config = Config.getNewInstance();
		String filename = config.getConfigValue("filterFile");
		urlc = new URLClassifier(filename);
		try {
			bufw = new BufferedWriter(new FileWriter("/home/qjp/tmp/pageout.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected PageOutput processOnePage(Page page)
	{
		PageOutput po=new PageOutput();
		if (page.getPageType() == this.pageType)
		{
			String pageURL = page.getUrl();
					
			String pageContent = page.getContent();
			DataRecord dr = this.ontology.generateDataRecord();
			//HashMap<AttributeType, String> retMap = new HashMap<AttributeType, String>();
			for (int i = 0; i < attrIndex.length; ++i) {
				Field f = getFieldByAttrType(attrTypeList.get(i), dr);				
				Attribute attr = attrIndex[i];
				ArrayList<Wrapper> wrapperList = attr.getWrappers();
				String res = "";
				for (Wrapper wrapper : wrapperList) {
					//System.out.println("try match url: "+pageURL+" with pattern "+wrapper.getUrlPattern());							
					if (!pageURL.matches(wrapper.getUrlPattern()))
						continue;
					String contentPattern = wrapper.getContentPattern();
					//System.out.println("try match contentPattern:" + contentPattern);
					Pattern p = Pattern.compile(contentPattern, Pattern.DOTALL);					
					Matcher m = p.matcher(pageContent);
					while (m.find()) {
						res += m.group().replaceAll("<.*?>|\\n|\\s", "");					
					}
				}
				if (res.length() > 0) {
					System.out.println("pageURL: "+pageURL + "\nres: "+res);
					f.setContent(res);
				}
				//retMap.put(attrTypeList.get(i), res);
			}
			po.getDataRecords().add(dr);
		}
		return po;
	}
}
