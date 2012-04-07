/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

import java.util.ArrayList;
import java.util.HashMap;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.tags.CompositeTag;

import edu.tsinghua.WebPageExtractor.feature.Feature;

/**
 * @author shixing
 *
 */
public class HNode extends AbstractHNode {
	private HNode parent=null;
	private ArrayList<HNode> children=new ArrayList<HNode>();
	private String tag=null;
	private HashMap<String,String> attributes = new HashMap<String,String>();
	private String text=null;
	private String splitText=null;
	public void setSplitText(String splitText) {
		this.splitText = splitText;
	}
	private String matchText=null;
	private ChildrenOrganize childrenOrganize=new ChildrenOrganize();
	private int childrenId=0;
	private Feature feature=new Feature(this);
	
	/**
	 * @return the childrenId
	 */
	public int getChildrenId() {
		return childrenId;
	}

	/**
	 * @param childrenId the childrenId to set
	 */
	public void setChildrenId(int childrenId) {
		this.childrenId = childrenId;
	}

	/**
	 * @return the matchText
	 */
	public String getMatchText() {
		return matchText;
	}

	/**
	 * @param matchText the matchText to set
	 */
	public void setMatchText(String matchText) {
		this.matchText = matchText;
	}

	/**
	 * @return the dataRecord
	 */
	public ChildrenOrganize getChildrenOrganize() {
		return childrenOrganize;
	}

	/**
	 * @param dataRecord the dataRecord to set
	 */
	public void setChildrenOrganize(ChildrenOrganize dataRecord) {
		this.childrenOrganize = dataRecord;
	}

	public static String getTagName(Node node)
	{
		String tagstr=null;
		if (node.toPlainTextString().length()==node.toHtml().length())
		{
			String t=node.toHtml().replaceAll("\\s", "");
			t=t.replaceAll("[\r\n]", "");
			if (t.length()==0)
				tagstr="EMPTY";
			else
				tagstr="TEXT";
		}
		else
		{	
			try{
			Tag tag=(Tag)node;
			tagstr=tag.getTagName();
			//System.out.println(tag.toHtml());
			}catch(Exception e)
			{
				tagstr="STOP";
				//System.out.println("***"+node.toHtml()+"***");
				//e.printStackTrace();
			}
		}
		String stopTag[]={"EMPTY","SCRIPT","STYLE","BR"};
		for (int i=0;i<stopTag.length;i++)
		{
			if (stopTag[i].equals(tagstr))
			{
				tagstr="STOP";
				break;
			}
		}
		return tagstr;
	}
	
	public void init(Node node,HNode parent)
	{
		String tag=this.getTagName(node);
		if (tag.equals("STOP"))
			return;
		this.tag=tag;
		this.parent=parent;
		if (this.tag.equals("TEXT"))
			this.text=node.toPlainTextString().trim();
		else
		{
			Tag t=(Tag)node;
			String tstr=t.getAttribute("class");
			if (tstr!=null)
				this.attributes.put("class", tstr);
		}
	}
	
	
	
	/**
	 * @return the parent
	 */
	public HNode getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(HNode parent) {
		this.parent = parent;
	}
	/**
	 * @return the children
	 */
	public ArrayList<HNode> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<HNode> children) {
		this.children = children;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * @return the attributes
	 */
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the splitText
	 */
	public String getSplitText() {
		return splitText;
	}

	/**
	 * @param splitText the splitText to set
	 */
	
	public String toString()
	{
		String output="";
		if (this.tag.equals("TEXT"))
			output=this.tag+" \""+this.text+"\"";
		else
		{
			output=this.tag;
			if (this.attributes.containsKey("class"))
				output+="(" +this.attributes.get("class")+ ")";
		}
		return output;
	}
	public String getAttribute(String key)
	{
		String output="";
		if (this.attributes.containsKey(key))
			output=this.attributes.get(key);
		return output;
	}
	
}
