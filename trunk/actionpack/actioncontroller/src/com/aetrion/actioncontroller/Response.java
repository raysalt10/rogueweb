package com.aetrion.actioncontroller;

/**
 * Wraps {@link javax.servlet.http.HttpServletResponse}.
 *
 * @author Anthony Eden
 */
public class Response {

    private String body;

    public void setHeader(String key, String value) {

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
