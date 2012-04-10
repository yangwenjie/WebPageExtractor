/**
 * 
 */
package edu.tsinghua.WebPageExtractor.blogNews;

import java.util.ArrayList;

import edu.tsinghua.WebPageExtractor.filter.*;
import edu.tsinghua.WebPageExtractor.elements.Page;
import edu.tsinghua.WebPageExtractor.extractor.Extractor;
import edu.tsinghua.WebPageExtractor.filter.PageType;
import edu.tsinghua.WebPageExtractor.ontology.Ontology;
import edu.tsinghua.WebPageExtractor.ontology.PageOutput;

/**
 * @author shixing
 *
 */
public class BlogNewsExtractor implements Extractor {

	/* (non-Javadoc)
	 * @see edu.tsinghua.WebPageExtractor.extractor.Extractor#extract(edu.tsinghua.WebPageExtractor.elements.Page, edu.tsinghua.WebPageExtractor.ontology.Ontology)
	 */
	@Override
	public void extract(Page page, Ontology ontology) {
		// TODO Auto-generated method stub
		WrapperProcessor processor = new WrapperProcessor(page.getPageType(),
				ontology);
		processor.processOnePage(page);
	}
}
