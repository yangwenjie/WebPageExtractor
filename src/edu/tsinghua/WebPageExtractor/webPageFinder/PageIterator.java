/**
 * 
 */
package edu.tsinghua.WebPageExtractor.webPageFinder;

import java.io.*;

import tsinghua.crawlers.messages.Storage.PageResult;

import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;

import edu.tsinghua.WebPageExtractor.elements.Page;

/**
 * @author shixing
 *
 */
public class PageIterator {
	private String filePath=null;
	private BufferedReader fin=null;
	private Page curPage=null;
	public PageIterator(String filePath)
	{
		this.filePath=filePath;
		try {
			this.fin=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reset()
	{
		try {
			if (this.fin!=null)
				fin.close();
			this.fin=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean hasNext()
	{
		boolean has=false;
		String line;
		StringBuffer tempResult = new StringBuffer();
		try {
			while ((line = this.fin.readLine()) != null) {
				if (line.equals("<CRAWED_PAGE>")) {
					tempResult = new StringBuffer();
				} else if (line.equals("</CRAWED_PAGE>")) {
					PageResult.Builder pgResultBuilder = PageResult.newBuilder();
					TextFormat.merge(tempResult.toString(), pgResultBuilder);
					PageResult pgResult = pgResultBuilder.build();
					PageFactory pf=new PageFactory();
					this.curPage=pf.getPage(pgResult.getUrl(), pgResult.getHtml());
					return true;
				} else {
					tempResult.append(line + "\n");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (this.fin!=null)
				try {
					fin.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} 
		return has;
	}
	
	public Page next()
	{
		return curPage;
	}

}
