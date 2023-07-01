package com.kevin.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {
	public static final String PARAM_KEY = "param";

	public static final String REG_EXP_KEY = "regexp";

	public MyRoutePredicateFactory() {
		super(Config.class);
	}

	/**
	 * Returns hints about the number of args and the order for shortcut parsing.
	 *
	 * @return the list of hints
	 */
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList(PARAM_KEY, REG_EXP_KEY);
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Config {
		private String param;
		private String regexp;
	}

	@Override
	public Predicate<ServerWebExchange> apply(Config config) {
		return new GatewayPredicate() {
			@Override
			public boolean test(ServerWebExchange serverWebExchange) {
				return false;
			}
		};
	}
}
