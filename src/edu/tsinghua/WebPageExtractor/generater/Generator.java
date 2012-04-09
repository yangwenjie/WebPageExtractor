/**
 * 
 */
package edu.tsinghua.WebPageExtractor.generater;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.htmlparser.Parser;

import edu.tsinghua.WebPageExtractor.elements.HTree;
import edu.tsinghua.WebPageExtractor.elements.Page;

/**
 * @author shixing
 *
 */
public class Generator {
	/**
	 * 
	 * 功能： 从输入的page中，得到分析好的dataRecord树；
	 * 返回值说明： 无
	 * @param page 输入的page
	 */
	public void generate(Page page,int matchStringDepth)
	{
		HTree tree=new HTree(matchStringDepth);
		try {
			Parser parser=new Parser();
			parser.setInputHTML(page.getContent());
			tree.buildTree(parser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("generalize");
		tree.generalizeTree();
		//System.out.println("outputGeneralizeString");
		page.setTree(tree);
	}
}
