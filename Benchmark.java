/*
 * Copyright (c) 2020 Pantelis Andrianakis
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package unbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Benchmark
{
	public static void main(String[] args)
	{
		// Configs.
		boolean tryWithExceptions = true;
		int testIterations = 1000;
		int testSize = 10000;
		
		// Create test list before actual tests start.
		System.out.print("Generating test list...");
		final Random random = new Random();
		List<Byte> testList = new ArrayList<>(testSize);
		for (int i = 0; i < testSize; i++)
		{
			testList.add((byte) random.nextInt(Byte.MAX_VALUE));
		}
		System.out.println(" Done.");
		
		// Used for storing time of tests.
		long timeBeforeIterations = System.currentTimeMillis();
		long savedTime;
		long averageTime1 = -1;
		long averageTime2 = -1;
		
		// Used to delay and force JVM to enter the For loop.
		@SuppressWarnings("unused")
		double sum;
		
		System.out.println("Starting tests...");
		for (int i = 0; i < testIterations; i++)
		{
			savedTime = System.currentTimeMillis();
			sum = 0;
			for (int j = 0; j < testList.size(); j++)
			{
				sum += testList.get(j);
			}
			if (averageTime1 == -1)
			{
				averageTime1 = System.currentTimeMillis() - savedTime;
			}
			else
			{
				averageTime1 = (averageTime1 + (System.currentTimeMillis() - savedTime)) / 2;
			}
			
			savedTime = System.currentTimeMillis();
			sum = 0;
			for (int j = 0; j < testList.size(); j++)
			{
				Byte num = null;
				try
				{
					// 50% errors
					if (tryWithExceptions)
					{
						if ((j % 2) == 0) // even - produce exception
						{
							num = testList.get(testList.size() + 1);
						}
						else // odd
						{
							num = testList.get(j);
						}
					}
					else
					{
						num = testList.get(j);
					}
				}
				catch (Exception e)
				{
				}
				
				if (num == null)
				{
					continue;
				}
				
				sum += num;
			}
			if (averageTime2 == -1)
			{
				averageTime2 = System.currentTimeMillis() - savedTime;
			}
			else
			{
				averageTime2 = (averageTime2 + (System.currentTimeMillis() - savedTime)) / 2;
			}
			
			System.out.println("Progress " + ((100f * (i + 1)) / testIterations) + "%");
		}
		
		System.out.println("Average time for " + testIterations + " tests.");
		System.out.println("Test list of " + testSize + " random elements.");
		System.out.println("Normal Get: Execution time was " + averageTime1 + " milliseconds.");
		System.out.println("Get with Try: Execution time was " + averageTime2 + " milliseconds.");
		System.out.println("Total time for testing was " + ((System.currentTimeMillis() - timeBeforeIterations) / 1000) + " seconds.");
	}
}
