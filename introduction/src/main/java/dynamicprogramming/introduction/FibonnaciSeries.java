package dynamicprogramming.introduction;

public class FibonnaciSeries {
	
	int idxCheck = 31;
	static int computation = 0;
	
	public static void main(String[] args) throws InterruptedException {

		long oldtime,newtime;
		FibonnaciSeries fiboSeries = new FibonnaciSeries();

		System.out.println("\n======= A recursive solution ========\n");
		oldtime = System.currentTimeMillis();
		System.out.println("Fibonacci number 30th is:" + fiboSeries.fiboRecursive(31));
		newtime = System.currentTimeMillis();
		System.out.println("Time spent=" + (newtime - oldtime)+ " Computatipons = "+computation);
		
		
		computation = 0;
		System.out.println("\n======= Dynamic Programming - Memoitization approach ========\n");
		oldtime = System.currentTimeMillis();
		System.out.println("Fibonacci number 30th is:" + fiboSeries.fiboMemoitization(31));
		newtime = System.currentTimeMillis();
		System.out.println("Time spent=" + (newtime - oldtime)+ " Computatipons = "+computation);
		
		computation = 0;
		System.out.println("\n======= Dynamic Programming - Tabulation approach ========\n");
		oldtime = System.currentTimeMillis();
		System.out.println("Fibonacci number 30th is:" + fiboSeries.fiboTabulation(31));
		newtime = System.currentTimeMillis();
		System.out.println("Time spent=" + (newtime - oldtime)+ " Computatipons = "+computation);
		
	}

	/**
	 * A generic recursive approach for calculating fibonacci numbers
	 * @param num
	 * @return
	 */
	int fiboRecursive(int num) {
		if (num == 0 || num == 1) {
			return (num);
		} else {
			computation++;
			return (fiboRecursive(num - 2) + fiboRecursive(num - 1));
		}
	}

	/**
	 * Memoitization is a top down approach of dynamic programming.
	 * @param num
	 * @return
	 * 
	 * PROS: Sometimes avoids computing solutions to subproblems that are not needed (longest common subsequence)
	 * More intuitive - Matrix chain multiplication
	 * CONS: Makes more function calls
	 */
	
	int arr[] = new int[idxCheck+1];
	int fiboMemoitization(int num)
	{
		if(arr[num] == 0)
		{
			if(num == 1 || num == 0)
			{
				arr[num] = num;
			}
			else
			{
				computation++;
				arr[num] = fiboMemoitization(num-2) + fiboMemoitization(num-1);
			}
		}
		return(arr[num]);
	}
	
	/**
	 * Tabulation is a bottom up approach to dynamic programming
	 * @param num
	 * @return
	 * PROS: Avoids multiple lookups, thus saves function call overhead
	 * CONS: Solves all the subproblems
	 */
	int arr1[] = new int[idxCheck+1];
	int fiboTabulation(int num)
	{
		arr1[0] = 0;
		arr1[1] = 1;
		for(int i=2; i<=num; i++)
		{
			computation++;
			arr1[i] = arr1[i-1] + arr1[i-2];
		}
		return arr1[num];
	}
	
}
