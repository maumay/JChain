/**
 *
 */
package com.github.maumay.jflow.impl.filter;

import com.github.maumay.jflow.impl.AbstractRichIterator;
import com.github.maumay.jflow.test.AbstractObjectAdapterTest;

import java.util.List;

/**
 * @author t
 *
 */
public final class ObjectFilterNotTest
        extends AbstractObjectAdapterTest<String, AbstractRichIterator<String>>
{
    @Override
    protected List<Case<String, AbstractRichIterator<String>>> getTestCases()
    {
        Adapter<String, AbstractRichIterator<String>> adapter = iter -> iter
                .filterNot(s -> s.startsWith("0"));
        return list(new Case<>(list(), adapter, list()),
                new Case<>(list("0", "1"), adapter, list("1")),
                new Case<>(list("1", "2"), adapter, list("1", "2")),
                new Case<>(list("0", "00"), adapter, list()));
    }
}
