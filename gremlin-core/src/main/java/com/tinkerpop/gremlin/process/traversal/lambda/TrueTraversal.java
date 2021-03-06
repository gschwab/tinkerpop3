package com.tinkerpop.gremlin.process.traversal.lambda;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class TrueTraversal<S, E> extends AbstractLambdaTraversal<S, E> {

    private static final TrueTraversal INSTANCE = new TrueTraversal<>();

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public String toString() {
        return "true";
    }

    @Override
    public TrueTraversal<S, E> clone() throws CloneNotSupportedException {
        return INSTANCE;
    }

    public static <A, B> TrueTraversal<A, B> instance() {
        return INSTANCE;
    }


}
