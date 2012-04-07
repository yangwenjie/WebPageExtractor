/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.ParsingDetector;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.Charset;


/**
 * @author shixing
 *
 */
public class LocalPage extends Page{
	private String url=null;
	private String content=null;
	private String encoding=null;
	
	public void initAll()	//建造者模式中的Director
	{
		this.initEncoding();
		this.initContent();
		this.filterScript();
	}
	
	public void initEncoding()	//目前是用探测器探测出来的。
	{
		CodepageDetectorProxy detector=CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		File file=new File(this.url);
		Charset charset=null;
		try {
			charset=detector.detectCodepage(file.toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (charset==null)
			this.encoding="utf8";
		else
			this.encoding=charset.displayName();
	}
	
	public void filterScript()
	{
		if (content==null) return;
		content=content.replaceAll("<script[^>]*>[\\s\\S]*?</script>", "");
	}
	
	//从文件读取content
	public void initContent()
	{
		if (url==null) return;
		if (this.encoding==null)
			this.initEncoding();
		StringBuffer sb=new StringBuffer("");
		try {
			BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream(this.url),this.encoding));
			String line=null;
			while((line=fin.readLine())!=null)
			{
				sb.append(line+"\n");
			}
			fin.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.content=sb.toString();
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
