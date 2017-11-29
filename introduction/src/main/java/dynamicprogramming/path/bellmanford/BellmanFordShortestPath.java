package dynamicprogramming.path.bellmanford;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dynamicprogramming.path.ShortestPath;

public class BellmanFordShortestPath {
	
	static int computation = 0;
	
	public static void main(String[] args) throws Exception {
		
		List<List<Integer>> inputList = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new FileReader("input/inputGraph.txt"));
		String line = br.readLine();
		/* Reading the input graph */
		while (line != null) {
			List<String> stringList = Arrays.asList(line.split(" "));
			List<Integer> tempLsit = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
			inputList.add(tempLsit);
			line = br.readLine();
		}
		br.close();
		
		BellmanFordShortestPath shortestPath = new BellmanFordShortestPath();
		
		/*Shortest path using Bellman Ford */
		System.out.println("======= Shortest Path (Bellman Ford) ============");
		List<Integer> key = new ArrayList<>();
		int[][] shortestBellman = shortestPath.shortestBellmanPath(inputList,1,key);
		shortestPath.printNumericArray(shortestBellman);
		int[] path = shortestBellman[1];
		shortestPath.printPath(path, key, 1);
		System.out.println("No. of computations = "+computation);
	}
	
	/**
	 * Bellman ford = shortest path from one node to all other nodes.
	 * Works on graphs with negative edge weights. Fails on negative cycle
	 * 
	 * Bellman-Ford is used like Dijkstra's, when there is only one source. This can handle negative weights.
	 * @param inputList
	 * @param startNode
	 * @return
	 * N-1 iterations for n vertices
	 * Time Complexity: O(|V|.|E|)
	 * Ref: https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
	 */
	int[][] shortestBellmanPath(List<List<Integer>> inputList, Integer startNode, List<Integer> key)
	{
		int constant = 1000000000;
		Set<Integer> uniqueNodes = new HashSet<>();
		
		/* Getting a list of unique nodes */
		for (List<Integer> i : inputList) {
			uniqueNodes.add(i.get(0));
			uniqueNodes.add(i.get(1));
			computation++;
		}
		
		/* Converting set to list for ease of access and indexing */
		List<Integer> uniqueNodesList = new ArrayList<>(uniqueNodes);
		int size = uniqueNodesList.size();
		
		int[] distance = new int[size];
		int[] predecessor = new int[size];

		
		/** Step 1: Initializing all the non existent path with infinity(constant here) 
		 * and predecessors to null */
		for (int i = 0; i < size; i++) {
				distance[i] = constant;
				predecessor[i] = -1;
				computation++;
		}
		
		/** Step 1: Initializing start node to 0 */
		distance[uniqueNodesList.indexOf(startNode)] = 0;
		
		/** Step 2: Iterate through edges to find min distance repeatedly */
		for(int i=0; i<size-1; i++)
		{
			for(List<Integer> edges : inputList)
			{
				int u = uniqueNodesList.indexOf(edges.get(0));
				int v = uniqueNodesList.indexOf(edges.get(1));
				int w = edges.get(2);
				if((distance[u]+w) < distance[v])
				{
					distance[v] = (distance[u]+w);
					predecessor[v] = u;
				}
				computation++;
			}
		}
		
		/* Failsafe checking for negative cycles */
		for(List<Integer> edges : inputList)
		{
			int u = uniqueNodesList.indexOf(edges.get(0));
			int v = uniqueNodesList.indexOf(edges.get(1));
			int w = uniqueNodesList.indexOf(edges.get(2));
			if(distance[u]+w < distance[v])
			{
				System.out.println("Graph contains a negative cycle");
			}
		}
		
		/* A way to output the results */
		int[][] output = new int[size][size];
		output[0] = distance;
		output[1] = predecessor;
		
		/* IMP: Accessing reference of an already utilized object */
		for(Integer e: uniqueNodesList)
		{
			key.add(e);
		}
		return output;
	}
	
	/**
	 * A function to print the shortest dijkstra path
	 * @param path
	 * @param key
	 */
	void printPath(int[] path, List<Integer> key, int srcNode)
	{
		String sep = "-->";
		StringBuffer strPath = new StringBuffer();
		strPath.append(srcNode);
		for(int i=1; i<path.length; i++)
		{
			strPath.append(sep);
			strPath.append(path[i]);
		}
		System.out.println("Path = "+strPath.toString());
	}
	
	/**
	 * A function to print all the elements of a primitive int array 
	 * @param arr
	 */
	void printNumericArray(int[][] arr) {
		Integer[][] arr1 = new Integer[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr1[i][j] = arr[i][j];
			}
		}
		printArray(arr1);
	}
	
	/**
	 * A generic function to print all the elements of a two dimensional array
	 * @param arr
	 */
	<T> void printArray(T[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

}
