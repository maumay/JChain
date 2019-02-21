/**
 *
 */
package com.github.maumay.jflow.iterators;

import java.util.Iterator;
import java.util.Map;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;

import com.gihub.maumay.jflow.iterators.misc.LongPair;
import com.gihub.maumay.jflow.iterators.misc.LongWith;
import com.github.maumay.jflow.iterators.factories.Iter;
import com.github.maumay.jflow.iterators.factories.Numbers;
import com.github.maumay.jflow.iterators.impl.AccumulationIterator;
import com.github.maumay.jflow.iterators.impl.AppendIterator;
import com.github.maumay.jflow.iterators.impl.DoubleMapIterator;
import com.github.maumay.jflow.iterators.impl.DropIterator;
import com.github.maumay.jflow.iterators.impl.DropwhileIterator;
import com.github.maumay.jflow.iterators.impl.FilteredIterator;
import com.github.maumay.jflow.iterators.impl.InsertIterator;
import com.github.maumay.jflow.iterators.impl.IntMapIterator;
import com.github.maumay.jflow.iterators.impl.LongCollectionConsumption;
import com.github.maumay.jflow.iterators.impl.LongMinMaxConsumption;
import com.github.maumay.jflow.iterators.impl.LongPredicateConsumption;
import com.github.maumay.jflow.iterators.impl.LongReductionConsumption;
import com.github.maumay.jflow.iterators.impl.MapIterator;
import com.github.maumay.jflow.iterators.impl.ObjectMapIterator;
import com.github.maumay.jflow.iterators.impl.SlicedIterator;
import com.github.maumay.jflow.iterators.impl.TakeIterator;
import com.github.maumay.jflow.iterators.impl.TakewhileIterator;
import com.github.maumay.jflow.iterators.impl.ZipIterator;

/**
 * A skeletal implementation of a LongFlow, users writing custom LongFlows
 * should subclass this class.
 *
 * @author ThomasB
 */
public abstract class AbstractEnhancedLongIterator extends AbstractOptionallySized
		implements EnhancedLongIterator
{
	public AbstractEnhancedLongIterator(OptionalInt size)
	{
		super(size);
	}

	@Override
	public AbstractEnhancedLongIterator slice(IntUnaryOperator indexMapping)
	{
		return new SlicedIterator.OfLong(this, indexMapping);
	}

	@Override
	public AbstractEnhancedLongIterator map(LongUnaryOperator f)
	{
		return new MapIterator.OfLong(this, f);
	}

	@Override
	public <E> AbstractEnhancedIterator<E> mapToObject(LongFunction<? extends E> f)
	{
		return new ObjectMapIterator.FromLong<>(this, f);
	}

	@Override
	public AbstractEnhancedDoubleIterator mapToDouble(LongToDoubleFunction f)
	{
		return new DoubleMapIterator.FromLong(this, f);
	}

	@Override
	public AbstractEnhancedIntIterator mapToInt(LongToIntFunction f)
	{
		return new IntMapIterator.FromLong(this, f);
	}

	@Override
	public <E> AbstractEnhancedIterator<LongWith<E>> zipWith(Iterator<? extends E> other)
	{
		return new ZipIterator.OfObjectAndLong<>(other, this);
	}

	@Override
	public AbstractEnhancedIterator<LongPair> zipWith(OfLong other)
	{
		return new ZipIterator.OfLongPair(this, other);
	}

	@Override
	public AbstractEnhancedIterator<LongWith<Integer>> enumerate()
	{
		return zipWith(Numbers.natural());
	}

	@Override
	public AbstractEnhancedLongIterator take(int n)
	{
		return new TakeIterator.OfLong(this, n);
	}

	@Override
	public AbstractEnhancedLongIterator takeWhile(LongPredicate predicate)
	{
		return new TakewhileIterator.OfLong(this, predicate);
	}

	@Override
	public AbstractEnhancedLongIterator drop(int n)
	{
		return new DropIterator.OfLong(this, n);
	}

	@Override
	public AbstractEnhancedLongIterator dropWhile(LongPredicate predicate)
	{
		return new DropwhileIterator.OfLong(this, predicate);
	}

	@Override
	public AbstractEnhancedLongIterator filter(LongPredicate predicate)
	{
		return new FilteredIterator.OfLong(this, predicate);
	}

	@Override
	public AbstractEnhancedLongIterator append(OfLong other)
	{
		return new AppendIterator.OfLong(this, other);
	}

	@Override
	public AbstractEnhancedLongIterator append(long... xs)
	{
		return append(Iter.longs(xs));
	}

	@Override
	public AbstractEnhancedLongIterator insert(OfLong other)
	{
		return new InsertIterator.OfLong(this, other);
	}

	@Override
	public AbstractEnhancedLongIterator insert(long... xs)
	{
		return insert(Iter.longs(xs));
	}

	@Override
	public AbstractEnhancedLongIterator scan(LongBinaryOperator accumulator)
	{
		return new AccumulationIterator.OfLong(this, accumulator);
	}

	@Override
	public AbstractEnhancedLongIterator scan(long id, LongBinaryOperator accumulator)
	{
		return new AccumulationIterator.OfLong(this, id, accumulator);
	}

	@Override
	public OptionalLong min()
	{
		return LongMinMaxConsumption.findMin(this);
	}

	@Override
	public long min(long defaultValue)
	{
		return LongMinMaxConsumption.findMin(this, defaultValue);
	}

	@Override
	public OptionalLong max()
	{
		return LongMinMaxConsumption.findMax(this);
	}

	@Override
	public long max(long defaultValue)
	{
		return LongMinMaxConsumption.findMax(this, defaultValue);
	}

	@Override
	public boolean areAllEqual()
	{
		return LongPredicateConsumption.allEqual(this);
	}

	@Override
	public boolean allMatch(LongPredicate predicate)
	{
		return LongPredicateConsumption.allMatch(this, predicate);
	}

	@Override
	public boolean anyMatch(LongPredicate predicate)
	{
		return LongPredicateConsumption.anyMatch(this, predicate);
	}

	@Override
	public boolean noneMatch(LongPredicate predicate)
	{
		return LongPredicateConsumption.noneMatch(this, predicate);
	}

	@Override
	public long count()
	{
		return LongReductionConsumption.count(this);
	}

	@Override
	public long fold(long id, LongBinaryOperator reducer)
	{
		return LongReductionConsumption.fold(this, id, reducer);
	}

	@Override
	public long fold(LongBinaryOperator reducer)
	{
		return LongReductionConsumption.fold(this, reducer);
	}

	@Override
	public OptionalLong foldOption(LongBinaryOperator reducer)
	{
		return LongReductionConsumption.foldOption(this, reducer);
	}

	@Override
	public long[] toArray()
	{
		return LongCollectionConsumption.toArray(this);
	}

	@Override
	public <K, V> Map<K, V> toMap(LongFunction<K> keyMapper, LongFunction<V> valueMapper)
	{
		return LongCollectionConsumption.toMap(this, keyMapper, valueMapper);
	}

	@Override
	public <K> Map<K, long[]> groupBy(LongFunction<K> classifier)
	{
		return LongCollectionConsumption.groupBy(this, classifier);
	}
}