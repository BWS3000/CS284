//################################\\
// CS284 - HW2 : IDLListTest.java \\
// Name : Brian Sampson  		  \\
// Date : 2/21/22		  		  \\
//################################\\

package cs284;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDLListTest {
	
	/*
	 * public boolean add(int index, E elem) done
	 * public boolean add(E elem) done
	 * public boolean append(E elem) done
	 * public E get(int index) done
	 * public E getFirst() done
	 * public getLast() done
	 * public int size() done
	 * public E removeFirst() done
	 * public E removeLast() done
	 * public E removeAt(int index) done
	 * public boolean remove(E elem) done
	 * public String toString() done
	 */

	
	//Tests public boolean add(int index, E elem)
    @Test
    public void testAddIntE() {

        IDLList test = new IDLList<>();
        
        for (int i = 0; i < 10; i = i + 1) {
        	test.add(0, i + "");
        }
        
     assertEquals("9", test.get(0));
     assertEquals("0", test.get(9));
     
     assertTrue(test.add(test.size() - 1, "100"));
     assertEquals("100", test.get(test.size() - 1));
        
    }
    
    //Tests public boolean add(E elem)

    @Test
    public void testAddE() {
        IDLList test = new IDLList<>();

        for (int i = 1; i < 10; i++) {
            test.add(i + "");
        }

        test.add(1);
        assertEquals(1, test.get(0));
        assertTrue(test.add(1));

        test.add(2);
        assertEquals(2, test.get(0));
        assertTrue(test.add(2));

        test.add(3);
        assertEquals(3, test.get(0));
        assertTrue(test.add(3));

    }
    
    //Tests public boolean append(E elem)

    @Test
    public void testAppend() {
        IDLList test = new IDLList<>();

        for (int i = 1; i < 10; i++) {
            test.add(i);
        }

        test.append(10);
        assertEquals(10, test.get(test.size() - 1));
        assertTrue(test.append(10));

        test.append(15);
        assertEquals(15, test.get(test.size() - 1));
        assertTrue(test.append(15));

        IDLList<String> test1 = new IDLList<>();

        for (int i = 1; i < 10; i++) {
            test1.add(String.valueOf(i));
        }

        test1.append("10");
        assertEquals("10", test1.get(test1.size() - 1));
        assertTrue(test1.append("10"));

        test1.append("15");
        assertEquals("15", test1.get(test1.size() - 1));
        assertTrue(test1.append("15"));

    }
    
    //Tests public E get(int index)

    @Test
    public void testGetIndex() {

        IDLList test = new IDLList<>();

        for (int i = 0; i < 10; i++) {
            test.add(i + "");
        }
        
        
        assertEquals("0", test.get(test.size() - 1));
        assertEquals("9", test.get(0));
        
    }
    
    //Tests public E getFirst()

    @Test
    public void testGetFirst() {
        IDLList test = new IDLList();
        
       test.add(0);
       
       assertEquals(0, test.getFirst());
       
       test.add(1);
       
       assertEquals(1, test.getFirst());
       
       for (int i = 0; i < 10; i = i + 1) {
    	   test.add(i);
       }
        
       assertEquals(9, test.getFirst());
    }
    
    //Tests public getLast()

    @Test
    public void testGetLast() {
        IDLList test = new IDLList<>();
        
        test.add(0);
        
        assertEquals(0, test.getLast());
        
        test.add(1);
        
        assertEquals(0, test.getLast());
        
        for (int i = 0; i < 10; i = i + 1) {
        	test.add(i);
        }
        
        assertEquals(0, test.getLast());
        
    }
    
    //Tests public int size()

    @Test
    public void testSize() {
        IDLList test = new IDLList<>();
        
        for (int i = 0; i < 10; i = i + 1) {
        	test.add(i);
        }
        
        assertEquals(10, test.size());
        
        test.add(1);
        
        assertEquals(11, test.size());

    }

    //Tests public E removeFirst()
   @Test
   public void testRemoveFirst() {
	   IDLList test = new IDLList<>();
	   
	   for (int i = 0; i < 10; i = i + 1) {
		   test.add(i);
	   }
	   
	  assertEquals(9, test.removeFirst());
	  assertEquals(8, test.removeFirst());
	  assertEquals(7, test.removeFirst());
	  
	  IDLList fail1 = new IDLList<>();
	 
	  
	  try {
          fail1.removeFirst();
          fail();
      } catch (IndexOutOfBoundsException e) {
          assertEquals(1, 1);
      } 
	   
   }

   //Tests public E removeLast()
    @Test
    public void testRemoveLast() {
        IDLList fail1 = new IDLList<>();

        // An exception should be thrown here 
        
        try {
            fail1.removeLast();
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(1, 1);
        } 
        
        IDLList test = new IDLList<>();
        
        for (int i = 0 ; i < 10; i = i + 1) {
        	test.add(i);
        }
        
        assertEquals(0, test.removeLast());
        assertEquals(1, test.removeLast());
        assertEquals(2, test.removeLast());
        
    }

    //Tests public E removeAt(int index)
    @Test
    public void testRemoveAt() {
        IDLList test = new IDLList<>();

        for (int i = 0; i < 10; i++) {
            test.add(i);
        }

        // An exception should be thrown here
        try {
            test.removeAt(100);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(1, 1);
        }

        assertEquals(test.get(0), test.removeAt(0));

        assertEquals(test.get(test.size() - 1), test.removeAt(test.size() - 1));

        assertEquals(test.get(5), test.removeAt(5));
    }

    //Tests public boolean remove(E elem)
    @Test
    public void testRemoveE() {
        IDLList test = new IDLList<>();

        for (int i = 0; i < 10; i++) {
            test.add(i);
        }

        test.remove(999);
        assertFalse(test.remove(999));

        assertTrue(test.remove(0));

        test.remove(0);
        assertNotEquals(test.get(test.size() - 1), 0);

        test.append(8);
        test.append(8);
        assertEquals(test.get(test.size() - 1), 8);
    }

    //Tests public String toString()
    @Test
    public void testToString() {
        IDLList<Integer> test = new IDLList<>();
        IDLList test2 = new IDLList<>();

        for (int i = 0; i < 10; i++) {
            test.add(i);
        }
        
        assertEquals("[]", test2.toString());
        assertEquals("[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]", test.toString());
    }
}