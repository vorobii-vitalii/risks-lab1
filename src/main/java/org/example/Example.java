package org.example;

import static guru.nidi.graphviz.model.Factory.mutGraph;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

public class Example {
	public static void main(String[] args) throws IOException {
		double[][] A = {
				{1, 1, 2, 2, 3},
				{1, 2, 3, 3, 3},
				{1, 2, 3, 3, 4},
				{2, 3, 4, 4, 4},
				{2, 3, 4, 5, 5}
		};
		double[][] B = {
				{1, 1, 2, 2, 3},
				{1, 2, 3, 3, 4},
				{2, 2, 3, 3, 4},
				{2, 3, 4, 4, 5},
				{3, 3, 4, 5, 5}
		};
		double[][] C = {
				{1, 1, 1, 2, 3},
				{1, 1, 2, 3, 3},
				{2, 2, 2, 3, 4},
				{2, 2, 3, 4, 4},
				{3, 3, 4, 4, 5}
		};
		double[][] COSTS = {
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
		MutableGraph g = mutGraph("result");
		System.out.println(resultNode);
		AtomicInteger count = new AtomicInteger();
		resultNode.createGraph(mutableNode -> {
			count.incrementAndGet();
			g.add(mutableNode);
		}, null);
		Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("out/result.png"));
	}
}
