package com.tinkerpop.gremlin.process.graph.traversal.strategy;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.TraversalEngine;
import com.tinkerpop.gremlin.process.graph.traversal.step.util.MarkerIdentityStep;
import com.tinkerpop.gremlin.structure.util.StringFactory;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class LabeledEndStepStrategy extends AbstractTraversalStrategy {

    private static final LabeledEndStepStrategy INSTANCE = new LabeledEndStepStrategy();

    private LabeledEndStepStrategy() {
    }

    @Override
    public void apply(final Traversal.Admin<?, ?> traversal, final TraversalEngine engine) {
        if (traversal.getEndStep().getLabel().isPresent())
            traversal.addStep(new MarkerIdentityStep<>(traversal));
    }

    public static LabeledEndStepStrategy instance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return StringFactory.traversalStrategyString(this);
    }
}
