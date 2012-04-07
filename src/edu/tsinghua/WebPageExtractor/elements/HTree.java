/**
 * 
 */
package edu.tsinghua.WebPageExtractor.elements;

import java.util.*;


import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


/**
 * @author shixing
 *
 */
public class HTree {
	private HNode root=null;
	private int matchStringDepth=3;
	private int maxConNodes=5;
	
	public HTree(int matchStringDepth)
	{
		this.matchStringDepth=matchStringDepth;
	}
	
	public void buildTree(Parser p)
	{
		NodeFilter rootFilter=new TagNameFilter("html");
		NodeList nodes;
		try {
			nodes = p.parse(rootFilter);
			if (nodes.size()>0)
			{
				HNode rootFather=new HNode();
				rootFather.setMatchText("ROOTFATHER");
				this.root=new HNode();
				this.root.init(nodes.elementAt(0), rootFather);
				this.addChild(this.root,nodes.elementAt(0), p);
			}
			else 
				return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addChild(HNode hnode,Node node,Parser p)
	{
		String tag=HNode.getTagName(node);
		if (tag.equals("STOP") || tag.equals("TEXT"))
			return;
		if (node.getChildren()==null) return;
		for(int i=0;i<node.getChildren().size();i++)
		{
			Node inode=node.getChildren().elementAt(i);
			String itag=HNode.getTagName(inode);
			if (itag.equals("STOP"))
				continue;
			HNode ihnode=new HNode();
			ihnode.init(inode, hnode);
			hnode.getChildren().add(ihnode);
			this.addChild(ihnode, inode, p);
		}
	}
	

	
	
	public void generalizeTree()
	{
		Stack<HNode> stack=new Stack<HNode>();
		Queue<HNode> tempStack=new LinkedList<HNode>();
		stack.push(this.root);
		tempStack.offer(this.root);
		//add the whole tree into the stack;
		while(!tempStack.isEmpty())
		{
			HNode current=tempStack.poll();
			for (int i=0;i<current.getChildren().size();i++)
			{
				HNode ihnode=current.getChildren().get(i);
				tempStack.offer(ihnode);
				stack.push(ihnode);
			}
		}
		//按照宽搜的倒序输出
		HNode preFather=null;
		HNode currentFather=null;
		HNode currentHNode=null;
		
		while(!stack.isEmpty())
		{
			currentHNode=stack.pop();
			currentFather=currentHNode.getParent();
			if (currentFather!=preFather && preFather!=null)
			{
				//对preFather的孩子进行兄弟间的比较，生成preFather的DataRecord
				//这样可以保证，在计算父亲的DataRecord的时候，所有的孩子都的matchString都已经完成
				//采用完全相等的方法，进行等分比较。
				
				//分桶
				ArrayList<HNode> children=preFather.getChildren();
				ArrayList<ArrayList<HNode>> bins=new ArrayList<ArrayList<HNode>>();
				HashMap<String,Integer> indexMap=new HashMap<String,Integer>();
				
				for (int i=0;i<children.size();i++)
				{
					HNode child=children.get(i);
					child.setChildrenId(i);
					if (!indexMap.containsKey(child.getMatchText()))
					{
						indexMap.put(child.getMatchText(), bins.size());
						ArrayList<HNode> bin=new ArrayList<HNode>();
						bin.add(child);
						bins.add(bin);
					}
					else
					{
						int index=indexMap.get(child.getMatchText());
						bins.get(index).add(child);
					}
				}
				
				//扫描归类
				int binIndex=0;
				int start=binIndex;
				int curSize=0;
				int preSize=-1;
				int curChildId=0;
				int preChildId=-1;
				
				bins.add(new ArrayList<HNode>());	//在末尾增加一个哨兵节点
				
				while (binIndex<bins.size())
				{
					curSize=bins.get(binIndex).size();
					if(bins.get(binIndex).size()>0)
						curChildId=bins.get(binIndex).get(0).getChildrenId();
					else
						curChildId=-1;	//触发末尾的哨兵
					
					if (preSize==1 	||							//桶中只有一个元素的
							curSize!=preSize && binIndex!=0 ||			//桶中的元素数量发生变化时
							curChildId!=preChildId+1 && binIndex!=0 ||	//桶中的第一个元素不连续时
							binIndex==bins.size()-1)					//最后一个元素
					{
						
							ArrayList<GeneralizedNode> dataSheet=new ArrayList<GeneralizedNode>();
							for (int i=0;i<bins.get(start).size();i++)
							{
								GeneralizedNode dr=new GeneralizedNode();
								for (int j=start;j<binIndex;j++)
									dr.getAttribute().add(bins.get(j).get(i));
								dataSheet.add(dr);
							}
							preFather.getChildrenOrganize().getRecords().add(dataSheet);
							start=binIndex;
					}
					binIndex++;
					preSize=curSize;
					preChildId=curChildId;
				}
			}
			//计算当前节点的matchString;
			currentHNode.setMatchText(this.generateMatchString(currentHNode, matchStringDepth));
			preFather=currentFather;
		}
	}
	
	public String generateMatchString(HNode hnode,int depth)
	{
		StringBuffer output=new StringBuffer();
		output.append(hnode.getTag()+"("+hnode.getAttribute("class")+")");
		this.addChildrenMatchString(hnode, 1, depth, output);
		return output.toString();
	}
	public void addChildrenMatchString(HNode hnode,int cdepth,int depth,StringBuffer output)
	{
		if (cdepth==depth) return;
		ChildrenOrganize co=hnode.getChildrenOrganize();
		for (int i=0;i<co.getRecords().size();i++)
		{
			ArrayList<GeneralizedNode> dataRecords=co.getRecords().get(i);
			ArrayList<HNode> attributes=dataRecords.get(0).getAttribute();
				for (int j=0;j<attributes.size();j++)
				{
					HNode ihnode=attributes.get(j);
					output.append(ihnode.getTag()+"("+hnode.getAttribute("class")+")");
					this.addChildrenMatchString(ihnode, cdepth+1, depth, output);
				}
			
		}
	}
	

	
	public String toStringByDFS(Visitor visitor)
	{
		//每次迭代负责增加孩子
		StringBuffer sb=new StringBuffer("");
		sb.append(this.root.stringAccept(visitor,0));
		this.addChildStringByDFS(this.root, sb, 1, visitor);
		return sb.toString();
	}
	public void addChildStringByDFS(HNode hnode,StringBuffer output,int childlevel, Visitor visitor)
	{
		for (int i=0;i<hnode.getChildren().size();i++)
		{
			HNode child=hnode.getChildren().get(i);
			output.append(child.stringAccept(visitor,childlevel));
			this.addChildStringByDFS(child, output,childlevel+1,visitor);
		}
	}
	
	
	
	public String toTabString()
	{
		Visitor visitor=new TabStringVisitor();
		return this.toStringByDFS(visitor);
	}
	
	public String toRecordString()
	{
		Visitor visitor=new RecordStringVisitor();
		return this.toStringByDFS(visitor);
	}
	
	
	
	/**
	 * @return the root
	 */
	public HNode getRoot() {
		return root;
	}
	/**
	 * @param root the root to set
	 */
	public void setRoot(HNode root) {
		this.root = root;
	}
}

class TabStringVisitor implements Visitor
{

	/* (non-Javadoc)
	 * @see edu.tsinghua.WebPageExtractor.elements.Visitor#stringVisitor(edu.tsinghua.WebPageExtractor.elements.AbstractHNode)
	 */
	@Override
	public String stringVisitor(AbstractHNode abnode,int level) {
		// TODO Auto-generated method stub
		String tabString="";
		for (int i=0;i<level;i++)
			tabString+="-|";
		String output="";
		if (abnode instanceof HNode)
		{
			abnode=(HNode)abnode;
			output=tabString+abnode.toString()+"\n";
			
		}
		return output;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.WebPageExtractor.elements.Visitor#visitor(edu.tsinghua.WebPageExtractor.elements.AbstractHNode)
	 */
	@Override
	public void visitor(AbstractHNode ahnode) {
		// TODO Auto-generated method stub
	}
	
}
class RecordStringVisitor implements Visitor
{

	/* (non-Javadoc)
	 * @see edu.tsinghua.WebPageExtractor.elements.Visitor#stringVisitor(edu.tsinghua.WebPageExtractor.elements.AbstractHNode, int)
	 */
	@Override
	public String stringVisitor(AbstractHNode abnode, int level) {
		// TODO Auto-generated method stub
		String output="";
		String tabString="";
		
		for (int i=0;i<level;i++)
		{
			tabString+="-|";
			
		}
		if (abnode instanceof HNode)
		{
			abnode=(HNode)abnode;
			output=tabString+abnode.toString()+"\n"
			+((HNode)abnode).getChildrenOrganize().toString(level)+"\n";
		}
		return output;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.WebPageExtractor.elements.Visitor#visitor(edu.tsinghua.WebPageExtractor.elements.AbstractHNode)
	 */
	@Override
	public void visitor(AbstractHNode ahnode) {
		// TODO Auto-generated method stub
		
	}
	
}
