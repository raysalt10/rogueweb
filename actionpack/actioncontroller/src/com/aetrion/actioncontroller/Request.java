package com.aetrion.actioncontroller;

import java.util.Map;
import java.util.HashMap;

/**
 * Wraps {@link javax.servlet.http.HttpServletRequest}.
 * @author Anthony Eden
 */
public class Request {

    private Map<String, Object> parameters = new HashMap<String, Object>();
    private String method;

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isGet() {
        return method.equalsIgnoreCase("get");
    }

    public boolean isPost() {
        return method.equalsIgnoreCase("post");
    }


}
