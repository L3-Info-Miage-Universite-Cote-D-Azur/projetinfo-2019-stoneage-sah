package statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ArrayListUtilityTest 
{
	@Test
	public void testGetMaxLength ()
	{
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] arr = new ArrayList[10];
		for (int i = 0; i < 10; i++)
		{
			arr[i] = new ArrayList<Integer>();
		}
		int length = 0;
		for (;length < 100; length++)
		{
			arr[9].add(3);
		}
		assertEquals(ArrayListUtility.getMaxLength(arr), length);
	}
	
	@Test
	public void testGetLastValueOf ()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int length = 0;
		for (;length < 100; length++)
		{
			arr.add(3);
		}
		arr.add(54684684);
		assertEquals(ArrayListUtility.getLastValueOf(arr), 54684684);
	}
	
	@Test
	public void testGetLastValuesOf ()
	{
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] arr = new ArrayList[10];
		for (int i = 0; i < 10; i++)
		{
			arr[i] = new ArrayList<Integer>();
			arr[i].add(0);
		}
		int length = 0;
		for (;length < 100; length++)
		{
			arr[9].add(3);
		}
		int[] expected = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 3};
		int[] res = ArrayListUtility.getLastValuesOf(arr);
		
		assertEquals(res.length, expected.length);
		for (int i = 0; i < res.length; i++)
		{
			assertEquals(res[i], expected[i]);
		}
	}
	
	@Test
	public void testAddFromTo ()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int length = 0;
		for (;length < 100; length++)
		{
			arr.add(3);
		}
		
		ArrayListUtility.addFromTo(arr, 0, 2, 5);
		for (int i = 0; i < arr.size(); i++)
		{
			if (i >= 0 && i < 2)
			{
				assertEquals(arr.get(i), 5 + 3);
			}
			else
			{
				assertEquals(arr.get(i), 3);
			}
		}
	}
	
	@Test
	public void testFillUpTo ()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int length = 0;
		for (;length < 100; length++)
		{
			arr.add(3);
		}
		int len = arr.size();
		ArrayListUtility.fillUpTo(arr, arr.size() + 2, 5);
		for (int i = 0; i < arr.size(); i++)
		{
			if (i >= len)
			{
				assertEquals(arr.get(i), 5);
			}
			else
			{
				assertEquals(arr.get(i), 3);
			}
		}
	}
	
	@Test
	public void testFillArraysUpTo ()
	{
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] arr = new ArrayList[10];
		for (int i = 0; i < 10; i++)
		{
			arr[i] = new ArrayList<Integer>();
			arr[i].add(0);
		}
		for (int i = 0; i < arr.length; i++)
		{
			assertEquals(arr[i].get(0), 0);
		}
		int[] input = new int[] {0,0,0,0,0,0,0,0,0,0};
		ArrayListUtility.fillArraysUpTo(arr, 1, input);
		for (int i = 0; i < arr.length; i++)
		{
			assertEquals(arr[i].get(0), input[i]);
		}
	}
}
