/**
 * 
 */
package edu.tsinghua.WebPageExtractor.extractor;

import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.ontology.Ontology;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;

/**
 * @author shixing
 * ������һ����ȡ������ʵ�ֵĽӿ�
 */
public interface Extractor {
	/**
	 * 
	 * ���ܣ���һ��ҳ�棨page���У�����ontologyȥ��ȡ����
	 * ����ֵ˵����
	 * @param page 
	 * @return PageOutput
	 */
	public PageOutput extract(Page page,Ontology ontology);
}
