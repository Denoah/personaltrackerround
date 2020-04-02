package kotlin.streams.jdk8;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\000\n\002\030\002\n\002\020\006\n\002\030\002\n\002\020\b\n\002\030\002\n\002\020\t\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\000\032\022\020\000\032\b\022\004\022\0020\0020\001*\0020\003H\007\032\022\020\000\032\b\022\004\022\0020\0040\001*\0020\005H\007\032\022\020\000\032\b\022\004\022\0020\0060\001*\0020\007H\007\032\036\020\000\032\b\022\004\022\002H\b0\001\"\004\b\000\020\b*\b\022\004\022\002H\b0\tH\007\032\036\020\n\032\b\022\004\022\002H\b0\t\"\004\b\000\020\b*\b\022\004\022\002H\b0\001H\007\032\022\020\013\032\b\022\004\022\0020\0020\f*\0020\003H\007\032\022\020\013\032\b\022\004\022\0020\0040\f*\0020\005H\007\032\022\020\013\032\b\022\004\022\0020\0060\f*\0020\007H\007\032\036\020\013\032\b\022\004\022\002H\b0\f\"\004\b\000\020\b*\b\022\004\022\002H\b0\tH\007?\006\r"}, d2={"asSequence", "Lkotlin/sequences/Sequence;", "", "Ljava/util/stream/DoubleStream;", "", "Ljava/util/stream/IntStream;", "", "Ljava/util/stream/LongStream;", "T", "Ljava/util/stream/Stream;", "asStream", "toList", "", "kotlin-stdlib-jdk8"}, k=2, mv={1, 1, 15}, pn="kotlin.streams")
public final class StreamsKt
{
  public static final Sequence<Double> asSequence(DoubleStream paramDoubleStream)
  {
    Intrinsics.checkParameterIsNotNull(paramDoubleStream, "$this$asSequence");
    (Sequence)new Sequence()
    {
      public Iterator<Double> iterator()
      {
        return (Iterator)this.$this_asSequence$inlined.iterator();
      }
    };
  }
  
  public static final Sequence<Integer> asSequence(IntStream paramIntStream)
  {
    Intrinsics.checkParameterIsNotNull(paramIntStream, "$this$asSequence");
    (Sequence)new Sequence()
    {
      public Iterator<Integer> iterator()
      {
        return (Iterator)this.$this_asSequence$inlined.iterator();
      }
    };
  }
  
  public static final Sequence<Long> asSequence(LongStream paramLongStream)
  {
    Intrinsics.checkParameterIsNotNull(paramLongStream, "$this$asSequence");
    (Sequence)new Sequence()
    {
      public Iterator<Long> iterator()
      {
        return (Iterator)this.$this_asSequence$inlined.iterator();
      }
    };
  }
  
  public static final <T> Sequence<T> asSequence(Stream<T> paramStream)
  {
    Intrinsics.checkParameterIsNotNull(paramStream, "$this$asSequence");
    (Sequence)new Sequence()
    {
      public Iterator<T> iterator()
      {
        return this.$this_asSequence$inlined.iterator();
      }
    };
  }
  
  public static final <T> Stream<T> asStream(Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$asStream");
    paramSequence = StreamSupport.stream((Supplier)new Supplier()
    {
      public final Spliterator<T> get()
      {
        return Spliterators.spliteratorUnknownSize(this.$this_asStream.iterator(), 16);
      }
    }, 16, false);
    Intrinsics.checkExpressionValueIsNotNull(paramSequence, "StreamSupport.stream({ S…literator.ORDERED, false)");
    return paramSequence;
  }
  
  public static final List<Double> toList(DoubleStream paramDoubleStream)
  {
    Intrinsics.checkParameterIsNotNull(paramDoubleStream, "$this$toList");
    paramDoubleStream = paramDoubleStream.toArray();
    Intrinsics.checkExpressionValueIsNotNull(paramDoubleStream, "toArray()");
    return ArraysKt.asList(paramDoubleStream);
  }
  
  public static final List<Integer> toList(IntStream paramIntStream)
  {
    Intrinsics.checkParameterIsNotNull(paramIntStream, "$this$toList");
    paramIntStream = paramIntStream.toArray();
    Intrinsics.checkExpressionValueIsNotNull(paramIntStream, "toArray()");
    return ArraysKt.asList(paramIntStream);
  }
  
  public static final List<Long> toList(LongStream paramLongStream)
  {
    Intrinsics.checkParameterIsNotNull(paramLongStream, "$this$toList");
    paramLongStream = paramLongStream.toArray();
    Intrinsics.checkExpressionValueIsNotNull(paramLongStream, "toArray()");
    return ArraysKt.asList(paramLongStream);
  }
  
  public static final <T> List<T> toList(Stream<T> paramStream)
  {
    Intrinsics.checkParameterIsNotNull(paramStream, "$this$toList");
    paramStream = paramStream.collect(Collectors.toList());
    Intrinsics.checkExpressionValueIsNotNull(paramStream, "collect(Collectors.toList<T>())");
    return (List)paramStream;
  }
}
