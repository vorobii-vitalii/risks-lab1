package org.example;

import static guru.nidi.graphviz.model.Factory.mutNode;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.MutableNode;

public class ListResult implements ResultNode {
	public static final double DEFAULT_COST = Integer.MAX_VALUE;
	private final List<ResultNode> resultNodes;
	private final double cost;
	private final int score;

	public ListResult(List<ResultNode> resultNodes, int score) {
		this.score = score;
		this.resultNodes = resultNodes;
		this.cost = resultNodes.stream()
				.min(Comparator.comparingDouble(ResultNode::cost))
				.map(ResultNode::cost)
				.orElse(DEFAULT_COST);
	}

	@Override
	public boolean isEmpty() {
		return resultNodes.isEmpty();
	}

	@Override
	public double cost() {
		return cost;
	}

	@Override
	public void createGraph(GraphCreator graphCreator, @Nullable MutableNode parentNode) {
		if (resultNodes.size() == 1) {
			resultNodes.get(0).createGraph(graphCreator, parentNode);
			return;
		}
		var newNode = mutNode(UUID.randomUUID().toString());
		newNode.add(Label.of("Cost = " + cost + " score = " + score));
		if (parentNode != null) {
			newNode.addLink(parentNode);
		}
		graphCreator.addNode(newNode);
		for (var resultNode : resultNodes) {
			resultNode.createGraph(graphCreator, newNode);
		}
	}

	@Override
	public String toString() {
		return "List (" + cost + ") [" + resultNodes.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
	}
}
