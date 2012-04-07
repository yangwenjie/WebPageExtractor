/**
 * 
 */
package edu.tsinghua.WebPageExtractor.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
/**
 * @author shixing
 *
 */
public class Config {
	private static final String configFilePath="config/config.txt";
	private static Config config=new Config();
	private HashMap<String,String> kvs=new HashMap<String,String>();
	private Config()
	{
		String path="";
		try {
			BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream(configFilePath),"utf8"));
			path=fin.readLine();
			readConfigFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void readConfigFile(String filePath)
	{
		try
		{
			BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf8"));
			String line=null;
			while ((line=fin.readLine())!=null)
			{
				String ll[]=line.split("=");
				if (ll.length==2)
					kvs.put(ll[0], ll[1]);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public String getConfigValue(String key)
	{
		String value=null;
		value=kvs.get(key);
		if (value!=null && value.startsWith("$"))
			value=kvs.get(value.substring(1));
		return value;
	}
	public static Config getNewInstance()
	{
		return config;
	}
}
