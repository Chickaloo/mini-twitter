import javax.swing.tree.TreeNode;

// Provides a way for AdminPanelUI to manage nodes in the tree.
// However, this may not be necessary as the Database model that I'm using
// allows me to manage nodes without the extra overhead.
public abstract class AbstractUserNode implements TreeNode {
	public abstract void add(AbstractUserNode u);
}
