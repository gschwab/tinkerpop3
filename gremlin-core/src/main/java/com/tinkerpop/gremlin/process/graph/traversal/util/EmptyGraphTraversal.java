package com.tinkerpop.gremlin.process.graph.traversal.util;

import com.tinkerpop.gremlin.process.Step;
import com.tinkerpop.gremlin.process.computer.GraphComputer;
import com.tinkerpop.gremlin.process.graph.traversal.GraphTraversal;
import com.tinkerpop.gremlin.process.traversal.util.EmptyTraversal;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class EmptyGraphTraversal<S, E> extends EmptyTraversal<S, E> implements GraphTraversal.Admin<S, E>, GraphTraversal<S, E> {

    private static final EmptyGraphTraversal INSTANCE = new EmptyGraphTraversal<>();

    public static <A, B> EmptyGraphTraversal<A, B> instance() {
        return INSTANCE;
    }

    private EmptyGraphTraversal() {

    }

    @Override
    public GraphTraversal.Admin<S, E> asAdmin() {
        return this;
    }

    @Override
    public <E2> GraphTraversal.Admin<S, E2> addStep(final Step<?, E2> step) {
        return instance();
    }

    @Override
    public GraphTraversal<S, E> withPath() {
        return instance();
    }

    @Override
    public GraphTraversal<S, E> submit(final GraphComputer computer) {
        return instance();
    }

    @Override
    public GraphTraversal<S, E> iterate() {
        return this;
    }

    @Override
    public EmptyGraphTraversal<S, E> clone() throws CloneNotSupportedException {
        return instance();
    }
}
