package org.example;

import static guru.nidi.graphviz.model.Factory.mutNode;

import java.util.UUID;

import javax.annotation.Nullable;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.MutableNode;

public class LeafResultNode implements ResultNode {
	private final double cost;
	private final String paramName;
	private final int expectedScore;

	public LeafResultNode(double cost, String paramName, int expectedScore) {
		this.cost = cost;
		this.paramName = paramName;
		this.expectedScore = expectedScore;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public double cost() {
		return cost;
	}

	@Override
	public void createGraph(GraphCreator graphCreator, @Nullable MutableNode parentNode) {
		var newNode = mutNode(UUID.randomUUID().toString());
		newNode.add(Label.of(paramName + " cost = " + cost + " score = " + expectedScore));
		if (parentNode != null) {
			newNode.addLink(parentNode);
		}
		graphCreator.addNode(newNode);
	}

	@Override
	public String toString() {
		return "Leaf (" + cost + ")";
	}
}
