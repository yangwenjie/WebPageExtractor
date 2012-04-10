/**
 * 
 */
package edu.tsinghua.WebPageExtractor.filter;

/**
 * @author shixing
 *
 */
public enum AttributeType {
	URL,CATEGORY,TIME,AUTHOR,AUTHORURL,NCOMMENT,NFAVOURIT,NREPOST,
	TITLE, BODY, TIMESTAMP, KEYWORD,SOURCE,DOMAIN,NREAD,NREPLY,ID,PARENTID;	
	
	public static AttributeType string2AttributeType(String str)
	{
		if (str.equals("author"))
			return AUTHOR;
		else if (str.equals("authorURL"))
			return AUTHORURL;
		else if (str.equals("body"))
			return BODY;
		else if (str.equals("category"))
			return CATEGORY;
		else if (str.equals("domain"))
			return DOMAIN;
		else if (str.equals("ID"))
			return ID;
		else if (str.equals("keyword"))
			return KEYWORD;
		else if (str.equals("nComment"))
			return NCOMMENT;
		else if (str.equals("nFavourit"))
			return NFAVOURIT;
		else if (str.equals("nRead"))
			return NREAD;
		else if (str.equals("nReply"))
			return NREPLY;
		else if (str.equals("nRepost"))
			return NREPOST;		
		else if (str.equals("parentID"))
			return PARENTID;
		else if (str.equals("source"))
			return SOURCE;
		else if (str.equals("time"))
			return TIME;
		else if (str.equals("timeStamp"))
			return TIMESTAMP;
		else if (str.equals("title"))
			return TITLE;
		else if (str.equals("url"))
			return URL;
		else
			return null;
	}
	
	public static String attributeType2str(AttributeType type) {
		switch (type) {
		case AUTHOR:
			return "author";
		case AUTHORURL:
			return "authorURL";
		case BODY:
			return "body";
		case CATEGORY:
			return "category";
		case DOMAIN:
			return "domain";
		case ID:
			return "ID";
		case KEYWORD:
			return "keyword";
		case NCOMMENT:
			return "nComment";
		case NFAVOURIT:
			return "nFavourit";
		case NREAD:
			return "nRead";
		case NREPLY:
			return "nReply";
		case NREPOST:
			return "nRepost";
		case PARENTID:
			return "parentID";
		case SOURCE:
			return "source";
		case TIME:
			return "time";
		case TITLE:
			return "title";
		case TIMESTAMP:
			return "timeStamp";
		case URL:
			return "url";
		default:
			return null;
		}
	}
}
