package com.aetrion.actioncontroller;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 */
public class ActionControllerTest extends TestCase {

    public void testUrlForNoArgs() {
        ActionController controller = new TestController();
        controller.urlFor(null, null);
    }

    public void testUrlForWithController() {

    }

    public void testUrlForWithAction() {

    }

    public void testUrlForWithAllArgs() {
        ActionController controller = new TestController();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", 10);
        URL url = controller.urlFor("someController", "someAction", params);
        assertEquals("/some_controller/some_action/10", url.toString());
    }
}
