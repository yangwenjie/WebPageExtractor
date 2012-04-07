/**
 * 
 */
package edu.tsinghua.WebPageExtractor.filter;

/**
 * @author shixing
 *
 */
public enum PageType {
	NEWS,BLOG,MOVIE,MUSIC,REVIEWS,UNKNOWN;
	public static PageType getNewInstance(String str)
	{
		if (str.equals("news"))
			return NEWS;
		else if (str.equals("blog"))
			return BLOG;
		else if (str.equals("movie"))
			return MOVIE;
		else if (str.equals("music"))
			return MUSIC;
		else if (str.equals("reviews"))
			return REVIEWS;
		else
			return UNKNOWN;
	}
	public String toString()
	{
		switch(this)
		{
		case NEWS:
			return "news";
		case BLOG:
			return "blog";
		case MOVIE:
			return "movie";
		case MUSIC:
			return "music";
		case REVIEWS:
			return "reviews";
			default:
				return "unknown";
		}
	}
}