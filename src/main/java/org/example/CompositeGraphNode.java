package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CompositeGraphNode implements GraphNode {
	private final GraphNode left;
	private final GraphNode right;
	private final double[][] matrix;
	private String paramName;

	public CompositeGraphNode(GraphNode left, GraphNode right, double[][] matrix) {
		this.left = left;
		this.right = right;
		this.matrix = matrix;
	}

	public CompositeGraphNode(GraphNode left, GraphNode right, double[][] matrix, String paramName) {
		this.left = left;
		this.right = right;
		this.matrix = matrix;
		this.paramName = paramName;
	}

	@Override
	public ResultNode calculateResult(int expectedScore) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		List<int[]> indexes = new ArrayList<>();
		for (var i = 0; i < rows; i++) {
			for (var j = 0; j < cols; j++) {
				var score = matrix[i][j];
				if (score == expectedScore) {
					boolean foundLower = false;
					for (var arr : indexes) {
						if (arr[0] <= i && arr[1] <= j) {
							foundLower = true;
							break;
						}
					}
					if (!foundLower) {
						var iterator = indexes.iterator();
						while (iterator.hasNext()) {
							var arr = iterator.next();
							if (arr[0] >= i && arr[1] >= j) {
								iterator.remove();
							}
						}
						indexes.add(new int[] {i, j});
					}
				}
			}
		}
		var resultList = new ArrayList<ResultNode>();
		var pairs = new ArrayList<Pair<int[], PairResultNode>>();
		for (int[] arr : indexes) {
			var leftExpectedScore = arr[1] + 1;
			var rightExpectedScore = arr[0] + 1;
			var leftResult = left.calculateResult(leftExpectedScore);
			if (leftResult.isEmpty()) {
				continue;
			}
			var rightResult = right.calculateResult(rightExpectedScore);
			if (rightResult.isEmpty()) {
				continue;
			}
			PairResultNode pairResultNode = new PairResultNode(leftResult, rightResult, leftExpectedScore, rightExpectedScore, expectedScore);
			pairs.add(new Pair<>(arr, pairResultNode));
			resultList.add(pairResultNode);
		}
		if (!pairs.isEmpty()) {
			System.out.println(paramName() + " expected score = " + expectedScore);
			for (var pair : pairs) {
				var arr = pair.a();
				System.out.println((arr[0] + 1) + " " + (arr[1] + 1) + " -> " + pair.b().cost());
			}
		}
		return new ListResult(resultList, expectedScore);
	}

	private record Pair<A, B>(A a, B b) {

	}

	@Override
	public String paramName() {
		return paramName != null ? paramName : ("(" + left.paramName() + ") / (" + right.paramName() + ")");
	}

}
