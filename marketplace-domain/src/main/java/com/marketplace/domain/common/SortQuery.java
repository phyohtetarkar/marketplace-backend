package com.marketplace.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortQuery {

	public enum Direction {
		ASC, DESC
	}

	private String[] properties;

	private Direction direction;

	private SortQuery() {
	}

	public static SortQuery asc(String... properties) {
		return of(Direction.ASC, properties);
	}

	public static SortQuery desc(String... properties) {
		return of(Direction.DESC, properties);
	}

	public static SortQuery of(Direction direction, String... properties) {
		var query = new SortQuery();
		query.setDirection(direction);
		query.setProperties(properties == null || properties.length == 0 ? new String[] { "createdAt" } : properties);
		return query;
	}

}
