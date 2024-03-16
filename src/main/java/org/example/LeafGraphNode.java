package org.example;

public class LeafGraphNode implements GraphNode {
	private final double[] costs;
	private final String paramName;

	public LeafGraphNode(double[] costs, String paramName) {
		this.costs = costs;
		this.paramName = paramName;
	}

	@Override
	public ResultNode calculateResult(int expectedScore) {
		return new LeafResultNode(costs[expectedScore - 1], paramName, expectedScore);
	}

	@Override
	public String paramName() {
		return paramName;
	}
}
