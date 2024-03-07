package org.example;

import static guru.nidi.graphviz.model.Factory.mutGraph;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

public class RiskLab1_Variant4 {
	public static void main(String[] args) throws IOException {
		double[][] A = {
				{2, 3, 4, 4, 5},
				{1, 2, 3, 4, 4},
				{1, 2, 3, 4, 4},
				{1, 2, 2, 3, 3},
				{1, 1, 1, 2, 3}
		};
		double[][] B = {
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 4},
				{1, 2, 3, 4, 4},
				{1, 2, 2, 3, 3},
				{1, 1, 1, 2, 2}
		};
		double[][] C = {
				{3, 3, 4, 5, 5},
				{2, 3, 4, 4, 5},
				{2, 2, 3, 3, 4},
				{1, 2, 3, 3, 4},
				{1, 1, 2, 2, 3}
		};
		double[][] D = {
				{3, 3, 4, 5, 5},
				{2, 3, 4, 4, 5},
				{2, 2, 3, 4, 4},
				{1, 2, 3, 3, 4},
				{1, 2, 2, 3, 3}
		};
		double[][] E = {
				{3, 3, 4, 5, 5},
				{2, 3, 4, 4, 5},
				{2, 3, 3, 4, 5},
				{1, 2, 3, 4, 4},
				{1, 2, 3, 3, 4}
		};
		double[][] COSTS = {
				{92.6, 201.0, 274.2, 352.9, 425},
				{83, 149.7, 182, 249.3, 302.4},
				{56.2, 108.7, 158.6, 193.1, 241.6},
				{27.7, 58.4, 82.3, 99.4, 126.7}
		};

		var root = new CompositeGraphNode(
				new CompositeGraphNode(
						new CompositeGraphNode(
								new LeafGraphNode(COSTS[0], "К1"),
								new LeafGraphNode(COSTS[1], "К2"),
								A
						),
						new CompositeGraphNode(
								new LeafGraphNode(COSTS[1], "К2"),
								new LeafGraphNode(COSTS[2], "К3"),
								B
						),
						C
				),
				new CompositeGraphNode(
						new LeafGraphNode(COSTS[2], "К3"),
						new LeafGraphNode(COSTS[3], "К4"),
						D
				),
				E
		);
		var resultNode = root.calculateResult(5);
		MutableGraph g = mutGraph("result4");
		System.out.println(resultNode);
		AtomicInteger count = new AtomicInteger();
		resultNode.createGraph(mutableNode -> {
			count.incrementAndGet();
			g.add(mutableNode);
		}, null);
		Graphviz.fromGraph(g).height(2000).width(2000).render(Format.PNG).toFile(new File("out/result4.png"));
	}
}
