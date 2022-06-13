//################################\\
// CS284 - HW2 : IDLList.java     \\
// Name : Brian Sampson  		  \\
// Date : 2/21/22		  		  \\
//################################\\

package cs284;

import java.lang.StringBuilder;
import java.util.ArrayList;

public class IDLList<E> {

	//Inner class Node<E>
	private class Node<E> {
		
		//Private data fields of Node<E>
		private E data;
		private Node<E> next;
		private Node<E> prev;

		//Constructor 1 of Node class
		//Only one parameter
		public Node(E elem) {
			this.data = elem;
			this.prev = null;
			this.next = null;
		}

		//Constructor 2 of Node class
		//Three parameters (data, prev, next)
		public Node(E elem, Node<E> prev, Node<E> next) {
			this.data = elem;
			this.prev = prev;
			this.next = next;

		}
	}

	//Data fields of IDLList()
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;

	//Constructor of IDLList()
	public IDLList() {
		this.indices = new ArrayList<Node<E>>();
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	//Start of methods...
	
	//Adds elem at the head
	//Always returns true
	public boolean add(E elem) {
		
		//If the ArrayList is empty...
		if (this.size == 0) {

			//Creates new node
			Node<E> extra = new Node<E>(elem, null, null);

			this.head = extra;
			this.tail = extra;

			this.size = this.size + 1;
			this.indices.add(extra);

			return true;

		}

		//If the ArrayList is not empty...
		
		Node<E> oldFirst = this.head;
		Node<E> extra = new Node<E>(elem, null, oldFirst);
		this.head = extra;

		oldFirst.prev = extra;
		this.indices.add(0, extra);

		this.size = this.size + 1;
		return true;

	}

	//Adds elem at index
	//Always returns true
	public boolean add(int index, E elem) {
		if (this.size == 0 && index == 0) {
			return this.add(elem);
		}

		// Invalid index, throw IndexOutOfBoundsException()
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Out of bounds");
		}

		// if the index is 0, use the add(E elem) to add elem at head
		else if (index == 0) {
			return this.add(elem);
		}

		// else if the index is this.size - 1, the elem is added at the tail
		// call append(elem)
		else if (index == this.size - 1) {
			return this.append(elem);
		}
		
		//Else...

		Node<E> currentNode = this.indices.get(index);
		Node<E> nextNode = this.indices.get(index + 1);

		Node<E> newNode = new Node<E>(elem, currentNode, nextNode);

		currentNode.next = newNode;
		nextNode.prev = newNode;
		this.size = this.size + 1;
		this.indices.add(index, newNode);

		return true;

		// else:
		// get the current node at the input index and the prev node of current node
		// create a new node: prev -> prev node of current node
		// next -> current node
		// for the prev node of current node: the next of it is the new node
		// for the current node: the prev is the new node

		// update the size of the IDLList and the indices

	}

	//Adds elem at the end of ArrayList
	//Should always return true
	public boolean append(E elem) {
		// if the list is empty call add(elem)
		if (this.size == 0) {
			this.add(elem);
			return true;
		}

		//If the list is not empty...
		
		Node<E> newNode = new Node<E>(elem, this.tail, null);

		this.tail.next = newNode;
		this.indices.add(newNode);
		this.tail = newNode;
		this.size = this.size + 1;

		return true;

	}

	//Returns the data inside the node at index
	//Returns E
	public E get(int index) {
		// if the index is invaild, throw IllegalArgumentException
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}

		return this.indices.get(index).data;

	}

	//Returns the last element of ArrayList
	//Returns E
	public E getLast() {
		// if the list is empty, throw IllegalArgumentException()
		if (this.size == 0) {
			throw new IllegalArgumentException("List is empty");
		}

		return this.tail.data;

	}

	//Returns the first element of ArrayList
	//Returns E
	public E getFirst() {
		
		// if the list is empty, throw IllegalArgumentException()
		if (this.indices.isEmpty()) {
			throw new IllegalArgumentException("List is empty");
		}

		return this.head.data;
	}

	//Returns the size of the ArrayList
	//Returns int
	public int size() {
		return this.indices.size();
	}

	//A helper remove function that removes the head of ArrayList
	//Returns E
	public E remove() {
		if (this.size == 0) {
			throw new IndexOutOfBoundsException("Empty list");

		} else if (this.size == 1) { 
			//This list only has 1 node, return the data value of the head
			this.head = null;
			this.tail = null;
			this.size = this.size - 1;
			Node<E> returnNode = this.indices.remove(0);

			return returnNode.data;
		}

		//If there is more than one in ArrayList...
		
		Node<E> oldHead = this.head;
		Node<E> returnVal = this.indices.remove(0);
		this.head = oldHead.next;
		this.head.prev = null;
		this.size = this.size - 1;

		return returnVal.data;

	}

	//Removes the first of ArrayList
	//Returns E
	public E removeFirst() {
		// if the list is empty, throw IllegalArgumentException()
		if (this.size == 0) {
			throw new IndexOutOfBoundsException("Empty");
		} else if (this.size == 1) {
			//If there is only one element in ArrayList, call remove()
			return this.remove();
		}

		//If there is more than one element in ArrayList...
		
		Node<E> oldHead = this.head;
		Node<E> removed = this.indices.remove(0);

		this.head = oldHead.next;
		this.head.prev = null;
		this.size = this.size - 1;

		return removed.data;
	}

	//Removes the last of ArrayList
	//Returns E
	public E removeLast() {
		// if the list is empty, throw IndexOutOfBoundsException()
		if (this.size == 0) {
			throw new IndexOutOfBoundsException("Empty");
		} else if (this.size == 1) { 
			// if only 1 node in the list, call remove()
			return this.remove();

		}
		// if more than 1 node
		// get the oldTail of the list
		// if we remove the oldtail:
		// the new tail will be oldtail.prev
		// the next of the new tail will be null
		// update the values of the data fields if needed
		// return the data value of the tail

		Node<E> oldTail = this.tail;

		Node<E> removed = this.indices.remove(this.size() - 1);

		this.tail = oldTail.prev;
		this.tail.next = null;
		this.size = this.size - 1;

		return removed.data;

	}

	//Removes the element at index in ArrayList
	//Returns E
	public E removeAt(int index) {
		//Checks if IndexOutOfBoundsException()
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Out of bounds");
		} else if (this.indices.isEmpty()) {
			//Checks if the ArrayList is empty
			throw new IllegalArgumentException("Empty list");
		}

		//Checks if index is pointing at first or last element in the ArrayList
		if (index == 0 && this.size >= 1) {
			return this.removeFirst();
		} else if (index == this.size - 1) {
			return this.removeLast();
		}

		//Else...

		Node<E> prevNode = this.indices.get(index - 1); // Causing error
		Node<E> nextNode = this.indices.get(index + 1); // Causing error
		Node<E> currentNode = this.indices.remove(index);

		prevNode.next = currentNode.next;
		nextNode.prev = currentNode.prev;

		this.size = this.size - 1;

		if (this.size == 0) {
			this.indices.clear();
		}

		return currentNode.data;
	}

	//Removes elem at its first occurance
	//Returns true if its found, else false
	public boolean remove(E elem) {
		// if the list is empty IllegalArgumentException()
		if (this.size == 0) {
			throw new IllegalArgumentException("List is empty");
		}

		//Traverse the whole ArrayList and search for elem
		for (int i = 0; i < this.size; i = i + 1) {
			if (this.indices.get(i).data.equals(elem)) {
				//If found, remove it at its first occuring index
				this.removeAt(i);
				return true;
			}
		}
		//Otherwise return false
		return false;

	}

	//Converts the arrayList into a python-like array
	//Returns String
	public String toString() {
		if (this.size == 0) {
			return "[]";
		}

		StringBuilder build = new StringBuilder();
		build.append("[");
		for (int i = 0; i < this.size; i = i + 1) {
			build.append(this.indices.get(i).data + ", ");
		}
		build.append("]");
		build.delete(build.length() - 3, build.length() - 1);

		return build.toString();
	}

}
