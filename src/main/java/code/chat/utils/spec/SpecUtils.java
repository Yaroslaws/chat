package code.chat.utils.spec;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class SpecUtils {
    private SpecUtils() {
    }

    public static <X, T> Specification<X> in(SingularAttribute<? super X, T> column, Collection<T> values) {
        return (root, criteriaQuery, criteriaBuilder) ->
                root.get(column).in(values);
    }

    @SafeVarargs
    public static <X, T> Specification<X> in(SingularAttribute<? super X, T> column, T... values) {
        return (root, criteriaQuery, criteriaBuilder) ->
                root.get(column).in((Object[]) values);
    }

    public static <X> Specification<X> like(SingularAttribute<? super X, String> column, String like) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(column), like);
    }

    public static <X> Specification<X> likeUpper(SingularAttribute<? super X, String> column, String like) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get(column)), like.toUpperCase());
    }

    public static <X> Specification<X> inLike(SingularAttribute<? super X, String> column, String... likes) {
        return inLike(column, false, likes);
    }

    public static <X> Specification<X> inLike(SingularAttribute<? super X, String> column, boolean ignoreCase, String... likes) {
        Function<String, Specification<X>> getLike = ignoreCase ?
                l -> likeUpper(column, l.toUpperCase()) :
                l -> like(column, l);
        return reduceSpecificationsByOrRule(
                Arrays.stream(likes).map(getLike).collect(Collectors.toList()));
    }

    public static <X> Specification<X> notLikeUpper(SingularAttribute<? super X, String> column, String like) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.notLike(criteriaBuilder.upper(root.get(column)), like.toUpperCase());
    }

    public static <X> Specification<X> gt(SingularAttribute<? super X, ? extends Number> col, Number num) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.gt(root.get(col), num);
    }

    public static <T, D extends Comparable<? super D>> Specification<T> greaterThan(
            SingularAttribute<? super T, ? extends D> column, D value) {
        return (r, q, cb) -> cb.greaterThan(r.get(column), value);
    }

    public static <T, D extends Comparable<? super D>> Specification<T> greaterThanOrEqualTo(
            SingularAttribute<? super T, ? extends D> column, D value) {
        return (r, q, cb) -> cb.greaterThanOrEqualTo(r.get(column), value);
    }

    public static <T> Specification<T> greaterThanOrEqualTo(SingularAttribute<? super T, String> column, Date value, String dateFormat) {
        return (r, q, cb) ->
                cb.greaterThanOrEqualTo(cb.function("to_date", Date.class, r.get(column), cb.literal(dateFormat)), value);
    }

    public static <T, D extends Comparable<? super D>> Specification<T> lessThan(
            SingularAttribute<? super T, ? extends D> column, D value) {
        return (r, q, cb) -> cb.lessThan(r.get(column), value);
    }

    public static <T, D extends Comparable<? super D>> Specification<T> lessThanOrEqualTo(
            SingularAttribute<? super T, ? extends D> column, D value) {
        return (r, q, cb) -> cb.lessThanOrEqualTo(r.get(column), value);
    }

    /**
     * Собирает базовую спецификацию и переданные в where(spec1.and.spec2.and..)
     */
    @SafeVarargs
    public static <T> Specification<T> reduceSpecifications(Specification<T> rootSpec, Specification<T>... specs) {
        return reduceSpecifications(rootSpec, Arrays.asList(specs));
    }

    /**
     * Собирает базовую спецификацию и переданные в where(spec1.and.spec2.and..)
     */
    public static <T> Specification<T> reduceSpecifications(Specification<T> rootSpec, Collection<Specification<T>> specs) {
        Specification<T> base = Specification.where(rootSpec);
        return specs.stream()
                .map(Specification::where)
                .reduce(base, Specification::and);
    }

    public static <T> Specification<T> reduceSpecifications(Collection<Specification<T>> specs) {
        return specs.stream()
                .map(Specification::where)
                .reduce(Specification.where(trueSpecification()), Specification::and);
    }

    /**
     * Собирает спецификаци в where(spec1.or.spec2.or..)
     */
    @SafeVarargs
    public static <T> Specification<T> reduceSpecificationsByOrRule(Specification<T>... specs) {
        return reduceSpecificationsByOrRule(Arrays.asList(specs));
    }

    public static <T> Specification<T> reduceSpecificationsByOrRule(Collection<Specification<T>> specs) {
        if (specs.isEmpty())
            return trueSpecification();

        return specs.stream()
                .map(Specification::where)
                .reduce(Specification.where(falseSpecification()), Specification::or);
    }

    public static <T> Specification<T> falseSpecification() {
        return SpecUtils::falsePredicate;
    }


    public static <T> Specification<T> trueSpecification() {
        return SpecUtils::truePredicate;
    }

    private static <T> Predicate falsePredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.disjunction();
    }

    public static <T> Predicate truePredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.conjunction();
    }

    public static <T> Specification<T> isColumnNotZero(SingularAttribute<T, ?> column) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(column), 0).not();
    }


    public static <T> Specification<T> between(SingularAttribute<? super T, LocalDate> column, LocalDate dateFrom, LocalDate dateTo) {
        return betweenImpl(column, dateFrom, nonNull(dateTo) ? dateTo.plusDays(1) : null);
    }

    public static <T> Specification<T> between(SingularAttribute<? super T, LocalDateTime> column, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return betweenImpl(column, dateFrom, nonNull(dateTo) ? dateTo.plusDays(1) : null);
    }

    private static <T, D extends Comparable<? super D>> Specification<T> betweenImpl(SingularAttribute<? super T, D> column, D dateFrom, D dateTo) {
        return (r, q, cb) -> {
            Predicate predicate = cb.and();
            if (nonNull(dateFrom)) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(r.get(column), dateFrom));
            }
            if (nonNull(dateTo)) {
                predicate = cb.and(predicate, cb.lessThan(r.get(column), dateTo));
            }
            return predicate;
        };
    }

    public static <X, T> Specification<X> equals(SingularAttribute<? super X, T> column, T value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(column), value);
    }

    public static <X> Specification<X> equalsIgnoreCase(SingularAttribute<? super X, String> column, String equals) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get(column)), equals.toUpperCase());
    }

    public static <X> Specification<X> isNull(SingularAttribute<? super X, ?> column) {
        return (root, criteriaQuery, criteriaBuilder) ->
                root.get(column).isNull();
    }

    private static <Y> Root<Y> asRoot(Join<?, Y> join, Metamodel metamodel) {
        return new RootJoin<>(
                join,
                metamodel.entity(join.getModel().getBindableJavaType()));
    }

    public static <Y> Root<Y> asRoot(Join<?, Y> join, EntityManager entityManager) {
        return asRoot(join, entityManager.getMetamodel());
    }

    public static <Y> Root<Y> asRoot(Join<?, Y> join, CriteriaBuilder criteriaBuilder) {
        return asRoot(join, ((CriteriaBuilderImpl) criteriaBuilder).getEntityManagerFactory().getMetamodel());
    }

    public static <X> Specification<X> isTrue(SingularAttribute<? super X, Boolean> column) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get(column));
    }

    public static <X> Specification<X> isFalse(SingularAttribute<? super X, Boolean> column) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get(column));
    }
}
