package code.chat.utils;


import javax.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

public class SpecUtils {

    private SpecUtils() {}

    public static <X, T> Specification<X> equals(SingularAttribute<? super X, T> column, T value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(column), value);
    }
}
