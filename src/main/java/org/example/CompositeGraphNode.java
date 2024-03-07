package org.example;

import java.util.ArrayList;
import java.util.List;

public class CompositeGraphNode implements GraphNode {
	private final GraphNode left;
	private final GraphNode right;
	private final double[][] matrix;

	public CompositeGraphNode(GraphNode left, GraphNode right, double[][] matrix) {
		this.left = left;
		this.right = right;
		this.matrix = matrix;
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
		for (int[] arr : indexes) {
			var leftExpectedScore = arr[0] + 1;
			var rightExpectedScore = arr[1] + 1;
			var leftResult = left.calculateResult(leftExpectedScore);
			if (leftResult.isEmpty()) {
				continue;
			}
			var rightResult = right.calculateResult(rightExpectedScore);
			resultList.add(new PairResultNode(leftResult, rightResult, leftExpectedScore, rightExpectedScore, expectedScore));
		}
		return new ListResult(resultList, expectedScore);
	}

}
