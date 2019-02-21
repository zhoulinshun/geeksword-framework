package org.geeksword.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author 周林顺
 * @Description:
 * @Date: Created in 2018/4/24.
 */
public class StreamUtil {


    /**
     * 根据传入的keyMapper对collection分类 对于重复的key会默认保留最后面一个value
     *
     * @param collection
     * @param keyMapper
     * @param <ID>
     * @param <T>
     * @return
     */

    public static <ID, T> Map<ID, T> groupBySingle(Collection<T> collection, Function<T, ID> keyMapper) {
        return collection.stream().collect(toMap(keyMapper));
    }

    public static <ID, T,F> Map<ID, F> groupBySingle(Collection<T> collection, Function<T, ID> keyMapper, Function<T, F> valueMapper) {
        return collection.stream().collect(toMap(keyMapper,valueMapper));
    }

    public static <ID, T> Map<ID, T> groupBySingle(Stream<T> stream, Function<T, ID> keyMapper) {
        return stream.collect(toMap(keyMapper));
    }

    public static <ID, T,F> Map<ID, F> groupBySingle(Stream<T> stream, Function<T, ID> keyMapper, Function<T, F> valueMapper) {
        return stream.collect(toMap(keyMapper,valueMapper));
    }

    public static <ID, T> Collector<T, ?, Map<ID, T>> toMap(Function<T, ID> keyMapper) {
        return toMap(keyMapper, c -> c);
    }

    public static <ID, T, F> Collector<T, ?, Map<ID, F>> toMap(Function<T, ID> keyMapper, Function<T, F> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, (f, f2) -> f2);
    }

    public static <T, R> Collection<R> map(Collection<T> list, Function<T, R> map) {
        return list.stream().map(map).collect(Collectors.toList());
    }

    public static <T, R> Collection<R> map(T[] list, Function<T, R> map) {
        return Arrays.stream(list).map(map).collect(Collectors.toList());
    }

    public static <T> Collection<T> filter(T[] list, Predicate<T> test){
        return Arrays.stream(list).filter(test).collect(Collectors.toList());
    }

    public static <T> Collection<T> filter(Collection<T> list, Predicate<T> test){
        return list.stream().filter(test).collect(Collectors.toList());
    }


}
