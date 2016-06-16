package br.com.cds.connecta.portal.persistence.specification;

import br.com.cds.connecta.portal.entity.Dashboard;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 *
 * @author Nataniel Paiva
 */
public class DashboardSpecification {

    public static Specification<Dashboard> byDomain(final String domain) {
        return new Specification<Dashboard>() {
            @Override
            public Predicate toPredicate(Root<Dashboard> root,
                    CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(builder.upper(root.<String>get("domain")), domain.toUpperCase());
            }
        };
    }

    public static Specification<Dashboard> byId(final Long id) {
        return new Specification<Dashboard>() {
            @Override
            public Predicate toPredicate(Root<Dashboard> root,
                    CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.<Long>get("id"), id);
            }
        };
    }

    public static Specification<Dashboard> byIds(final List<Long> ids) {
        return new Specification<Dashboard>() {
            @Override
            public Predicate toPredicate(Root<Dashboard> root,
                    CriteriaQuery<?> query, CriteriaBuilder builder) {
                return root.<Long>get("id").in(ids);
            }
        };
    }

    public static Specification<Dashboard> byIdAndDomain(Long id, String domain) {
        return Specifications.where(byId(id)).and(byDomain(domain));
    }

    public static Specification<Dashboard> byIdsAndDomain(List<Long> ids, String domain) {
        return Specifications.where(byIds(ids)).and(byDomain(domain));
    }

}
