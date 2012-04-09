/**
 * 
 */
package edu.tsinghua.WebPageExtractor.extractor;

import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.ontology.Ontology;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;

/**
 * @author shixing
 * 定义了一个抽取器必须实现的接口
 */
public interface Extractor {
	/**
	 * 
	 * 功能：从一个页面（page）中，根绝ontology去抽取内容
	 * 返回值说明：
	 * @param page 
	 * @return PageOutput
	 */
	public PageOutput extract(Page page,Ontology ontology);
}
