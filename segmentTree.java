public class SegmentTree {
	public static void main(String[] args) {
		int[] arr = { 3, 8, 7, 6, -2, -8, 4, 9 };
		SegmentTree tree = new SegmentTree(arr);
		tree.display();
	}

	public class Node {
		int data;
		int startInterval, endInterval;
		Node left, right;

		public Node(int startInterval, int endInterval) {
			this.startInterval = startInterval;
			this.endInterval = endInterval;
		}
	}

	Node root;

	public SegmentTree(int[] arr) {
		// Create the root of the segment tree
		this.root = constructTree(arr, 0, arr.length - 1);
	}

	private Node constructTree(int[] arr, int start, int end) {
		if (start == end) {
			// Create and return a leaf node with data from the input array
			Node leaf = new Node(start, end);
			leaf.data = arr[start];
			return leaf;
		}
		// Create a new node representing a range and recursively build the tree
		Node node = new Node(start, end);
		int mid = (start + end) / 2;
		node.left = constructTree(arr, start, mid);
		node.right = constructTree(arr, mid + 1, end);
		node.data = node.left.data + node.right.data;
		return node;
	}

	public void display() {
		display(this.root);
	}

	private void display(Node node) {
		String str = " ";

		if (node.left != null) {
			str = str + "Interval=[" + node.left.startInterval + "-" + node.left.endInterval + "] and data: "
					+ node.left.data + " =>";
		} else {
			str = str + "No left child";
		}
		// Display information for the current node
		str = str + "Interval= [" + node.startInterval + "-" + node.endInterval + "] and data: " + node.data + " =>";

		if (node.right != null) {
			str = str + " interval= [" + node.right.startInterval + "-" + node.right.endInterval + "] and data: "
					+ node.right.data + " =>";
		} else {
			str = str + "No right child";
		}
		System.out.println(str + '\n');

		// Recursively display left and right subtrees if they exist
		if (node.left != null) {
			display(node.left);
		}
		if (node.right != null) {
			display(node.right);
		}
	}

	// Query
	public int query(int qsi, int qei) {
		return this.query(this.root, qsi, qei);
	}

	private int query(Node node, int qsi, int qei) {
		if (node.startInterval >= qsi && node.endInterval <= qei) {
			// Node is completely lying inside the query interval, return its data
			return node.data;
		} else if (node.startInterval > qei || node.endInterval < qsi) {
			// Node is completely outside the query interval, return 0
			return 0;
		} else {
			// Node partially overlaps with the query interval, recursively query left and
			// right subtrees
			return this.query(node.left, qsi, qei) + this.query(node.right, qsi, qei);
		}
	}

	// Update
	public void update(int index, int value) {
		this.update(this.root, index, value);
	}

	private void update(Node node, int index, int value) {
		if (node.startInterval == node.endInterval) {
			// Leaf node, update its data with the new value
			node.data = value;
			return;
		}
		int mid = (node.startInterval + node.endInterval) / 2;
		if (index <= mid) {
			// Index falls in the left subtree, recursively update left subtree
			update(node.left, index, value);
		} else {
			// Index falls in the right subtree, recursively update right subtree
			update(node.right, index, value);
		}
		// Update the current node's data based on the updated children
		node.data = node.left.data + node.right.data;
	}
}
