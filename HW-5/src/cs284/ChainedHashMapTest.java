package cs284;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;


class ChainedHashMapTest {

	@Test
	void testIncreasingNumsWithRemoval() {
	    Map<String, String> m = new ChainedHashMap<>();

	    ArrayList<Integer> keys = new ArrayList<>();
	    for (int i = 0; i < 1024; i += 1) {
	      keys.add(i);
	      assertEquals(null, m.put("key" + i, "val" + i), "fresh key inserts correctly");
	      assertEquals(i + 1, m.size(), "size goes up by 1");
	      assertEquals("val" + i, m.get("key" + i), "fresh key gets correctly");
	      assertEquals("val" + i, m.get("key" + i), "fresh key contained correctly");
	    }

	    Collections.shuffle(keys);

	    List<Integer> removed = keys.subList(0, 512);
	    List<Integer> remaining = keys.subList(512, 1024);

	    int count = 0;
	    for (int k : removed) {
	      assertEquals("val" + k, m.remove("key" + k), "remove returns old key");
	      assertFalse(m.containsKey("key" + k), "value actually removed");
	      count += 1;
	      assertEquals(1024 - count, m.size(), "count changes correctly");
	    }
	    assertEquals(512, m.size(), "512 elements left");

	    for (int k : remaining) {
	      assertEquals("val" + k, m.get("key" + k), "get finds remaining values");
	      assertEquals(true, m.containsKey("key" + k), "containsKey finds remaining values");
	      assertEquals("val" + k, m.put("key" + k, "val" + k + "'"), "unremoved values still present, returned by put");
	    }

	    for (Entry<String, String> e : m) {
	      String k = e.key();
	      assertEquals("key", k.substring(0, 3), "key correct");
	      int num = Integer.parseInt(k.substring(3));
	      assertEquals("val" + num + "'", e.value(), "value correct");
	      assertEquals(true, remaining.remove((Integer) num), "key was in the remaining set");
	    }
	    assertEquals(0, remaining.size(), "all remaining elements accounted for");
	  }

}
