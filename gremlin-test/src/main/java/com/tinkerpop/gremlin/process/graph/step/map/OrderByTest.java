package com.tinkerpop.gremlin.process.graph.step.map;

import com.tinkerpop.gremlin.AbstractGremlinTest;
import com.tinkerpop.gremlin.LoadGraphWith;
import com.tinkerpop.gremlin.process.T;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.util.StreamFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.tinkerpop.gremlin.LoadGraphWith.GraphData.MODERN;
import static org.junit.Assert.assertEquals;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class OrderByTest extends AbstractGremlinTest {

    public abstract Traversal<Vertex, String> get_g_V_orderByXname_incrX_name();

    public abstract Traversal<Vertex, String> get_g_V_orderByXnameX_name();

    public abstract Traversal<Vertex, Double> get_g_V_outE_orderByXweight_decrX_weight();

    // TODO: example using meta-properties

    @Test
    @LoadGraphWith(MODERN)
    public void g_V_orderByXname_incrX_name() {
        Arrays.asList(get_g_V_orderByXname_incrX_name(), get_g_V_orderByXnameX_name()).forEach(traversal -> {
            printTraversalForm(traversal);
            final List<String> names = StreamFactory.stream(traversal).collect(Collectors.toList());
            assertEquals(names.size(), 6);
            assertEquals("josh", names.get(0));
            assertEquals("lop", names.get(1));
            assertEquals("marko", names.get(2));
            assertEquals("peter", names.get(3));
            assertEquals("ripple", names.get(4));
            assertEquals("vadas", names.get(5));
        });
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_V_outE_orderXweight_decrX_weight() {
        final Traversal<Vertex, Double> traversal = get_g_V_outE_orderByXweight_decrX_weight();
        printTraversalForm(traversal);
        final List<Double> weights = StreamFactory.stream(traversal).collect(Collectors.toList());
        assertEquals(weights.size(), 6);
        assertEquals(Double.valueOf(1.0d), weights.get(0));
        assertEquals(Double.valueOf(1.0d), weights.get(1));
        assertEquals(Double.valueOf(0.5d), weights.get(2));
        assertEquals(Double.valueOf(0.4d), weights.get(3));
        assertEquals(Double.valueOf(0.4d), weights.get(4));
        assertEquals(Double.valueOf(0.2d), weights.get(5));

    }

    public static class JavaOrderByTest extends OrderByTest {

        @Override
        public Traversal<Vertex, String> get_g_V_orderByXname_incrX_name() {
            return g.V().orderBy("name", T.incr).value("name");
        }

        @Override
        public Traversal<Vertex, String> get_g_V_orderByXnameX_name() {
            return g.V().orderBy("name").value("name");
        }

        @Override
        public Traversal<Vertex, Double> get_g_V_outE_orderByXweight_decrX_weight() {
            return g.V().outE().orderBy("weight", T.decr).value("weight");
        }
    }
}