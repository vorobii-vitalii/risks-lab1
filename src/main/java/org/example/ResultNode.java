package org.example;

import javax.annotation.Nullable;

import guru.nidi.graphviz.model.MutableNode;

public interface ResultNode {
	boolean isEmpty();
	int cost();
	void createGraph(GraphCreator graphCreator, @Nullable MutableNode mutableNode);
}
