package dynamicprogramming.floydwarshal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Here we attempt to use floyd warshall algorithm to find the shortest path
 * among nodes. This algorithm finds the shortest path among all nodes.
 * 
 * @author harsh Negative edges are allowed, but no negative cycle. Time
 *         Complexity: O(V^3)
 */
public class FloydWarshallShortestPath {
	
	static int computation = 0;

	public static void main(String[] args) throws IOException {
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
		
		FloydWarshallShortestPath shortestPath = new FloydWarshallShortestPath();
		
		/*Shortest path using Floyd Warshall*/
		System.out.println("======= Shortest Path (Floyd Warshall) ============");
		int[][] shortestFloyd = shortestPath.shortestFloydPath(inputList);
		shortestPath.printNumericArray(shortestFloyd);
		System.out.println("No. of computations = "+computation);
		
		/* Path reconstruction using floyd warshall */
		System.out.println("======= Path reconstruction (Floyd Warshall) ============");
		List<Integer> key = new ArrayList<>();
		Integer[][] path = shortestPath.shortestFloydPathReconstruction(inputList, key);
		shortestPath.printShortestPath(2, 4, path, key);
	
	}
	
	
	Integer[][] shortestFloydPathReconstruction(List<List<Integer>> inputList, List<Integer> key) {
		
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
		int arr[][] = new int[size][size];
		Integer path[][] = new Integer[size][size];
		
		/** Step 1: Initializing all the non existent path with infinity(constant here) and 
		 * the same nodes as 0 (diagonal elements) */
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i != j)
				{
					arr[i][j] = constant;
					computation++;
				}
			}
		}
		
		/** Step 2: Adding the graph information in the array */
		for (List<Integer> e : inputList) {
			arr[uniqueNodesList.indexOf(e.get(0))][uniqueNodesList.indexOf(e.get(1))] = e.get(2);
			path[uniqueNodesList.indexOf(e.get(0))][uniqueNodesList.indexOf(e.get(1))] = uniqueNodesList.indexOf(e.get(1)); 
			computation++;
		}
		
		/** Step 3: Using Floyd Warshall algorithm to calculate the shortest distance */
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (arr[i][j] > arr[i][k] + arr[k][j]) // Going through path
					{
						arr[i][j] = arr[i][k] + arr[k][j];
						path[i][j] = path[i][k];
						computation++;
					}
				}
			}
		}
		
		/* IMP: Accessing reference of an already utilized object */
		for(Integer e: uniqueNodesList)
		{
			key.add(e);
		}
		return path;
	}
	
	/**
	 * This function provides algorithm for printing the path given the generated path matrix
	 * @param u
	 * @param v
	 * @param path
	 */
	void printShortestPath(int u1, int v1, Integer[][] path, List<Integer> key)
	{
		int u = key.indexOf(u1);
		int v = key.indexOf(v1);
		String sep = "-->";
		StringBuffer pathStr = new StringBuffer();
		if(path[u][v] == null)
		{
			System.out.println("-");
		}
		pathStr.append(key.get(u));
		while(u != v)
		{
			u = path[u][v];
			pathStr.append(sep);
			pathStr.append(key.get(u));
		}
		System.out.println("Path = "+pathStr.toString());
	}
	
	/**
	 * Floyd warshall algorithm for finding shortest path to all the nodes in a graph 
	 * starting from any node
	 * @param inputList
	 * @return
	 * Time Complexity: O(n^3)
	 */
	int[][] shortestFloydPath(List<List<Integer>> inputList) {
		
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
		int arr[][] = new int[size][size];
		
		/** Step 1: Initializing all the non existent path with infinity(constant here) and 
		 * the same nodes as 0 (diagonal elements) */
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i != j)
				{
					arr[i][j] = constant;
					computation++;
				}
			}
		}
		
		/** Step 2: Adding the graph information in the array */
		for (List<Integer> e : inputList) {
			arr[uniqueNodesList.indexOf(e.get(0))][uniqueNodesList.indexOf(e.get(1))] = e.get(2);
			computation++;
		}
		
		/** Step 3: Using Floyd Warshall algorithm to calculate the shortest distance */
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (arr[i][j] > arr[i][k] + arr[k][j]) // Going through path
					{
						arr[i][j] = arr[i][k] + arr[k][j];
					}
					computation++;
				}
			}
		}
		return arr;
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
