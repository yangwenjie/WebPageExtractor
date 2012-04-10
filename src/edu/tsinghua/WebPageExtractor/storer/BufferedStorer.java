/**
 * 
 */
package edu.tsinghua.WebPageExtractor.storer;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import edu.tsinghua.WebPageExtractor.ontology.PageOutput;

/**
 * @author shixing
 *
 */
public class BufferedStorer {
	private ByteArrayOutputStream bout;
	private BufferedWriter fout;
	private BufferedWriter fileout;
	private int limit;
	private int size=0;
	private String dir;
	private String prefix;
	private int fileIndex=0;
	private String encoding;
	
	/**
	 * 
	 * @param limit 单个文件大小，单位MB
	 * @param dir	输出文件所在文件夹，可以没有，会自动mkdir
	 * @param prefix 输出文件的前缀
	 * @param encoding 输出文件的编码方式
	 */
	public BufferedStorer(int limit,String dir,String prefix,String encoding)
	{
		this.encoding=encoding;
		this.limit=limit*1024*1024;
		this.dir=dir;
		File file=new File(this.dir);
		if (!file.exists())
			file.mkdirs();
		this.prefix=prefix;
		this.bout=new ByteArrayOutputStream();
		try {
			this.fout=new BufferedWriter(new OutputStreamWriter(this.bout,encoding));
			this.fileout=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.toFileString()),encoding));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean addPageOutput(PageOutput po)
	{
		return this.addString(po.toXML());
	}
	
	public boolean addString(String str)
	{
		try {
			this.fout.write(str);
			this.fout.flush();
			if (this.bout.size()+this.size>this.limit)
			{
				return this.writeToFile(true);
			}
			byte a[]=this.bout.toByteArray();
			this.bout.reset();
			this.size+=a.length;
			this.fileout.write(str);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean writeToFile(boolean newfile)
	{
		boolean success=true;
		try
		{
			this.fileout.flush();
			this.fileout.close();
			if (newfile)
				this.fileout=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.toFileString()),encoding));
		} catch(Exception e)
		{
			e.printStackTrace();
			success=false;
		}
		this.size=0;
		return success;
	}
	
	public void close()
	{
		this.writeToFile(false);
		try {
			this.fout.close();
			this.bout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String toFileString()
	{
		String output=this.dir+"/"+this.prefix+"."+this.fileIndex;
		System.out.println(output);
		fileIndex++;
		return output;
	}
	public static void main(String argv[])
	{
		BufferedStorer bs=new BufferedStorer(60,"output","result","utf8");
		String a="shixing dingna!\n";
		for (int i=0;i<8;i++)
		{
			a=a+a;
		}
		a+="*************************\n";
		for (int i=0;i<50000;i++)
		{
			bs.addString(a);
		}
		bs.close();
		
	}
}
