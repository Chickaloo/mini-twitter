import javax.swing.tree.TreeNode;

// Provides a way for AdminPanelUI to manage nodes in the tree.
// However, this may not be necessary as the Database model that I'm using
// allows me to manage nodes without the extra overhead.
public abstract class AbstractUserNode implements TreeNode {
	private long creationTime;
	private long lastUpdateTime;

	public AbstractUserNode() {
		creationTime = System.currentTimeMillis();
		lastUpdateTime = creationTime;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setUpdateTime() {
		lastUpdateTime = System.currentTimeMillis();
	}

	public abstract void add(AbstractUserNode u);

	public abstract void setParent(AbstractUserNode u);
}
