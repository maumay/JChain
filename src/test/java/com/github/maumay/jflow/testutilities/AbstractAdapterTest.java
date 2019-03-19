/**
 * 
 */
package com.github.maumay.jflow.testutilities;

import org.junit.jupiter.api.Test;

/**
 * @author thomasb
 *
 */
public abstract class AbstractAdapterTest<T> implements AdapterTest<T>
{
	@Test
	public final void testImpl()
	{
		test();
	}
}
