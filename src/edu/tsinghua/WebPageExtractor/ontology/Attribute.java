/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

import edu.tsinghua.WebPageExtractor.util.XMLUtil;

/**
 * @author shixing
 *
 */
public class Attribute {
	
	private String typeName=null;
	private String HBColumnName=null;
	private String HBDataType=null;
	private ArrayList<Wrapper> wrappers=new ArrayList<Wrapper>();
	private boolean canNull=true;
	
	//子类根据需求重载
	public void initAll(org.w3c.dom.Node node)
	{
		initBasic(node);
	}
	
	private void initBasic(org.w3c.dom.Node node)
	{
		this.initTypeName(node);
		this.initWrappers(node);
		this.initCanNull(node);
		this.initHBColumnName(node);
		this.initHBDataType(node);
	}
	
	private void initTypeName(org.w3c.dom.Node node)
	{
		this.typeName=XMLUtil.getAttributeInXML(node, "typeName", "value");
	}
	
	private void initCanNull(org.w3c.dom.Node node)
	{
		this.canNull=Boolean.parseBoolean((XMLUtil.getAttributeInXML(node, "canNull", "value")));
	}
	
	private void initHBColumnName(org.w3c.dom.Node node)
	{
		this.HBColumnName=XMLUtil.getAttributeInXML(node, "HBColumnName", "value");
	}

	private void initHBDataType(org.w3c.dom.Node node)
	{
		this.HBDataType=XMLUtil.getAttributeInXML(node, "HBDataType", "value");
	}
	
	private void initWrappers(org.w3c.dom.Node node)
	{
		
		for (int i=0;i<node.getChildNodes().getLength();i++)
		{
			org.w3c.dom.Node n=node.getChildNodes().item(i);
			if (n.getNodeName().equals("wrapper"))
			{
			Wrapper w=new Wrapper();
			w.initAll(n);
			this.wrappers.add(w);
			}
		}
	}
	
	public String toString()
	{
		String output="";
		output+="typeName:"+this.typeName+"\n"+
		"HBColumnName:"+this.HBColumnName+"\n"+
		"HBDataType:"+this.HBDataType+"\n"+
		"canNull:"+this.canNull+"\n"
		+"wrappers:"+"\n";
		for (Wrapper w:this.wrappers)
		{
			output+=w.toString()+"\n";
		}
		output+="\n";
		return output;
	}
	
	public Field generateFiled()
	{
		Field field=new Field(this);
		return field;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the wrappers
	 */
	public ArrayList<Wrapper> getWrappers() {
		return wrappers;
	}

	/**
	 * @param wrappers the wrappers to set
	 */
	public void setWrappers(ArrayList<Wrapper> wrappers) {
		this.wrappers = wrappers;
	}

	/**
	 * @return the canNull
	 */
	public boolean isCanNull() {
		return canNull;
	}

	/**
	 * @param canNull the canNull to set
	 */
	public void setCanNull(boolean canNull) {
		this.canNull = canNull;
	}
	/**
	 * @return the hBColumnName
	 */
	public String getHBColumnName() {
		return HBColumnName;
	}

	/**
	 * @param hBColumnName the hBColumnName to set
	 */
	public void setHBColumnName(String hBColumnName) {
		HBColumnName = hBColumnName;
	}

	/**
	 * @return the hBDataType
	 */
	public String getHBDataType() {
		return HBDataType;
	}

	/**
	 * @param hBDataType the hBDataType to set
	 */
	public void setHBDataType(String hBDataType) {
		HBDataType = hBDataType;
	}
}
