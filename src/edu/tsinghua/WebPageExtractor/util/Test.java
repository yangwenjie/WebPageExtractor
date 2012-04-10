/**
 * 
 */
package edu.tsinghua.WebPageExtractor.util;

import java.io.*;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import edu.tsinghua.WebPageExtractor.config.Config;
import edu.tsinghua.WebPageExtractor.filter.URLClassifier;

/**
 * @author shixing
 *
 */
public class Test {
	public static void main(String[] args){  
        Test tt = new Test(); 
        tt.test3();
	}
	public void test3()
	{
		BufferedReader fin;
		try {
			fin = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
			String line=fin.readLine();
			System.out.println(line);
			System.out.println("http://blog.sohu.com/yule/".matches(line));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void test()
	{
		Config config=Config.getNewInstance();
		String filename=config.getValue("filterFile");
		URLClassifier uc=new URLClassifier(filename);
		System.out.println(uc.toJudgeString());
	}
	public void test1()
	{
        try {
			Parser p=new Parser("temp/test1.txt");
        	//Parser p=new Parser();
        	//p.setInputHTML("<html><head></head>" +
        	//		"<body>  <h1> shi </h1>  </body></html>");
			NodeFilter rootfilter=new TagNameFilter("html");
			NodeList nodes=p.parse(rootfilter);
			System.out.println(nodes.size());
			System.out.println(nodes.elementAt(0).getChildren().size());
			Node node=nodes.elementAt(0).getChildren().elementAt(1);
			for (int i=0;i<node.getChildren().size();i++)
			{
				System.out.println(":"+node.getChildren().elementAt(i).toHtml()+":");
			}
			//Tag tag=(CompositeTag)node;
			//System.out.println(tag.getAttribute("class"));
		//	System.out.println(node.toHtml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
}
