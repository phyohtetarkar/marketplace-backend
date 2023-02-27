package com.shoppingcenter.app.common;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingcenter.domain.common.HTMLStringSanitizer;

@Component
public class OWASPHTMLSanitizer implements HTMLStringSanitizer {

    @Autowired
    private PolicyFactory policyFactory;

    @Override
    public String sanitize(String html) {
        return policyFactory.sanitize(html);
    }

}
