package com.aetrion.actioncontroller;

import com.aetrion.activesupport.Options;

import java.util.Map;
import java.util.Collection;

/**
 * ActionController options.
 *
 * @author Anthony Eden
 */
public class ACOptions extends Options {
    public String contentType;
    public String text;
    public Object status;
    public Object file;
    public boolean useFullPath;
    public Map locals;
    public String template;
    public String inline;
    public String action;
    public String xml;
    public String type;
    public Object partial;
    public String update;
    public Boolean nothing;
    public Boolean layout;
    public Collection collection;
    public String spacerTemplate;
    public Object object;

    public ACOptions(Object... args) {
        super(args);
    }
}
