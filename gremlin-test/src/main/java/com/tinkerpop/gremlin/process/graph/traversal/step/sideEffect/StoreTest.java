package com.tinkerpop.gremlin.process.graph.traversal.step.sideEffect;

import com.tinkerpop.gremlin.LoadGraphWith;
import com.tinkerpop.gremlin.process.AbstractGremlinProcessTest;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.util.BulkSet;
import com.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.tinkerpop.gremlin.LoadGraphWith.GraphData.MODERN;
import static com.tinkerpop.gremlin.process.graph.traversal.__.*;
import static org.junit.Assert.*;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class StoreTest extends AbstractGremlinProcessTest {
    public abstract Traversal<Vertex, Collection> get_g_V_storeXaX_byXnameX_out_capXaX();

    public abstract Traversal<Vertex, Collection> get_g_VX1X_storeXaX_byXnameX_out_storeXaX_byXnameX_name_capXaX(final Object v1Id);

    public abstract Traversal<Vertex, Set<String>> get_g_V_withSideEffectXa_setX_both_name_storeXaX();

    public abstract Traversal<Vertex, Collection> get_g_V_storeXaX_byXoutEXcreatedX_countX_out_out_storeXaX_byXinEXcreatedX_weight_sumX();

    @Test
    @LoadGraphWith(MODERN)
    public void g_V_storeXa_nameX_out_capXaX() {
        final Traversal<Vertex, Collection> traversal = get_g_V_storeXaX_byXnameX_out_capXaX();
        printTraversalForm(traversal);
        Collection names = traversal.next();
        assertEquals(6, names.size());
        assertTrue(names.contains("marko"));
        assertTrue(names.contains("josh"));
        assertTrue(names.contains("peter"));
        assertTrue(names.contains("lop"));
        assertTrue(names.contains("ripple"));
        assertTrue(names.contains("vadas"));
        assertFalse(traversal.hasNext());
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_VX1X_storeXaX_byXnameX_out_storeXaX_byXnameX_name_capXaX() {
        final Traversal<Vertex, Collection> traversal = get_g_VX1X_storeXaX_byXnameX_out_storeXaX_byXnameX_name_capXaX(convertToVertexId("marko"));
        printTraversalForm(traversal);
        Collection names = traversal.next();
        assertEquals(4, names.size());
        assertTrue(names.contains("marko"));
        assertTrue(names.contains("josh"));
        assertTrue(names.contains("vadas"));
        assertTrue(names.contains("lop"));
        assertFalse(traversal.hasNext());
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_V_withXa_setX_both_name_storeXaX() {
        final Traversal<Vertex, Set<String>> traversal = get_g_V_withSideEffectXa_setX_both_name_storeXaX();
        printTraversalForm(traversal);
        final Set<String> names = traversal.next();
        assertFalse(traversal.hasNext());
        assertFalse(names instanceof BulkSet);
        assertEquals(6, names.size());
        assertTrue(names.contains("marko"));
        assertTrue(names.contains("vadas"));
        assertTrue(names.contains("josh"));
        assertTrue(names.contains("lop"));
        assertTrue(names.contains("ripple"));
        assertTrue(names.contains("peter"));
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_V_storeXaX_byXoutEXcreatedX_countX_out_out_storeXaX_byXinEXcreatedX_weight_sumX() {
        final Traversal<Vertex, Collection> traversal = get_g_V_storeXaX_byXoutEXcreatedX_countX_out_out_storeXaX_byXinEXcreatedX_weight_sumX();
        printTraversalForm(traversal);
        assertTrue(traversal.hasNext());
        final Collection store = traversal.next();
        assertFalse(traversal.hasNext());
        assertEquals(8, store.size());
        assertTrue(store.contains(0L));
        assertTrue(store.contains(1L));
        assertTrue(store.contains(2L));
        assertTrue(store.contains(1.0d));
        assertFalse(store.isEmpty());
    }

    public static class StandardTest extends StoreTest {
        public StandardTest() {
            requiresGraphComputer = false;
        }

        @Override
        public Traversal<Vertex, Collection> get_g_V_storeXaX_byXnameX_out_capXaX() {
            return g.V().store("a").by("name").out().cap("a");
        }

        @Override
        public Traversal<Vertex, Collection> get_g_VX1X_storeXaX_byXnameX_out_storeXaX_byXnameX_name_capXaX(final Object v1Id) {
            return g.V(v1Id).store("a").by("name").out().store("a").by("name").values("name").cap("a");
        }

        @Override
        public Traversal<Vertex, Set<String>> get_g_V_withSideEffectXa_setX_both_name_storeXaX() {
            return (Traversal) g.V().withSideEffect("a", HashSet::new).both().<String>values("name").store("a");
        }

        @Override
        public Traversal<Vertex, Collection> get_g_V_storeXaX_byXoutEXcreatedX_countX_out_out_storeXaX_byXinEXcreatedX_weight_sumX() {
            return (Traversal) g.V().store("a").by(outE("created").count()).out().out().store("a").by(inE("created").values("weight").sum());
        }
    }

    public static class ComputerTest extends StoreTest {
        public ComputerTest() {
            requiresGraphComputer = true;
        }

        @Override
        public Traversal<Vertex, Collection> get_g_V_storeXaX_byXnameX_out_capXaX() {
            return g.V().store("a").by("name").out().<Collection>cap("a").submit(g.compute());
        }

        @Override
        public Traversal<Vertex, Collection> get_g_VX1X_storeXaX_byXnameX_out_storeXaX_byXnameX_name_capXaX(final Object v1Id) {
            return g.V(v1Id).store("a").by("name").out().store("a").by("name").values("name").<Collection>cap("a").submit(g.compute());
        }

        @Override
        public Traversal<Vertex, Set<String>> get_g_V_withSideEffectXa_setX_both_name_storeXaX() {
            return (Traversal) g.V().withSideEffect("a", HashSet::new).both().<String>values("name").store("a").submit(g.compute());
        }

        @Override
        public Traversal<Vertex, Collection> get_g_V_storeXaX_byXoutEXcreatedX_countX_out_out_storeXaX_byXinEXcreatedX_weight_sumX() {
            return (Traversal) g.V().store("a").by(outE("created").count()).out().out().store("a").by(inE("created").values("weight").sum()).submit(g.compute());
        }
    }

}
