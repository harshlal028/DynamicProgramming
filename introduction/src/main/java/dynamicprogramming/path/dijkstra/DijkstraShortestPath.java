package dynamicprogramming.path.dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dynamicprogramming.path.ShortestPath;

public class DijkstraShortestPath {
	
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
		
		DijkstraShortestPath shortestPath = new DijkstraShortestPath();
		computation = 0;
		/*Shortest path using Dijkstra */
		System.out.println("======= Shortest Path (Dijkstra) ============");
		List<Integer> key = new ArrayList<>();
		int[][] shortestDijkstra = shortestPath.shortestDijkstraPath(inputList,1, key);
		shortestPath.printNumericArray(shortestDijkstra);
		int[] path = shortestDijkstra[1];
		shortestPath.printPath(path, key, 1);
		System.out.println("No. of computations = "+computation);
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
	 * Dijkstra shortest path from one node to all other nodes. Greedy Algorithm
	 * Does not Works on graphs with negative edge weights. 
	 * 
	 * Dijkstra's algorithm is used only when you have a single source and you want to know 
	 * the smallest path from one node to another, but fails [in graphs with negative edges]
	 * @param inputList
	 * @param startNode
	 * @return
	 * Time Complexity: O(|E| + |V|log|V|)
	 * Ref: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
	 */
	int[][] shortestDijkstraPath(List<List<Integer>> inputList, Integer startNode, List<Integer> key)
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
		
		/* IMP: Accessing reference of an already utilized object */
		for(Integer e: uniqueNodesList)
		{
			key.add(e);
		}
		return output;
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
