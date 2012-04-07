/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

/**
 * @author shixing
 *
 */
public interface Visitor {
	public abstract void visitor(AbstractHNode ahnode);
	public abstract String stringVisitor(AbstractHNode abnode,int level);
}

