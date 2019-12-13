package game.building;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class MathPlusTest
{
	@Test
	public void testFact ()
	{
		assertEquals(1, MathPlus.fact(0));
		assertEquals(1, MathPlus.fact(1));
		assertEquals(2, MathPlus.fact(2));
		assertEquals(3628800, MathPlus.fact(10));
		assertThrows(IllegalArgumentException.class, () -> MathPlus.fact(-1));
	}
	
	@Test
	public void testCombinaison ()
	{
		assertEquals(1, MathPlus.combinaison(0, 5));
		assertThrows(IllegalArgumentException.class, () -> MathPlus.combinaison(5, 0));
		assertEquals(1, MathPlus.combinaison(0, 0));
		assertEquals(252, MathPlus.combinaison(5, 10));
		assertThrows(IllegalArgumentException.class, () -> MathPlus.combinaison(5, -10));
	}
	
	@Test
	public void testSlice ()
	{
		// TEST CORRECT
		int[] tab = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int[] tabTest = MathPlus.slice(tab, 2);
		
		for (int i = 0; i < tabTest.length; i++)
		{
			assertEquals(tab[i+2], tabTest[i]);
		}
		
		// TEST AVEC 0
		tab = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		tabTest = MathPlus.slice(tab, 0);
		
		for (int i = 0; i < tabTest.length; i++)
		{
			assertEquals(tab[i], tabTest[i]);
		}
		
		// TEST NEGATIF
		tab = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		tabTest = MathPlus.slice(tab, -1);
		assertEquals(tabTest.length, 0);
		
		// TEST AVEC NOMBRE TROP GRAND
		tab = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		tabTest = MathPlus.slice(tab, tab.length);
		
		assertEquals(tabTest.length, 0);
	}
	
	@Test
	public void testConcat ()
	{
		// TEST CORRECT
		int[] tab = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int[] tabTest = MathPlus.concat(tab, 2);
		
		for (int i = 0; i < tab.length; i++)
		{
			assertEquals(tab[i], tabTest[i]);
		}
		assertEquals(2, tabTest[tabTest.length - 1]);
	}
		
	@Test
	public void testCombinaisonPartition ()
	{
		// TEST CORRECT
		int[] inv;
		int k;
		ArrayList<int[]> expected;
		ArrayList<int[]> ret;
		
		inv = new int[] {1,3,2,0};
		k = 3;
		ret = new ArrayList<int[]>();
		expected = new ArrayList<int[]>();
		expected.add(new int[] {1, 3, 2});
		expected.add(new int[] {1, 3, 0});
		expected.add(new int[] {1, 2, 0});
		expected.add(new int[] {3, 2, 0});
		for (int i = 0; i < expected.size(); i++) 
		{
			MathPlus.combinaisonPartition(i, inv, new int[] {}, ret, k);
		}
		assertEquals(ret.size(), expected.size());
		for (int i = 0; i < ret.size(); i++)
		{
			assertEquals(ret.get(i).length, expected.get(i).length);
			for (int n = 0; n < ret.get(i).length; n++)
			{
				assertEquals(ret.get(i)[n], expected.get(i)[n]);
			}
		}
	}
	
	@Test
	public void testCombinaisons ()
	{
		int k = 3;
		int[] inv = new int[] {1,3,2,0};
		ArrayList<int[]> ret;
		ArrayList<int[]> expected = new ArrayList<int[]>();
		expected.add(new int[] {1, 3, 2});
		expected.add(new int[] {1, 3, 0});
		expected.add(new int[] {1, 2, 0});
		expected.add(new int[] {3, 2, 0});
		
		ret = MathPlus.combinaisons(inv, k);
		assertEquals(ret.size(), expected.size());
		for (int i = 0; i < ret.size(); i++)
		{
			assertEquals(ret.get(i).length, expected.get(i).length);
			for (int n = 0; n < ret.get(i).length; n++)
			{
				assertEquals(ret.get(i)[n], expected.get(i)[n]);
			}
		}
	}
}
