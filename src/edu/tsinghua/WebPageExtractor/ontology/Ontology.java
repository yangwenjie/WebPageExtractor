/**
 * 
 */
package edu.tsinghua.WebPageExtractor.ontology;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import edu.tsinghua.WebPageExtractor.util.XMLUtil;

/**
 * @author shixing
 * 
 */
public class Ontology {
	private AttributeFactory af=null;
	private String ontologyType=null;
	private String HBTableName=null;
	private String ontologyFile=null;
	private org.w3c.dom.Document doc=null;
	private ArrayList<Attribute> attributes=new ArrayList<Attribute>();

	public Ontology(String configFile,AttributeFactory af)
	{
		this.af=af;
		this.ontologyFile=configFile;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc=builder.parse(new File(configFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initAll();
	}
	
	private void initAll()
	{
		this.initOntologyType();
		this.initHBTableName();
		this.initAttributes();
	}
	//子类可以根据需求重载
	protected void initAttributes()
	{
		org.w3c.dom.NodeList attributeXMLs=doc.getElementsByTagName("attribute");
		for (int i=0;i<attributeXMLs.getLength();i++)
		{
			Attribute attribute=this.af.getNewInstance();
			attribute.initAll(attributeXMLs.item(i));
			this.attributes.add(attribute);
		}
	}
	
	private void initOntologyType()
	{
		this.ontologyType=XMLUtil.getAttributeInXML(doc, "ontologyType", "value");
	}
	
	private void initHBTableName()
	{
		this.HBTableName=XMLUtil.getAttributeInXML(doc, "HBTableName", "value");
	}
	
	public DataRecord generateDataRecord()
	{
		DataRecord dr=new DataRecord(this);
		ArrayList<Field> fields=new ArrayList<Field>();
		for (int i=0;i<this.attributes.size();i++)
		{
			Field field=this.attributes.get(i).generateFiled();
			fields.add(field);
		}
		dr.setFields(fields);
		return dr;
	}

	/**
	 * @return the ontologyFile
	 */
	public String getOntologyFile() {
		return ontologyFile;
	}

	/**
	 * @param ontologyFile the ontologyFile to set
	 */
	public void setOntologyFile(String ontologyFile) {
		this.ontologyFile = ontologyFile;
	}

	/**
	 * @return the attributes
	 */
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @return the doc
	 */
	public org.w3c.dom.Document getDoc() {
		return doc;
	}

	/**
	 * @return the ontologyType
	 */
	public String getOntologyType() {
		return ontologyType;
	}
	
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @param doc the doc to set
	 */
	public void setDoc(org.w3c.dom.Document doc) {
		this.doc = doc;
	}
	
	/**
	 * @param ontologyType the ontologyType to set
	 */
	public void setOntologyType(String ontologyType) {
		this.ontologyType = ontologyType;
	}
	
	/**
	 * @return the hBTableName
	 */
	public String getHBTableName() {
		return HBTableName;
	}

	/**
	 * @param hBTableName the hBTableName to set
	 */
	public void setHBTableName(String hBTableName) {
		HBTableName = hBTableName;
	}
}

