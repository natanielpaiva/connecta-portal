package br.com.cds.connecta.portal.persistence.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.cds.connecta.portal.entity.Role;

public class RoleSpecification {
	
	public static Specification<Role> byId(final Long id) {
		return new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, 
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.<Long> get("id"), id);
			}
		};
	}
	
	public static Specification<Role> byName(final String name) {
		return new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, 
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.<Long> get("name"), name);
			}
		};
	}
	

}
