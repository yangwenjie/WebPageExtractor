/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

/**
 * @author shixing
 *
 */
public class AbstractHNode {
	public void accept(Visitor visitor)
	{
		visitor.visitor(this);
	}
	public String stringAccept(Visitor visitor,int level)
	{
		return visitor.stringVisitor(this,level);
	}
}
