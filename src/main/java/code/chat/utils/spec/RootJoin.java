package code.chat.utils.spec;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RootJoin<X> implements Root<X> {
    private final Join<?, X> join;
    private final EntityType<X> metaModel;

    RootJoin(Join<?, X> join, EntityType<X> metaModel) {
        this.join = join;
        this.metaModel = metaModel;
    }

    @Override
    public Set<Join<X, ?>> getJoins() {
        return join.getJoins();
    }

    @Override
    public boolean isCorrelated() {
        return join.isCorrelated();
    }

    @Override
    public From<X, X> getCorrelationParent() {
        return null;
    }


    @Override
    public <Y> Join<X, Y> join(SingularAttribute<? super X, Y> attribute) {
        return join.join(attribute);
    }

    @Override
    public <Y> Join<X, Y> join(SingularAttribute<? super X, Y> attribute, JoinType jt) {
        return join.join(attribute, jt);
    }

    @Override
    public <Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> collection) {
        return join.join(collection);
    }

    @Override
    public <Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> set) {
        return join.join(set);
    }

    @Override
    public <Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> list) {
        return join.join(list);
    }

    @Override
    public <K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> map) {
        return join.join(map);
    }

    @Override
    public <Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> collection, JoinType jt) {
        return join.join(collection, jt);
    }

    @Override
    public <Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> set, JoinType jt) {
        return join.join(set, jt);
    }

    @Override
    public <Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> list, JoinType jt) {
        return join.join(list, jt);
    }

    @Override
    public <K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> map, JoinType jt) {
        return join.join(map, jt);
    }

    @Override
    public <X1, Y> Join<X1, Y> join(String attributeName) {
        return join.join(attributeName);
    }

    @Override
    public <X1, Y> CollectionJoin<X1, Y> joinCollection(String attributeName) {
        return join.joinCollection(attributeName);
    }

    @Override
    public <X1, Y> SetJoin<X1, Y> joinSet(String attributeName) {
        return join.joinSet(attributeName);
    }

    @Override
    public <X1, Y> ListJoin<X1, Y> joinList(String attributeName) {
        return join.joinList(attributeName);
    }

    @Override
    public <X1, K, V> MapJoin<X1, K, V> joinMap(String attributeName) {
        return join.joinMap(attributeName);
    }

    @Override
    public <X1, Y> Join<X1, Y> join(String attributeName, JoinType jt) {
        return join.join(attributeName, jt);
    }

    @Override
    public <X1, Y> CollectionJoin<X1, Y> joinCollection(String attributeName, JoinType jt) {
        return join.joinCollection(attributeName, jt);
    }

    @Override
    public <X1, Y> SetJoin<X1, Y> joinSet(String attributeName, JoinType jt) {
        return join.joinSet(attributeName, jt);
    }

    @Override
    public <X1, Y> ListJoin<X1, Y> joinList(String attributeName, JoinType jt) {
        return join.joinList(attributeName, jt);
    }

    @Override
    public <X1, K, V> MapJoin<X1, K, V> joinMap(String attributeName, JoinType jt) {
        return join.joinMap(attributeName, jt);
    }

    @Override
    public Path<?> getParentPath() {
        return join.getParentPath();
    }

    @Override
    public <Y> Path<Y> get(SingularAttribute<? super X, Y> attribute) {
        return join.get(attribute);
    }

    @Override
    public <E, C extends Collection<E>> Expression<C> get(PluralAttribute<X, C, E> collection) {
        return join.get(collection);
    }

    @Override
    public <K, V, M extends Map<K, V>> Expression<M> get(MapAttribute<X, K, V> map) {
        return join.get(map);
    }

    @Override
    public Expression<Class<? extends X>> type() {
        return join.type();
    }

    @Override
    public <Y> Path<Y> get(String attributeName) {
        return join.get(attributeName);
    }

    @Override
    public Predicate isNull() {
        return join.isNull();
    }

    @Override
    public Predicate isNotNull() {
        return join.isNotNull();
    }

    @Override
    public Predicate in(Object... values) {
        return join.in(values);
    }

    @Override
    public Predicate in(Expression<?>... values) {
        return join.in(values);
    }

    @Override
    public Predicate in(Collection<?> values) {
        return join.in(values);
    }

    @Override
    public Predicate in(Expression<Collection<?>> values) {
        return join.in(values);
    }

    @Override
    public <X1> Expression<X1> as(Class<X1> type) {
        return join.as(type);
    }

    @Override
    public Selection<X> alias(String name) {
        return join.alias(name);
    }

    @Override
    public boolean isCompoundSelection() {
        return join.isCompoundSelection();
    }

    @Override
    public List<Selection<?>> getCompoundSelectionItems() {
        return join.getCompoundSelectionItems();
    }

    @Override
    public Class<? extends X> getJavaType() {
        return join.getJavaType();
    }

    @Override
    public String getAlias() {
        return join.getAlias();
    }

    @Override
    public Set<Fetch<X, ?>> getFetches() {
        return join.getFetches();
    }

    @Override
    public <Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> attribute) {
        return join.fetch(attribute);
    }

    @Override
    public <Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> attribute, JoinType jt) {
        return join.fetch(attribute, jt);
    }

    @Override
    public <Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> attribute) {
        return join.fetch(attribute);
    }

    @Override
    public <Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> attribute, JoinType jt) {
        return join.fetch(attribute, jt);
    }

    @Override
    public <X1, Y> Fetch<X1, Y> fetch(String attributeName) {
        return join.fetch(attributeName);
    }

    @Override
    public <X1, Y> Fetch<X1, Y> fetch(String attributeName, JoinType jt) {
        return join.fetch(attributeName, jt);
    }

    @Override
    public EntityType<X> getModel() {
        return metaModel;
    }
}
