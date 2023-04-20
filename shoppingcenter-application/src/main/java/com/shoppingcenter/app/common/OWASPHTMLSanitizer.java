package com.shoppingcenter.app.common;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Component;

import com.shoppingcenter.domain.common.HTMLStringSanitizer;

import jakarta.annotation.PostConstruct;

@Component
public class OWASPHTMLSanitizer implements HTMLStringSanitizer {

    private PolicyFactory policyFactory;
    
    @PostConstruct
    private void init() {
    	var custom = new HtmlPolicyBuilder().allowAttributes("target").onElements("a").toFactory();

		this.policyFactory = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS)
				.and(Sanitizers.IMAGES)
				.and(Sanitizers.LINKS)
				.and(Sanitizers.TABLES)
				.and(Sanitizers.STYLES)
				.and(custom);
    }

    @Override
    public String sanitize(String html) {
        return policyFactory.sanitize(html);
    }

}
