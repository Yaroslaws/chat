package code.chat.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BigDecimalHelper {
    public static final BigDecimal TWENTY = BigDecimal.valueOf(20L);
    public static final BigDecimal THIRTY = BigDecimal.valueOf(30L);
    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100L);
    public static final BigDecimal ONE_THOUSAND = ONE_HUNDRED.multiply(BigDecimal.TEN);
    private static final int DEFAULT_SCALE = 2;
    private static final int DIVISION_SCALE = 6;
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    private BigDecimalHelper() {
    }

    public static BigDecimal getFromNullable(BigDecimal bd) {
        return bd == null ? BigDecimal.ZERO : bd;
    }


    public static BigDecimal getAbsFromNullable(BigDecimal bd) {
        return getFromNullable(bd).abs();
    }

    public static boolean equalsZero(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) == 0;
    }

    public static boolean notZero(BigDecimal bd) {
        return !equalsZero(bd);
    }

    public static boolean isPositive(BigDecimal bd) {
        return BigDecimal.ZERO.compareTo(bd) <= 0;
    }

    public static boolean isNegative(BigDecimal bd) {
        return !isPositive(bd);
    }

    public static boolean equalsZeroNullable(BigDecimal bd) {
        return equalsZero(getAbsFromNullable(bd));
    }

    public static BigDecimal from(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    public static BigDecimal minOf(BigDecimal one, BigDecimal two) {
        if (Objects.isNull(one)) {
            return two;
        }

        if (Objects.isNull(two)) {
            return one;
        }

        return one.compareTo(two) < 0 ? one : two;
    }

    public static BigDecimal minOf(List<BigDecimal> bigDecimalList) {
        return bigDecimalList.stream().min(Comparator.naturalOrder()).orElse(null);
    }

    public static BigDecimal maxOf(BigDecimal one, BigDecimal two) {
        if (one == null) {
            return two;
        }
        if (two == null) {
            return one;
        }
        return one.compareTo(two) > 0 ? one : two;
    }

    public static BigDecimal divideNoAbsOr(BigDecimal dividend, BigDecimal divisor, BigDecimal ifZero) {
        dividend = getFromNullable(dividend);
        divisor = getFromNullable(divisor);
        if (equalsZero(dividend)) {
            return BigDecimal.ZERO;
        }
        if (equalsZero(divisor)) {
            return ifZero;
        }
        return dividend.divide(divisor, 6, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal divideByThousand(BigDecimal value) {
        return divideByThousand(value, DEFAULT_SCALE);
    }

    public static BigDecimal divideByThousand(BigDecimal value, int scale) {
        return divideByThousand(value, scale, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal divideByThousand(BigDecimal value, int scale, RoundingMode mode) {
        if (value == null) {
            return null;
        }
        return value.divide(ONE_THOUSAND, scale, mode);
    }

    public static BigDecimal setDefaultScale(BigDecimal value) {
        return value != null ? value.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP) : null;
    }

    public static boolean equals(BigDecimal one, BigDecimal two) {
        if (Objects.isNull(one)) {
            return Objects.isNull(two);
        }

        return Objects.nonNull(two) && one.compareTo(two) == 0;
    }

    /**
     * Отношение с модулем числительного, для двух отрицательных чисел
     */
    public static BigDecimal divideWithAbsDivisor(BigDecimal dividend, BigDecimal divisor, BigDecimal ifZero) {
        dividend = getFromNullable(dividend);
        divisor = getFromNullable(divisor);
        if (equalsZero(dividend)) {
            return BigDecimal.ZERO;
        }
        if (equalsZero(divisor)) {
            return ifZero;
        }
        return dividend.divide(divisor.abs(), 6, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal getPercentageNoAbsNoNull(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, DIVISION_SCALE, DEFAULT_ROUNDING_MODE).multiply(ONE_HUNDRED);
    }

    /**
     * Поеделить, либо вернуть ifZero, если divisor = 0
     *
     * @param dividend верхняя часть отношения
     * @param divisor  нижняя часть отношения
     * @return результат деления, либо ifZero.
     */
    public static BigDecimal divideOr(BigDecimal dividend, BigDecimal divisor, BigDecimal ifZero) {
        divisor = getAbsFromNullable(divisor);
        dividend = getAbsFromNullable(dividend);
        return divideNoAbsOr(dividend, divisor, ifZero);
    }

    /**
     * Посчитать процент, либо вернуть 100, если divisor = 0
     * УБИРАЕТ ЗНАК МИНУСА ИЗ РЕЗУЛЬТАТА!
     *
     * @param dividend верхняя часть отношения
     * @param divisor  нижняя часть отношения (100%)
     * @return результат деления, умноженный на 100, либо 100.
     */
    public static BigDecimal getPercentageOr100(BigDecimal dividend, BigDecimal divisor) {
        return getPercentageOr(dividend, divisor, ONE_HUNDRED);
    }

    /**
     * Посчитать процент, либо вернуть ifZero, если divisor = 0
     * УБИРАЕТ ЗНАК МИНУСА ИЗ РЕЗУЛЬТАТА!
     *
     * @param dividend верхняя часть отношения
     * @param divisor  нижняя часть отношения (100%)
     * @param ifZero   значение при делении на 0
     * @return результат деления, умноженный на 100, либо ifZero
     */
    public static BigDecimal getPercentageOr(BigDecimal dividend, BigDecimal divisor, BigDecimal ifZero) {
        divisor = getAbsFromNullable(divisor);
        dividend = getAbsFromNullable(dividend);
        if (equalsZero(dividend)) {
            return BigDecimal.ZERO;
        }
        if (equalsZero(divisor)) {
            return ifZero;
        }
        return divideNoAbsOr(dividend, divisor, ifZero).multiply(ONE_HUNDRED);
    }

    /**
     * Посчитать процент - 100%, либо вернуть ifZero, если divisor = 0 и dividend != 0.
     * Если dividend == divisor == 0, то вернуть 0%.
     * Числитель и знаменатель берутся по модулю, переводит null в ноль.
     *
     * @param dividend верхняя часть отношения
     * @param divisor  нижняя часть отношения (100%)
     * @param ifZero   значение при делении на 0
     * @return (dividend / divisor) * 100 - 100,  либо ifZero
     */
    public static BigDecimal getPercentageOver100Or(BigDecimal dividend, BigDecimal divisor, BigDecimal ifZero) {
        divisor = getAbsFromNullable(divisor);
        dividend = getAbsFromNullable(dividend);
        if (equalsZero(dividend) && equalsZero(divisor)) {
            return BigDecimal.ZERO;
        }
        if (equalsZero(divisor)) {
            return ifZero;
        }
        return getPercentageOr100(dividend, divisor).subtract(ONE_HUNDRED);
    }

    /**
     * Процент увеличения числового показателя
     *
     * @param initialSum Исходная сумма
     * @param finalSum   Итоговая сумма
     * @return Положительный процент, если итоговая сумма увеличилась, отрицательный - если уменьшилась.
     */
    public static BigDecimal getIncreasingDiffPercent(BigDecimal initialSum, BigDecimal finalSum) {
        BigDecimal diffSum = finalSum.subtract(initialSum);
        BigDecimal percentAbs = getPercentageOr100(diffSum, initialSum);
        return diffSum.doubleValue() > 0 ? percentAbs : percentAbs.negate();
    }

    /**
     * Процент уменьшения числового показателя
     *
     * @param initialSum Исходная сумма
     * @param finalSum   Итоговая сумма
     * @return Положительный процент, если итоговая сумма уменьшилась, отрицательный - если увеличилась.
     */
    public static BigDecimal getDecreasingDiffPercent(BigDecimal initialSum, BigDecimal finalSum) {
        return getIncreasingDiffPercent(initialSum, finalSum).negate();
    }

    /**
     * Посчитать процент - 100%, либо вернуть 100%, если divisor = 0
     *
     * @param dividend верхняя часть отношения
     * @param divisor  нижняя часть отношения (100%)
     * @return (dividend / divisor) * 100 - 100,  либо 100
     */
    public static BigDecimal getPercentageOver100Or100(BigDecimal dividend, BigDecimal divisor) {
        return getPercentageOver100Or(dividend, divisor, ONE_HUNDRED);
    }

    public static BigDecimal fromString(String decimalString, BigDecimal defaultValue) {
        if (decimalString == null)
            return defaultValue;
        try {
            return new BigDecimal(normalizeDecimalString(decimalString));
        } catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }

    public static BigDecimal getPercentOf(BigDecimal value, int percent) {
        if (Objects.isNull(value)) {
            return BigDecimal.ZERO;
        }

        return value.multiply(BigDecimal.valueOf(percent)).divide(ONE_HUNDRED, 2, DEFAULT_ROUNDING_MODE);
    }

    public static <T> BigDecimal sumByField(Collection<T> list, Function<T, BigDecimal> fieldMap) {
        return list.stream().map(fieldMap).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public static <T> BigDecimal sumByFields(Collection<T> list, List<Function<T, BigDecimal>> fieldMaps) {
        BigDecimal result = BigDecimal.ZERO;
        for (Function<T, BigDecimal> fieldMap : fieldMaps) {
            result = result.add(list.stream().map(fieldMap)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        return result;
    }

    public static <TItem, TKey> Map<TKey, BigDecimal> getSumGroupedByField(Collection<TItem> collection,
                                                                           Function<TItem, TKey> groupField,
                                                                           Function<TItem, BigDecimal> valueField) {
        return collection.stream()
                .filter(item -> Objects.nonNull(groupField.apply(item)))
                .collect(Collectors.toMap(
                        groupField, item -> BigDecimalHelper.getFromNullable(valueField.apply(item)), BigDecimal::add));
    }

    public static <TItem, TKey1, TKey2> Map<TKey1, Map<TKey2, BigDecimal>> getSumGroupedByFields(
            Collection<TItem> collection, Function<TItem, TKey1> groupField1, Function<TItem, TKey2> groupField2,
            Function<TItem, BigDecimal> valueField) {
        return collection.stream()
                .filter(item -> Objects.nonNull(groupField1.apply(item)))
                .filter(item -> Objects.nonNull(groupField2.apply(item)))
                .collect(Collectors.groupingBy(
                        groupField1,
                        Collectors.toMap(
                                groupField2,
                                item -> BigDecimalHelper.getFromNullable(valueField.apply(item)), BigDecimal::add)));
    }

    public static <TKey> Map<TKey, BigDecimal> mergeMapsWithSum(Map<TKey, BigDecimal> one, Map<TKey, BigDecimal> two) {
        return Stream.of(one, two)
                .flatMap(map -> map.entrySet().stream())
                .filter(entry -> !BigDecimalHelper.equalsZeroNullable(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, BigDecimal::add));
    }

    public static boolean lessThan5Percent(BigDecimal dividend, BigDecimal divisor) {
        if (equalsZeroNullable(dividend)) return true;
        return getPercentageOr100(dividend, divisor).compareTo(BigDecimal.valueOf(5L)) < 0;
    }

    private static String normalizeDecimalString(String decimalString) {
        return decimalString
                // Remove all whitespace
                .replaceAll("\\s", "")
                // Replace all potential decimal separators with dot
                .replaceAll("[^-+eE0-9]+", ".");
    }

    public static BigDecimal nvl(BigDecimal value) {
        return Objects.isNull(value) ? BigDecimal.ZERO : value;
    }

    public static boolean moreThanPercent(BigDecimal dividend, BigDecimal divisor, BigDecimal bigDecimal) {
        if (BigDecimalHelper.equalsZero(dividend) && BigDecimalHelper.equalsZero(divisor)) return false;
        return getPercentageOr100(dividend, divisor).compareTo(bigDecimal) > 0;
    }

    public static boolean equalWithinDiff(BigDecimal bd1, BigDecimal bd2, BigDecimal maxDiff) {
        if (Objects.equals(bd1, bd2)) {
            return true;
        }
        if (bd1 == null || bd2 == null) {
            return false;
        }
        if (bd1.compareTo(bd2) == 0) {
            return true;
        }
        if (maxDiff == null) {
            return false;
        }
        maxDiff = maxDiff.abs();
        BigDecimal diff = bd1.subtract(bd2).abs();
        return maxDiff.compareTo(diff) >= 0;
    }

    @SuppressWarnings("squid:S1452")
    public static Collector<BigDecimal, ?, BigDecimal> summing() {
        return Collectors.mapping(BigDecimalHelper::nvl,
                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
    }

    public static BigDecimal maxNullable(BigDecimal bd1, BigDecimal bd2) {
        return getFromNullable(bd1).max(getFromNullable(bd2));
    }

    public static BigDecimal getSum(Stream<BigDecimal> values) {
        return values.collect(BigDecimalHelper.summing());
    }

    public static BigDecimal getSum(Collection<BigDecimal> values) {
        return getSum(values.stream());
    }

    public static BigDecimal getAvg(Collection<BigDecimal> values) {
        return getAvg(values, DEFAULT_SCALE);
    }

    public static BigDecimal getAvg(Collection<BigDecimal> values, int scale) {
        switch (values.size()) {
            case 0:
                return BigDecimal.ZERO;
            case 1:
                return getSum(values).setScale(scale, DEFAULT_ROUNDING_MODE);
            default:
                return getSum(values).divide(BigDecimal.valueOf(values.size()), DIVISION_SCALE, DEFAULT_ROUNDING_MODE)
                        .setScale(scale, DEFAULT_ROUNDING_MODE);
        }
    }
}
