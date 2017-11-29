package dynamicprogramming.path;

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
 *         Complexity: O(n^3)
 */
public class ShortestPath {
	
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
		
		ShortestPath shortestPath = new ShortestPath();
		
		/*Shortest path using Floyd Warshall*/
		System.out.println("======= Shortest Path (Floyd Warshall) ============");
		int[][] shortestFloyd = shortestPath.shortestFloydPath(inputList);
		shortestPath.printNumericArray(shortestFloyd);
		System.out.println("No. of computations = "+computation);
		
		computation = 0;
		/*Shortest path using Bellman Ford */
		System.out.println("======= Shortest Path (Bellman Ford) ============");
		int[][] shortestBellman = shortestPath.shortestBellmanPath(inputList,1);
		shortestPath.printNumericArray(shortestBellman);
		System.out.println("No. of computations = "+computation);
		
		computation = 0;
		/*Shortest path using Dijkstra */
		System.out.println("======= Shortest Path (Dijkstra) ============");
		int[][] shortestDijkstra = shortestPath.shortestDijkstraPath(inputList,1);
		shortestPath.printNumericArray(shortestDijkstra);
		System.out.println("No. of computations = "+computation);
	}
	
	/**
	 * Dijkstra shortest path from one node to all other nodes. Greedy Algorithm
	 * Does not Works on graphs with negative edge weights. 
	 * @param inputList
	 * @param startNode
	 * @return
	 * Time Complexity: O(|E| + |V|log|V|)
	 * Ref: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
	 */
	int[][] shortestDijkstraPath(List<List<Integer>> inputList, Integer startNode)
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
		List<Integer> uniqueNodesList = new ArrayList<>(uniqueNodes); // using this as queue
		int size = uniqueNodesList.size();
		int[] distance = new int[size];
		int[] predecessor = new int[size];

		
		/** Create a Queue */
		List<Integer> queueList = new ArrayList<>(uniqueNodesList);
		
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
		int currentNode = uniqueNodesList.indexOf(startNode);
		queueList.remove(uniqueNodesList.get(currentNode));
		while(!queueList.isEmpty())
		{
			for(List<Integer> edges : inputList)
			{
				int u = uniqueNodesList.indexOf(edges.get(0));
				int v = uniqueNodesList.indexOf(edges.get(1));
				int w = edges.get(2);
				if(u == currentNode)
				{
					if(distance[u] + w < distance[v])
					{
						distance[v] = (distance[u] + w);
						predecessor[v] = u;
					}
					computation++;
				}
			}
			
			int min = queueList.get(0);
			for(int i : queueList)
			{
				if(distance[uniqueNodesList.indexOf(i)] < min)
				{
					min = i;
				}
				computation++;
			}
			currentNode = uniqueNodesList.indexOf(min);
			queueList.remove(uniqueNodesList.get(currentNode));
		}
		
		/* A way to output the results */
		int[][] output = new int[size][size];
		output[0] = distance;
		output[1] = predecessor;
		return output;
	}
	
	/**
	 * Bellman ford = shortest path from one node to all other nodes.
	 * Works on graphs with negative edge weights. Fails on negative cycle
	 * @param inputList
	 * @param startNode
	 * @return
	 * N-1 iterations for n vertices
	 * Time Complexity: O(|V|.|E|)
	 * Ref: https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
	 */
	int[][] shortestBellmanPath(List<List<Integer>> inputList, Integer startNode)
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
		return output;
	}
	
	/**
	 * Floyd warshall algorithm for finding shortest path to all the nodes in a graph 
	 * starting from any node
	 * @param inputList
	 * @return
	 * Time Complexity: O(n^3)
	 * Ref : https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
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
