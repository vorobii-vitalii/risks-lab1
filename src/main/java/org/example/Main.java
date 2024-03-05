package org.example;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

public class Main {
	public static void main(String[] args) throws IOException {
		int[][] A = {
				{1, 1, 2, 2, 3},
				{1, 2, 3, 3, 3},
				{1, 2, 3, 3, 4},
				{2, 3, 4, 4, 4},
				{2, 3, 4, 5, 5}
		};
		int[][] B = {
				{1, 1, 2, 2, 3},
				{1, 2, 3, 3, 4},
				{2, 2, 3, 3, 4},
				{2, 3, 4, 4, 5},
				{3, 3, 4, 5, 5}
		};
		int[][] C = {
				{1, 1, 1, 2, 3},
				{1, 1, 2, 3, 3},
				{2, 2, 2, 3, 4},
				{2, 2, 3, 4, 4},
				{3, 3, 4, 4, 5}
		};
		int[][] COSTS = {
				{28, 32, 40, 75, 80},
				{25, 29, 38, 49, 58},
				{32, 43, 61, 78, 99},
				{6, 10, 13, 20, 60}
		};
		final CompositeGraphNode root = new CompositeGraphNode(
				new CompositeGraphNode(
						new CompositeGraphNode(
								new LeafGraphNode(COSTS[0], "Д"),
								new LeafGraphNode(COSTS[1], "Ц"),
								A
						),
						new LeafGraphNode(COSTS[2], "К"),
						B
				),
				new LeafGraphNode(COSTS[3], "C"),
				C
		);
		var resultNode = root.calculateResult(4);
		MutableGraph g = mutGraph("example1");
		// add(mutNode("a").add(Color.RED).addLink(mutNode("b")))
		System.out.println(resultNode);
		AtomicInteger count = new AtomicInteger();
		resultNode.createGraph(new GraphCreator() {
			@Override
			public void addNode(MutableNode mutableNode) {
				count.incrementAndGet();
				g.add(mutableNode);
			}
		}, null);
		System.out.println("Nodes = " + count.get());
		Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("example/ex1m.png"));
	}
}