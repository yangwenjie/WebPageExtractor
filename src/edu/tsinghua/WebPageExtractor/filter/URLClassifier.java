/**
 * 
 */
package edu.tsinghua.WebPageExtractor.filter;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import edu.tsinghua.WebPageExtractor.ontology.Attribute;
import edu.tsinghua.WebPageExtractor.util.XMLUtil;

/**
 * @author shixing
 *
 */
public class URLClassifier {
	private org.w3c.dom.Document doc=null;
	private ArrayList<Judge> judges=new ArrayList<Judge>();
	public URLClassifier(String filename)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc=builder.parse(new File(filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initAll();
	}
	private void initAll()
	{
		this.initJudges();
	}
	
	private void initJudges()
	{
		org.w3c.dom.NodeList judgeXMLs=doc.getElementsByTagName("judge");
		for (int i=0;i<judgeXMLs.getLength();i++)
		{
			String pageType=XMLUtil.getAttributeInXML(judgeXMLs.item(i), "pageType", "_content");
		    PageType pt=PageType.getNewInstance(pageType);
		    for (int j=0;j<judgeXMLs.item(i).getChildNodes().getLength();j++)
		    {
		    	org.w3c.dom.Node child=judgeXMLs.item(i).getChildNodes().item(j);
		    	if (child.getNodeName().equals("pattern"))
		    	{
		    		String pattern = child.getTextContent();
		    		Judge judge = new Judge();
		    		judge.pattern=pattern;
		    		judge.pageType=pt;
		    		this.judges.add(judge);
		    	}
		    }
		}
	}
	
	public PageType judgePageType(String url)
	{
		for (int i=0;i<this.judges.size();i++)
		{
			Pattern p=Pattern.compile(this.judges.get(i).pattern);
			Matcher m=p.matcher(url);
			if (m.find())
				return this.judges.get(i).pageType;
		}
		return PageType.UNKNOWN;
	}
	
	public String toJudgeString()
	{
		String output="";
		for (int i =0;i<this.judges.size();i++)
		{
			output+=this.judges.get(i).pattern+" "+this.judges.get(i).pageType.toString()+"\n";
		}
		return output;
	}
	
	class Judge
	{
		private String pattern=null;
		private PageType pageType=null;
	}
}
