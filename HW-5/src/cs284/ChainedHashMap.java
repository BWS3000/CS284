//Thank you professor and TA's for a great semester.
///Brian Sampson
//HW-5
//CS 284
//I pledge my honor that I have abided by the Stevens Honor System.

package cs284;

//Imported libraries
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.List;

public class ChainedHashMap<K, V> implements Map<K, V> {

	static class CHMEntry<K, V> implements Entry<K, V> {
		public K key;
		public V val;
		public CHMEntry<K, V> next;

		public CHMEntry(K key, V val, CHMEntry<K, V> next) {
			this.key = key;
			this.val = val;
			this.next = next;
		}

		public V value() {
			return this.val;
		}

		public K key() {
			return this.key;
		}

		public CHMEntry<K, V> getNext() {
			return this.next;
		}

		public void setValue(V val) {
			this.val = val;
		}

		public void setNext(CHMEntry<K, V> next) {
			this.next = next;

		}

	}

	private static final double REHASH_FACTOR = 0.7;
	private static final int INITIAL_SIZE = 64;
	private Object[] table;
	private int size;
	private int deleted;
	private int rehashes;

	//Constructor that makes empty CHM
	public ChainedHashMap() {
		this.table = new Object[INITIAL_SIZE];
	}
	
	// Constructor with specific capacity
		public ChainedHashMap(int capacity) {
			this.table = new Object[capacity];
		}

	public int size() {
		return size;
	}

	public int deleted() {
		return deleted;
	}

	public int capacity() {
		return table.length;
	}

	public int rehashes() {
		return rehashes;
	}

	//Returns the value of a key. Returns null if it doesnt exist in the CHM.
	@SuppressWarnings("unchecked")
	public V get(K key) {
		if (this.size == 0) {
			return null;
		}
		int keyIndex = hash(key);
		for (CHMEntry e = (CHMEntry) this.table[keyIndex]; e != null; e = e.next) {
			if (e.key == key || (e.key != null && e.key.equals(key))) {
				return (V) e.val;
			}
		}

		return null;
	}

	private int hash(K key) {
		int hash = Objects.hashCode(key);

		return Math.abs(hash) % table.length;
	}

	//Puts an entry into the CHM. Returns the old key or null if it doesnt exist.
	public V put(K key, V val) {

		if (this.needsRehash()) {
			this.rehash();
		}

		V ret = null;

		int index = index(key);
		CHMEntry newEntry = new CHMEntry(key, val, null);
		CHMEntry prev = null;
		if (table[index] == null) {
			table[index] = newEntry;
		} else {
			CHMEntry previousNode = null;
			for (CHMEntry e = (CHMEntry) this.table[index]; e != null; e = e.next) {
				if (e.key == key || (e.key != null && e.key.equals(key))) {
					if (this.size > 0) {
						this.size = this.size - 1;
					}
					this.size = this.size + 1;
					ret = (V) e.val;
					e.val = val;
					return ret;
				}
				prev = e;

			}
		}
		if (prev != null) {
			prev.next = newEntry;
		}

		this.size = this.size + 1;
		return ret;
	}

	private int index(K key) {
		if (key == null) {
			return 0;
		}
		return Math.abs(hash(key) % this.capacity());
	}

	//Returns an array of integers with the amount of entries per bucket
	public int[] bucketSizes() {
		int curr;
		int[] arr = new int[this.capacity()];

		for (int i = 0; i < this.capacity(); i = i + 1) {
			curr = 0;

			for (CHMEntry n = (CHMEntry) this.table[i]; n != null; n = n.next) {
				curr = curr + 1;
			}
			arr[i] = curr;
		}
		return arr;
	}

	//Returns true if it exists in the CHM, else false.
	public boolean containsKey(K key) {
		int index = this.hash(key);

		for (CHMEntry e = (CHMEntry) this.table[index]; e != null; e = e.next) {
			if (e.key == key || (e.key != null && e.key.equals(key))) {
				return true;
			}
		}
		return false;
	}

	//Removes the entry containing key from the CHM, returns the old key or null if it doesnt exist
	public V remove(K key) {
		int index = this.index(key);
		CHMEntry ret = null;
		CHMEntry prev = null;

		for (CHMEntry e = (CHMEntry) this.table[index]; e != null; e = e.next) {
			if (e.key == key || (e.key != null && e.key.equals(key))) {
				if (prev != null && e.next != null) {
					prev.next = e.next;
				} else if (prev != null && e.next == null) {
					prev.next = null;
				} else if (prev == null && e.next == null) {
					this.table[index] = null;
				} else if (prev == null && e.next != null) {
					this.table[index] = e.next;
				}
				ret = this.copyOf(e);
				e = null;
				this.size = this.size - 1;
				break;
			}
			prev = e;
		}
		if (ret == null) {
			return null;
		}
		return (V) ret.val;
	}

	//Creates a copy of entry E with distinct memory address
	public CHMEntry copyOf(CHMEntry e) {
		K key = (K) e.key();
		V value = (V) e.value();

		CHMEntry ret = new CHMEntry(key, value, e.next);
		return ret;
	}

	// Returns true when a rehash is needed
	public boolean needsRehash() {
		if (this.size() > this.capacity() * 0.7) {
			return true;
		}
		return false;
	}

	//Rehashes CHM based on HW-5 specificity
	public Object[] rehash() {
		int newCapacity = this.capacity() * 2;

		Object newTable = new Object[newCapacity];
		ChainedHashMap<K, V> ret = new ChainedHashMap<K, V>(newCapacity);

		for (int i = 0; i < this.capacity(); i = i + 1) {
			for (CHMEntry e = (CHMEntry) this.table[i]; e != null; e = e.next) {
				K k = (K) e.key;
				V v = (V) e.val;

				ret.put(k, v);
			}
		}
		this.rehashes = this.rehashes + 1;
		this.table = ret.table;
		return this.table;
	}

	

	//Turns this.table into an array of linked lists
	public LinkedList<CHMEntry<K, V>>[] makeIntoLinkedList() {
		LinkedList<CHMEntry<K, V>>[] values = new LinkedList[this.capacity()];

		for (int i = 0; i < this.capacity(); i++) {
			LinkedList<CHMEntry<K, V>> curr = new LinkedList<>();
			for (CHMEntry e = (CHMEntry) this.table[i]; e != null; e = e.next) {
				curr.add((CHMEntry<K, V>) e);
			}
			values[i] = curr;
		}

		return values;
	}

	//Iterator method based on specificity on HW5
	public Iterator<Entry<K, V>> iterator() {
		LinkedList<CHMEntry<K, V>>[] values = this.makeIntoLinkedList();
		LinkedList<CHMEntry<K, V>> iterated = new LinkedList<>();

		for (LinkedList<CHMEntry<K, V>> value : values) {
			if (value != null) {
				iterated.addAll(value);
			}
		}

		return new Iterator<Entry<K, V>>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < iterated.size();
			}

			@Override
			public Entry<K, V> next() {
				return (Entry<K, V>) iterated.get(index++);
			}
		};
	}

	//DRIVER -- IGNORE
	public static void main(String[] args) {
		int count = 0;
		Map<String, String> m = new ChainedHashMap<>();
		ArrayList<Integer> keys = new ArrayList<>();
		ChainedHashMap test = new ChainedHashMap();

		for (int i = 0; i < 1024; i += 1) {
			keys.add(i);
			m.put("key" + i, "val" + i);
		}

		System.out.println(m.size());

		Collections.shuffle(keys);

		List<Integer> removed = keys.subList(0, 512);
		List<Integer> remaining = keys.subList(512, 1024);

		for (int k : removed) {
			m.remove("key" + k);
		}

		for (int k : remaining) {
			m.put("key" + k, "val" + k + "'");
		}

		for (Entry<String, String> e : m) {
			String k = e.key();
			int num = Integer.parseInt(k.substring(3));
			System.out.println("NUM: " + num + " REMOVE CALL: " + remaining.remove((Integer) num) + " ");
		}
	}
}