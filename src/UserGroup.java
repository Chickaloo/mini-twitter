import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.tree.*;

// Composite model
public class UserGroup extends AbstractUserNode {
	private String name;
	private TreeNode parent;
	private List<TreeNode> children;

	public UserGroup(String s) {
		this.name = s;
		children = new ArrayList<TreeNode>();
	}

	public String toString() {
		return name;
	}

	@Override
	public Enumeration<TreeNode> children() {
		return Collections.enumeration(children);
	}
	@Override
	public boolean getAllowsChildren() {
		return true;
	}
	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}
	@Override
	public int getChildCount() {
		return children.size();
	}
	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}
	public void setParent(TreeNode p) {
		parent = p;
	}
	@Override
	public TreeNode getParent() {
		return parent;
	}
	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void add(AbstractUserNode t) {
		children.add(t);

	}


}
