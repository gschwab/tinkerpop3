package com.tinkerpop.gremlin.process.traversal.util;

import com.tinkerpop.gremlin.process.TraversalSideEffects;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class SideEffectHelper {

    private SideEffectHelper() {
    }

    public static void validateSideEffect(final String key, final Object value) throws IllegalArgumentException {
        if (null == value)
            throw TraversalSideEffects.Exceptions.sideEffectValueCanNotBeNull();
        if (null == key)
            throw TraversalSideEffects.Exceptions.sideEffectKeyCanNotBeNull();
        if (key.isEmpty())
            throw TraversalSideEffects.Exceptions.sideEffectKeyCanNotBeEmpty();
    }
}
