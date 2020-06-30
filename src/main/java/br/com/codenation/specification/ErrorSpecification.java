package br.com.codenation.specification;

import br.com.codenation.model.Application;
import br.com.codenation.model.Error;
import br.com.codenation.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.codenation.commons.EnvironmentEnum;
import br.com.codenation.commons.LevelEnum;

public class ErrorSpecification {

	public static Specification<Error> addFilters(Map<Class<?>, Class<?>> params) {
		return new Specification<Error>() {
			public Predicate toPredicate(Root<Error> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				

				Set<Entry<Class<?>, Class<?>>> set = params.entrySet();
				Iterator it = set.iterator();
				
				while (it.hasNext()) {
					Entry<String, String> entry = (Entry)it.next();
					try {
						Class<?> klass = root.get(entry.getKey()).getJavaType();
						switch(entry.getKey()) {
							case "environment": 
								predicates.add(cb.equal(root.get("environment"), castParameters(klass, entry.getValue())));
								break;
							case "level":
								predicates.add(cb.equal(root.get("level"), castParameters(klass, entry.getValue())));
								break;
							case "application": 
								Join<Error, Application> application = root.join("application");
								predicates.add(cb.like(application.get("name"), "%"+entry.getValue()+"%"));
								break;
							case "user": 		
								Join<Error, User> user = root.join("user");
								predicates.add(cb.like(user.get("name"), "%"+entry.getValue()+"%"));
								break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
				return cb.and(predicates.toArray(new Predicate[] {}));
			}
		};
	}
	
	private static Object castParameters(Class<? extends Object> type, String param) {
		if (type.equals(EnvironmentEnum.class)) {
			return EnvironmentEnum.valueOf(param);
		} else if (type.equals(LevelEnum.class)) {
			return LevelEnum.valueOf(param);
		} if (type.equals(Integer.class)) {
			return Integer.parseInt(param);
		} else if (type.equals(Long.class)) {
			return Long.parseLong(param);
		} else if (type.equals(UUID.class)) {
			return UUID.fromString(param);
		} else if (type.equals(Boolean.class)) {
			return Boolean.parseBoolean(param);
		}
		return type;
	}
}
