package cs284;

import java.util.ArrayList;

//Brian Sampson
//CS284 - HW-4
//3/22/22
//I pledge my honor that I have abided by the Stevens Honor System.

public class BST<E extends Comparable<? super E>> {

	private Node<E> root; // Root of BST
	public ArrayList<E> GLOBAL_REMOVE_HOLDER = new ArrayList<E>(); // Data holder for remove()

	// Node class for BST
	public static class Node<E> {
		private Node<E> right;
		private Node<E> left;
		private E data;

		public Node() {
			this(null);
		}

		public Node(E data) {
			this.data = data;
			this.right = null;
			this.left = null;
		}

		public Node(E data, Node<E> right, Node<E> left) {
			this.data = data;
			this.right = right;
			this.left = left;
		}
	}

	// Creates Degenerate Trees
	public static BST<Integer> makeDegenerate(int size) {
		BST ret = new BST();
		int count = 0;

		while (size > 0) {
			ret.insert(count);
			size--;
			count++;
		}

		return ret;
	}

	// Creates Perfect Trees
	public static BST<Integer> makePerfect(int height) {
		int size = (int) (Math.pow(2, height) - 1);
		int[] arr = new int[size];

		if (height == 0) {
			return new BST();
		}

		if (height == 1) {
			return new BST(new Node(1));
		}

		for (int i = size - 1; i > 0; i--) {
			arr[i] = i;
		}

		return new BST(makePerfectHelper(arr, 0, arr[arr.length - 1]));
	}

	public static Node makePerfectHelper(int[] nums, int low, int high) {

		if (low > high) {
			return null;
		}

		int mid = low + (high - low) / 2;
		Node head = new Node(nums[mid]);

		head.left = makePerfectHelper(nums, low, mid - 1);
		head.right = makePerfectHelper(nums, mid + 1, high);

		return head;
	}

	// BST constructors
	public BST() {
		this.root = null;
	}

	public BST(Node<E> root) {
		this.root = root;
	}

	public int size() {
		return sizeHelper(this.root);
	}

	private int sizeHelper(Node<E> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + sizeHelper(node.right) + sizeHelper(node.left);
		}
	}

	// Takes the height of the BST
	public int height() {
		return heightHelper(this.root);
	}

	public int heightHelper(Node<E> node) {
		if (node == null)
			return 0;
		else {
			int lDepth = heightHelper(node.left);
			int rDepth = heightHelper(node.right);

			if (lDepth > rDepth)
				return (lDepth + 1);
			else
				return (rDepth + 1);
		}
	}

	// Returns true if the BST is degenerate, else false
	public boolean isDegenerate() {
		if (this.root == null) {
			return true;
		}
		return isDegenerateHelper(this.root);
	}

	public boolean isDegenerateHelper(Node<E> node) {
		if (node.right == null && node.left == null) {
			return true;
		} else if (node.right == null && node.left != null) {
			return true && isDegenerateHelper(node.left);
		} else if (node.left == null && node.right != null) {
			return true && isDegenerateHelper(node.right);
		} else {
			return false;
		}
	}

	// Returns true if the BST is perfect, else false
	public boolean isPerfect() {
		if (this.root == null) {
			return true;
		}
		return isPerfectHelper(this.root);
	}

	public boolean isPerfectHelper(Node<E> node) {
		if (node.right == null && node.left == null) {
			return true;
		} else if (node.right != null && node.left != null && heightHelper(node.right) == heightHelper(node.left)) {
			return isPerfectHelper(node.right) && isPerfectHelper(node.left);
		} else {
			return false;
		}
	}

	//Inserts an element elm into a BST. Returns old element if it is the same.
	public E insert(E elm) {
		if (elm == null) {
			return null;
		}

		if (root == null) {
			root = new Node(elm);
		} else {
			Node<E> parent = null;
			Node<E> current = root;
			while (current != null) {
				if (elm.compareTo(current.data) < 0) {
					parent = current;
					current = current.left;
				} else if (elm.compareTo(current.data) > 0) {
					parent = current;
					current = current.right;
				} else {
					return current.data; // duplicate node
				}
			}
			if (elm.compareTo(parent.data) < 0) {
				parent.left = new Node(elm);
			} else {
				parent.right = new Node(elm);
			}
		}
		return null;
	}

	//Returns elm if found in the BST else it will throw a runtimeException
	public E lookup(E elm) {
		if (!this.contains(elm)) {
			throw new RuntimeException("there is no such element e");
		} else {
			return lookupHelper(elm, this.root);
		}
	}

	public E lookupHelper(E elm, Node<E> node) {
		if (!node.data.equals(elm) && node.right == null && node.left == null) {
			return null;
		} else if (node.data.equals(elm)) {
			return node.data;
		} else if (node.left != null && elm.compareTo(node.data) < 0) {
			return lookupHelper(elm, node.left);
		} else if (node.right != null && elm.compareTo(node.data) > 0) {
			return lookupHelper(elm, node.right);
		}

		return null;
	}

	//If the BST contains elm, it will return true. Else false.
	public boolean contains(E elm) {
		if (root == null) {
			return false;
		}
		return containsHelper(elm, this.root);
	}

	public boolean containsHelper(E elem, Node<E> node) {
		if (node.data == null) {
			return false;
		} else if (node.left != null && elem.compareTo(node.data) < 0) {
			return false || containsHelper(elem, node.left);
		} else if (node.right != null && elem.compareTo(node.data) > 0) {
			return false || containsHelper(elem, node.right);
		} else if (node.data.equals(elem)) {
			return true;
		}
		return false;
	}

	//Testing method to see what is in the BST in order.
	public ArrayList<E> inOrder(Node<E> node) {
		ArrayList<E> ret = new ArrayList<>();
		inOrderHelper(node, ret);
		return ret;

	}

	public void inOrderHelper(Node<E> node, ArrayList<E> ret) {
		if (node != null) {
			inOrderHelper(node.left, ret);
			ret.add((E) node.data);
			inOrderHelper(node.right, ret);
		}
	}

	//min helper method for remove. Returns the left most data of node
	public E min(Node<E> node) {
		E min = node.data;

		while (node.left != null) {
			min = node.left.data;
			node = node.left;
		}
		return min;
	}

	//Removes elm from BST and returns the old element. If elm isnt in BST, return null.
	public E remove(E elm) {
		int size = this.size();
		GLOBAL_REMOVE_HOLDER.add(null);
		E hold = null;

		if (this.root == null) {
			return null;
		} else if (this.root.data.compareTo(elm) == 0 && size == 1) {
			E ret = this.root.data;
			this.root = null;
			return ret;
		}

		this.removeHelper(this.root, elm);

		if (GLOBAL_REMOVE_HOLDER.get(GLOBAL_REMOVE_HOLDER.size() - 1) != null
				&& GLOBAL_REMOVE_HOLDER.get(GLOBAL_REMOVE_HOLDER.size() - 2) != null) {
			return GLOBAL_REMOVE_HOLDER.get(GLOBAL_REMOVE_HOLDER.size() - 2);
		}

		return GLOBAL_REMOVE_HOLDER.get(GLOBAL_REMOVE_HOLDER.size() - 1);
	}

	public Node<E> removeHelper(Node<E> node, E elm) {
		if (node == null) {
			return null;
		}

		if (this.root.data.compareTo(elm) == 0 && (this.root.right == null || this.root.left == null)) {
			if (this.root.right == null) {
				Node<E> hold = this.root;
				this.root = this.root.left;
				GLOBAL_REMOVE_HOLDER.add(hold.data);
				return hold;
			} else if (this.root.left == null) {
				Node<E> hold = this.root;
				this.root = this.root.right;
				GLOBAL_REMOVE_HOLDER.add(hold.data);
				return hold;
			}
		}

		if (elm.compareTo(node.data) < 0) {
			node.left = removeHelper(node.left, elm);
		} else if (elm.compareTo(node.data) > 0) {
			node.right = removeHelper(node.right, elm);
		} else {
			GLOBAL_REMOVE_HOLDER.add(node.data);
			if (node.left == null) {
				return node.right;
			} else if (node.right == null) {
				return node.left;
			}
			node.data = min(node.right);
			node.right = removeHelper(node.right, node.data);
		}
		return node;

	}

	public static void main(String[] args) {
		BST t = makeDegenerate(1);

		BST t2 = makePerfect(4);

		BST t3 = new BST(new Node(10, new Node(20, null, new Node(15)), new Node(5)));

		System.out.println(t2.root.data);
		System.out.println(t2.remove(9));
		System.out.println(t2.inOrder(t2.root));

	}

}