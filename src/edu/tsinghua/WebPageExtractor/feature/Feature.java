/**
 * 
 */
package edu.tsinghua.WebPageExtractor.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.util.SplitUtil;

import edu.tsinghua.WebPageExtractor.elements.HNode;

/**
 * @author shixing
 *
 */
public class Feature {
	private HNode hnode;
	private ArrayList<String> words=new ArrayList<String>();
	private int tokenLength;
	private HashSet<String> tokens=new HashSet<String>();
	private int charLength;
	private double partCharacter;
	private double partDigital;
	private double partSymbol;
	private double partWord;
	
	public Feature(HNode hnode)
	{
		this.hnode=hnode;
	}
	public void initAll()
	{
		this.splitWord(hnode.getText());
		this.initToken();
		this.initCharacter(hnode.getText());
		
	}
	
	private void splitWord(String str)
	{
		String splitStr=SplitUtil.splitString(str);
		hnode.setSplitText(splitStr);
		String[] temp=splitStr.split(" ");
		for (int i=0;i<temp.length;i++)
		{
			String whole=temp[i];
			String parts[]=whole.split("/");
			if (!parts[0].equals(""))
				words.add(parts[0]);
		}
	}
	
	private void initToken()
	{
		this.tokenLength=this.words.size();
		for (int i=0;i<this.words.size();i++)
		{
			this.tokens.add(words.get(i));
		}
	}
	
	private void initCharacter(String str)
	{
		this.charLength=str.length();
		int n[]={0,0,0,0};
		Pattern phz=Pattern.compile("[\\u4e00-\\u9fa5]");
		Pattern pdigital=Pattern.compile("[0-9]");
		Pattern pletter=Pattern.compile("[a-zA-Z]");
		Matcher mhz=phz.matcher(str);
		Matcher mdigital=pdigital.matcher(str);
		Matcher mletter=pletter.matcher(str);
		while(mhz.find())
		{
			n[0]++;
		}
		while(mdigital.find())
		{
			n[1]++;
		}
		while(mletter.find())
		{
			n[2]++;
		}
		int nwhite=0;
		for (int i=0;i<str.length();i++)
		{
			if (str.charAt(i)==' ')
				nwhite++;
				
		}
		n[3]=str.length()-n[0]-n[1]-n[2]-nwhite;
		this.charLength=str.length()-nwhite;
		this.partCharacter=1.0*n[2]/this.charLength;
		this.partDigital=1.0*n[1]/this.charLength;
		this.partSymbol=1.0*n[3]/this.charLength;
		this.partWord=1.0*n[0]/this.charLength;
	}
	
	
	/**
	 * @return the hnode
	 */
	public HNode getHnode() {
		return hnode;
	}
	/**
	 * @param hnode the hnode to set
	 */
	public void setHnode(HNode hnode) {
		this.hnode = hnode;
	}
	/**
	 * @return the words
	 */
	public ArrayList<String> getWords() {
		return words;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	/**
	 * @return the tokenLength
	 */
	public int getTokenLength() {
		return tokenLength;
	}
	/**
	 * @param tokenLength the tokenLength to set
	 */
	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}
	/**
	 * @return the tokens
	 */
	public HashSet<String> getTokens() {
		return tokens;
	}
	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(HashSet<String> tokens) {
		this.tokens = tokens;
	}
	/**
	 * @return the charLength
	 */
	public int getCharLength() {
		return charLength;
	}
	/**
	 * @param charLength the charLength to set
	 */
	public void setCharLength(int charLength) {
		this.charLength = charLength;
	}
	/**
	 * @return the partCharacter
	 */
	public double getPartCharacter() {
		return partCharacter;
	}
	/**
	 * @param partCharacter the partCharacter to set
	 */
	public void setPartCharacter(double partCharacter) {
		this.partCharacter = partCharacter;
	}
	/**
	 * @return the partDigital
	 */
	public double getPartDigital() {
		return partDigital;
	}
	/**
	 * @param partDigital the partDigital to set
	 */
	public void setPartDigital(double partDigital) {
		this.partDigital = partDigital;
	}
	/**
	 * @return the partSymbol
	 */
	public double getPartSymbol() {
		return partSymbol;
	}
	/**
	 * @param partSymbol the partSymbol to set
	 */
	public void setPartSymbol(double partSymbol) {
		this.partSymbol = partSymbol;
	}
	/**
	 * @return the partWord
	 */
	public double getPartWord() {
		return partWord;
	}
	/**
	 * @param partWord the partWord to set
	 */
	public void setPartWord(double partWord) {
		this.partWord = partWord;
	}
	
	public String toFeatureString()
	{
		String output="";
		output+=this.charLength+"\n"
		+this.partWord+"\n"
		+this.partCharacter+"\n"
		+this.partDigital+"\n"
		+this.partSymbol+"\n"
		+this.tokenLength+"\n";
		for (String s:this.tokens)
			output+=s+" ";
		output+="\n";
		return output;
	}
	
	public static void main(String argv[])
	{
		HNode h=new HNode();
		h.setText("我是1个中国人（chinese）！");
		Feature f=new Feature(h);
		f.initAll();
		System.out.println(f.toFeatureString());
		
	}

	
	
}
