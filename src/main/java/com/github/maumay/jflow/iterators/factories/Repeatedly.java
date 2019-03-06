/**
 * 
 */
package com.github.maumay.jflow.iterators.factories;

import java.util.Arrays;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.github.maumay.jflow.iterables.DoubleIterable;
import com.github.maumay.jflow.iterables.IntIterable;
import com.github.maumay.jflow.iterables.LongIterable;
import com.github.maumay.jflow.iterators.DoubleIterator;
import com.github.maumay.jflow.iterators.EnhancedIterator;
import com.github.maumay.jflow.iterators.IntIterator;
import com.github.maumay.jflow.iterators.LongIterator;
import com.github.maumay.jflow.iterators.impl.ApplicationIterator;
import com.github.maumay.jflow.iterators.impl.CyclicIterator;
import com.github.maumay.jflow.iterators.impl.FunctionIterator;

/**
 * Static methods for building infinite {@link EnhancedIterator}s.
 * 
 * @author ThomasB
 */
public class Repeatedly
{
	private Repeatedly()
	{
	}

	/**
	 * Creates an infinite EnhancedIterator beginning with the given initial value
	 * and followed by repeated application of the given function to the previous
	 * value in the EnhancedIterator.
	 * 
	 * @param                     <E> The element type of the created iterator.
	 * @param applicationOperator The unary operator which will be applied
	 *                            repeatedly.
	 * @param initialValue        The first value in the EnhancedIterator
	 * @return An infinite EnhancedIterator generated by repeated function
	 *         application.
	 */
	public static <E> EnhancedIterator<E> apply(UnaryOperator<E> applicationOperator,
			E initialValue)
	{
		return new ApplicationIterator.OfObjects<E>(applicationOperator, initialValue);
	}

	/**
	 * Build an infinite EnhancedIterator from a function which generates instances
	 * of a specified type.
	 *
	 * @param                  <E> The target type of the supply function.
	 * @param instanceSupplier A supplier of objects.
	 * @return An infinite EnhancedIterator built from repeatedly calling the supply
	 *         function.
	 */
	public static <E> EnhancedIterator<E> call(Supplier<E> instanceSupplier)
	{
		return new FunctionIterator.OfObject<>(i -> instanceSupplier.get());
	}

	/**
	 * Build an infinite EnhancedIterator from a function which accepts a positive
	 * integer argument representing a sequence index.
	 *
	 * @param                  <E> The target type of the indexing function.
	 * @param indexingFunction A function whose domain is the natural numbers.
	 * @return An infinite EnhancedIterator built from apply the indexing function
	 *         to each natural number in turn.
	 */
	public static <E> EnhancedIterator<E> index(IntFunction<E> indexingFunction)
	{
		return new FunctionIterator.OfObject<>(indexingFunction);
	}

	/**
	 * Creates an infinite, continuously looping EnhancedIterator from an iterable
	 * sequence.
	 *
	 * @param        <E> The upper bound on the type of elements in the source.
	 *
	 * @param source The sequence which will be continuously looped over.
	 * @return A EnhancedIterator which continuously loops through the elements of
	 *         the source sequence.
	 */
	public static <E> EnhancedIterator<E> cycle(Iterable<? extends E> source)
	{
		return new CyclicIterator.OfObject<>(source::iterator);
	}

	/**
	 * Creates an infinite, continuously looping EnhancedIterator from a varargs
	 * array source.
	 *
	 * @param          <E> The type of the element which will be repeated.
	 *
	 * @param elements The references which will be cycled through.
	 * @return A EnhancedIterator which indefinitely cycles through the source
	 *         elements.
	 */
	@SafeVarargs
	public static <E> EnhancedIterator<E> cycle(E... elements)
	{
		return cycle(Arrays.asList(elements));
	}

	// Ints

	/**
	 * Creates an infinite EnhancedIterator beginning with the given initial value
	 * and followed by repeated application of the given function to the previous
	 * value in the EnhancedIterator.
	 * 
	 * @param applicationOperator The unary operator which will be applied
	 *                            repeatedly.
	 * @param initialValue        The first value in the EnhancedIterator
	 * @return An infinite EnhancedIterator generated by repeated function
	 *         application.
	 */
	public static IntIterator applyInts(IntUnaryOperator applicationOperator, int initialValue)
	{
		return new ApplicationIterator.OfInts(applicationOperator, initialValue);
	}

	/**
	 * Build an infinite IntEnhancedIterator from a function which generates
	 * primitive ints.
	 *
	 * @param indexingFunction A supplier of Ints.
	 * @return An infinite IntEnhancedIterator built from repeatedly calling the
	 *         supply function.
	 */
	public static IntIterator callInts(IntSupplier indexingFunction)
	{
		return Repeatedly.indexInts(i -> indexingFunction.getAsInt());
	}

	/**
	 * Build an infinite IntEnhancedIterator from a function which accepts a
	 * positive integer argument representing a sequence index.
	 *
	 * @param indexingFunction A function whose domain is the natural numbers.
	 * @return An infinite IntEnhancedIterator built from apply the indexing
	 *         function to each natural number in turn.
	 */
	public static IntIterator indexInts(final IntUnaryOperator indexingFunction)
	{
		return new FunctionIterator.OfInt(indexingFunction, -1);
	}

	/**
	 * Creates an infinite, continuously looping IntEnhancedIterator from an
	 * iterable sequence of ints.
	 *
	 * @param source The sequence which will be continuously looped over.
	 * @return An IntEnhancedIterator which continuously loops through the elements
	 *         of the source sequence.
	 */
	public static IntIterator cycleInts(IntIterable source)
	{
		return new CyclicIterator.OfInt(source::iter);
	}

	/**
	 * Creates an infinite, continuously looping IntEnhancedIterator from an int
	 * array.
	 *
	 * @param source The array which will be continuously looped over.
	 * @return An IntEnhancedIterator which continuously loops through the elements
	 *         of the source array.
	 */
	public static IntIterator cycleInts(int... source)
	{
		return new CyclicIterator.OfInt(() -> Iter.ints(source));
	}

	// Doubles

	/**
	 * Creates an infinite EnhancedIterator beginning with the given initial value
	 * and followed by repeated application of the given function to the previous
	 * value in the EnhancedIterator.
	 * 
	 * @param applicationOperator The unary operator which will be applied
	 *                            repeatedly.
	 * @param initialValue        The first value in the EnhancedIterator
	 * @return An infinite EnhancedIterator generated by repeated function
	 *         application.
	 */
	public static DoubleIterator applyDoubles(DoubleUnaryOperator applicationOperator,
			double initialValue)
	{
		return new ApplicationIterator.OfDoubles(applicationOperator, initialValue);
	}

	/**
	 * Build an infinite DoubleEnhancedIterator from a function which generates
	 * primitive doubles.
	 *
	 * @param indexingFunction A supplier of Doubles.
	 * @return An infinite DoubleEnhancedIterator built from repeatedly calling the
	 *         supply function.
	 */
	public static DoubleIterator callDoubles(final DoubleSupplier indexingFunction)
	{
		return Repeatedly.indexDoubles(i -> indexingFunction.getAsDouble());
	}

	/**
	 * Build an infinite DoubleEnhancedIterator from a function which accepts a
	 * positive integer argument representing a sequence index.
	 *
	 * @param indexingFunction A function whose domain is the natural numbers.
	 * @return An infinite DoubleEnhancedIterator built from apply the indexing
	 *         function to each natural number in turn.
	 */
	public static DoubleIterator indexDoubles(final IntToDoubleFunction indexingFunction)
	{
		return new FunctionIterator.OfDouble(indexingFunction, -1);
	}

	/**
	 * Creates an infinite, continuously looping DoubleEnhancedIterator from an
	 * iterable sequence of doubles.
	 *
	 * @param source The sequence which will be continuously looped over.
	 * @return An DoubleEnhancedIterator which continuously loops through the
	 *         elements of the source sequence.
	 */
	public static DoubleIterator cycleDoubles(DoubleIterable source)
	{
		return new CyclicIterator.OfDouble(source::iter);
	}

	/**
	 * Creates an infinite, continuously looping DoubleEnhancedIterator from an
	 * double array.
	 *
	 * @param source The array which will be continuously looped over.
	 * @return An DoubleEnhancedIterator which continuously loops through the
	 *         elements of the source array.
	 */
	public static DoubleIterator cycleDoubles(double... source)
	{
		return new CyclicIterator.OfDouble(() -> Iter.doubles(source));
	}

	// Longs

	/**
	 * Creates an infinite EnhancedIterator beginning with the given initial value
	 * and followed by repeated application of the given function to the previous
	 * value in the EnhancedIterator.
	 * 
	 * @param applicationOperator The unary operator which will be applied
	 *                            repeatedly.
	 * @param initialValue        The first value in the EnhancedIterator
	 * @return An infinite EnhancedIterator generated by repeated function
	 *         application.
	 */
	public static LongIterator applyLongs(LongUnaryOperator applicationOperator, long initialValue)
	{
		return new ApplicationIterator.OfLongs(applicationOperator, initialValue);
	}

	/**
	 * Build an infinite LongEnhancedIterator from a function which generates
	 * primitive longs.
	 *
	 * @param longSupplier A supplier of longs.
	 * @return An infinite LongEnhancedIterator built from repeatedly calling the
	 *         supply function.
	 */
	public static LongIterator callLongs(LongSupplier longSupplier)
	{
		return Repeatedly.indexLongs(i -> longSupplier.getAsLong());
	}

	/**
	 * Build an infinite LongEnhancedIterator from a function which accepts a
	 * positive integer argument representing a sequence index.
	 *
	 * @param indexingFunction A function whose domain is the natural numbers.
	 * @return An infinite LongEnhancedIterator built from apply the indexing
	 *         function to each natural number in turn.
	 */
	public static LongIterator indexLongs(IntToLongFunction indexingFunction)
	{
		return new FunctionIterator.OfLong(indexingFunction, -1);
	}

	/**
	 * Creates an infinite, continuously looping LongEnhancedIterator from an
	 * iterable sequence of longs.
	 *
	 * @param source The sequence which will be continuously looped over.
	 * @return An LongEnhancedIterator which continuously loops through the elements
	 *         of the source sequence.
	 */
	public static LongIterator cycleLongs(LongIterable source)
	{
		return new CyclicIterator.OfLong(source::iter);
	}

	/**
	 * Creates an infinite, continuously looping LongEnhancedIterator from an long
	 * array.
	 *
	 * @param source The array which will be continuously looped over.
	 * @return An LongEnhancedIterator which continuously loops through the elements
	 *         of the source array.
	 */
	public static LongIterator cycleLongs(long... source)
	{
		return new CyclicIterator.OfLong(() -> Iter.longs(source));
	}
}
