package br.com.cds.connecta.portal.persistence.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import br.com.cds.connecta.portal.entity.Dashboard;

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
    
    public static Specification<Dashboard> byPublicKey(final String publicKey) {
        return new Specification<Dashboard>() {
            @Override
            public Predicate toPredicate(Root<Dashboard> root,
                    CriteriaQuery<?> query, CriteriaBuilder builder) {
//            	
//            	Fetch<Dashboard,DashboardSection> sections = root.fetch("sections", JoinType.LEFT);
//            	Fetch<DashboardSection, DashboardItem> items = sections.fetch("items", JoinType.LEFT);
            	root.fetch("sections", JoinType.LEFT);
//            	.fetch("items", JoinType.LEFT);
                return builder.equal(root.<String>get("publicKey"), publicKey);
            }
        };
    }
    
//    public static Specification<Dashboard> fetchData() {
//        return new Specification<Dashboard>() {
//            @Override
//            public Predicate toPredicate(Root<Dashboard> root,
//                    CriteriaQuery<?> query, CriteriaBuilder builder) {
//                root.join("sections").fetch(items);
//            	
//            	Join<Dashboard, DashboardSection> join = root.join("sections");
//            	
//            	root.fetch("sections", JoinType.LEFT);
//            	Path sections = root.join("sections", JoinType.LEFT);
//            	Path items = sections.
//            	
//                return null;
//            }
//        };
//    }
    
    public static Specification<Dashboard> byIdAndDomain(Long id, String domain) {
        return Specifications.where(byId(id)).and(byDomain(domain));
    }

    public static Specification<Dashboard> byIdsAndDomain(List<Long> ids, String domain) {
        return Specifications.where(byIds(ids)).and(byDomain(domain));
    }

}
