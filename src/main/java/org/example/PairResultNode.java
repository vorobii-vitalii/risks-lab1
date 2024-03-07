package org.example;

import static guru.nidi.graphviz.model.Factory.mutNode;

import java.util.UUID;

import javax.annotation.Nullable;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.MutableNode;

public class PairResultNode implements ResultNode {
	private final ResultNode left;
	private final ResultNode right;
	private final double cost;
	private final double score;
	private final int leftScore;
	private final int rightScore;

	public PairResultNode(ResultNode left, ResultNode right, int leftScore, int rightScore, int score) {
		this.left = left;
		this.right = right;
		this.cost = left.cost() + right.cost();
		this.score = score;
		this.leftScore = leftScore;
		this.rightScore = rightScore;
	}

	@Override
	public boolean isEmpty() {
		return left.isEmpty() || right.isEmpty() ;
	}

	@Override
	public double cost() {
		return cost;
	}

	@Override
	public void createGraph(GraphCreator graphCreator, @Nullable MutableNode parentNode) {
		var newNode = mutNode(UUID.randomUUID().toString());
		newNode.add(Label.of("Cost = " + cost + " score = " + score + "{" + leftScore + ";" + rightScore + "}"));
		if (parentNode != null) {
			newNode.addLink(parentNode);
		}
		graphCreator.addNode(newNode);
		left.createGraph(graphCreator, newNode);
		right.createGraph(graphCreator, newNode);
	}

	@Override
	public String toString() {
		return "Pair (" + cost + ") { " + left + " | " + right + " }";
	}
}
