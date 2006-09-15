package com.aetrion.actioncontroller;

import com.aetrion.activesupport.Strings;
import com.aetrion.actioncontroller.error.DoubleRenderException;
import com.aetrion.actioncontroller.error.ActionControllerException;

import java.util.*;
import java.util.logging.Logger;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Base class for all ActionControllers.
 * @author Anthony Eden
 */
public abstract class ActionController {

    private static final String DEFAULT_RENDER_STATUS_CODE = "200 OK";

    protected String assetHost;
    protected Logger log = Logger.getLogger(getClass().getName());
    protected Request request;
    protected Response response;
    protected Session session;
    private Template template;
    private Map<String, Object> vars;
    private Map<String, String> headers;

    private String controllerClassName;
    private String controllerName;
    private String controllerPath;

    private List<String> hiddenActions = new ArrayList<String>();

    private URL url;
    private boolean sessionEnabled = true;
    private boolean performedRender = false;

    /**
     * Get the asset host.
     * @return The asset host
     */
    public String getAssetHost() {
        return assetHost;
    }

    /**
     * Set the asset host. For example, set to http://asset.yourcompany.com/ to retrieve assets from that host.
     * @param assetHost The asset host.
     */
    public void setAssetHost(String assetHost) {
        this.assetHost = assetHost;
    }

    /**
     * Get the controller class name. For example: my.webapp.FooController becomes FooController.
     * @return The controller class name
     */
    public String getControllerClassName() {
        if (controllerClassName == null) controllerClassName = Strings.depackage(getClass().getName());
        return controllerClassName;
    }

    /**
     * Get the controller name. For example: my.webapp.FooController becomes foo.
     * @return The controller name
     */
    public String getControllerName() {
        if (controllerName == null) {
            controllerName = Strings.underscore(getControllerClassName().replaceAll("Controller$", ""));
        }
        return controllerName;
    }

    /**
     * Get the controller path. For example: my.webapp.FooController becomes my/webapp/foo
     * @return The controller path
     */
    public String getControllerPath() {
        if (controllerPath == null) {
            controllerPath = Strings.underscore(getClass().getName().replaceAll("Controller$", ""));
        }
        return controllerPath;
    }

    /**
     * Get a List of hidden action names.
     * @return The list of hidden actions
     */
    public List<String> getHiddenActions() {
        return hiddenActions;
    }

    /**
     * Hide the named actions.
     * @param names The action names
     */
    public void hideActions(String... names) {
        for (String name : names) hiddenActions.add(name);
    }

    /**
     * Process the request.
     * @param request The request
     * @param response The response
     * @param method The method name
     * @param args The args
     */
    public void process(Request request, Response response, String method, Object... args) {
//        initialize_template_class(response)
//        assign_shortcuts(request, response)
//        initialize_current_url
//        assign_names
//        forget_variables_added_to_assigns

        logProcessing();
        send(method, args);
    }

    public boolean isSessionEnabled() {
        return sessionEnabled;
    }

    public void setSessionEnabled(boolean sessionEnabled) {
        this.sessionEnabled = sessionEnabled;
    }

    /**
     * Return true if the action has been performed.
     * @return True if th action has been peformed
     */
    protected boolean isPerformed() {
        return performedRender;
    }

    /**
     * Render.
     * @param options The options
     * @return The rendered text
     */
    protected String render(ACOptions options) {
        String result = null;
        if (isPerformed()) throw new DoubleRenderException("Can only render or redirect once per action");

        if (options.contentType != null) {
            headers.put("Content-Type", options.contentType);
        }

        if (options.text != null) {
            renderText(options.text, options.status);
        } else {
            if (options.file != null) {
                renderFile(options.file, options.status, options.useFullPath, options.locals);
            } else if (options.template != null) {
                renderFile(options.template, options.status, true);
            } else if (options.inline != null) {
                renderTemplate(options.inline, options.status, options.type, options.locals);
            } else if (options.action != null) {
                renderAction(options.action, options.status, options.layout);
            } else if (options.xml != null) {
                renderXML(options.xml, options.status);
            } else if (options.partial != null) {
                String partial = null;
                if ((options.partial instanceof Boolean) && ((Boolean) options.partial)) {
                    partial = getDefaultTemplateName();
                }
                if (options.collection != null) {
                    renderPartialCollection(partial, options.collection, options.spacerTemplate, options.locals,
                            options.status);
                } else {
                    renderPartial(partial, options.object, options.locals, options.status);
                }
            } else if (options.update != null) {
                // TODO implement
//            add_variables_to_assigns
//            @template.send :evaluate_assigns
//            generator = ActionView::Helpers::PrototypeHelper::JavaScriptGenerator.new(@template, &block)
//            render_javascript(generator.to_s)
            } else if (options.nothing != null) {
                // Safari doesn't pass the headers of the return if the response is zero length
                renderText(" ", options.status);
            } else {
                renderFile(getDefaultTemplateName(), options.status, true);
            }
        }
        return result;
    }

    public String renderToString(ACOptions options) {
        return render(options);

//        erase_render_results
//        forget_variables_added_to_assigns
//        reset_variables_added_to_assigns

    }

    String renderText(String text, Object status) {
        if (status == null) status = DEFAULT_RENDER_STATUS_CODE;

        performedRender = true;
        response.setHeader("Status", String.valueOf(status));
        response.setBody(text);

        return response.getBody();
    }

    String renderJavascript(String javascript, Object status) {
        response.setHeader("Content-Type", "text/javascript; charset=UTF-8");
        return renderText(javascript, status);
    }

    String renderFile(Object file, Object status, boolean useFullPath) {
        return renderFile(file, status, useFullPath, null);
    }

    String renderFile(Object file, Object status, boolean useFullPath, Map locals) {
        // TODO implement

//        def render_file(template_path, status = nil, use_full_path = false, locals = {}) #:nodoc:
//        add_variables_to_assigns
//        assert_existence_of_template_file(template_path) if use_full_path
//        logger.info("Rendering #{template_path}" + (status ? " (#{status})" : '')) if logger
//        render_text(@template.render_file(template_path, use_full_path, locals), status)
//      end

        return null;
    }

    String renderTemplate(String template, Object status, String type, Map locals) {
        // TODO implement
//        add_variables_to_assigns
        return renderText(getTemplate().renderTemplate(type, template, null, locals), status);
    }

    String renderAction(String actionName, Object status, boolean withLayout) {
        String template = getDefaultTemplateName(actionName);
        if (withLayout && !isTemplateExemptFromLayout(template)) {
            return renderWithLayout(template, status);
        } else {
            return renderWithoutLayout(template, status);
        }
    }

    String renderXML(String xml, Object status) {
        response.setHeader("Content-Type", "application/xml");
        return renderText(xml, status);
    }

    String renderPartial(String partial, Object object, Map locals, Object status) {
        // TODO implement
        return null;
    }

    String renderPartialCollection(String partial, Collection collection, String spacerTemplate, Map locals,
                                   Object status) {
        // TODO implement
        return null;
    }

    void logProcessing() {
        // TODO implement
    }

    boolean isTemplateExemptFromLayout(String template) {
        // TODO implement
        return false;
    }

    String renderWithLayout(String template, Object status) {
        // TODO implement
        return null;
    }

    String renderWithoutLayout(String template, Object stauts) {
        // TODO implement
        return null;
    }

    String getDefaultTemplateName() {
        return getDefaultTemplateName(null);
    }

    String getDefaultTemplateName(String actionName) {
        return null; // TODO implement
    }

    /**
     * Create a URL. If one of the arguments in the method is null then the value will be extracted from the request
     * URL.
     * @param controller The name of the controller (may be null)
     * @param action The name of the action (may be null)
     * @param parameters The parameters (may be null)
     * @return The URL
     */
    protected URL urlFor(String controller, String action, Object... parameters) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("controller", controller);
        options.put("action", action);
        options.put("parameters", parameters);
        url.rewrite(options);
        return url;
    }

    protected void redirectTo(URL url) {
        // TODO implement
    }

    protected void redirectTo(String url) {
        // TODO implement
    }

    /**
     * Get a Map of variables which are exposed to the view.
     * @return The vars map
     */
    protected Map<String, Object> vars() {
        if (vars == null) vars = new HashMap<String, Object>();
        return vars;
    }

    /**
     * Get the template object.
     * @return The Template object
     */
    protected Template getTemplate() {
        return template;
    }

    protected Map<String, Object> getParameters() {
        return request.getParameters(); // TODO implement
    }

    /**
     * Send to the method.
     * @param method The method name
     * @param args The args
     */
    void send(String method, Object... args) {
        try {
            Method m = getClass().getMethod(method);
            m.invoke(this, args);
        } catch (NoSuchMethodException e) {
            throw new ActionControllerException("Action " + method + " not found", e);
        } catch (IllegalAccessException e) {
            throw new ActionControllerException("Action " + method + " is inaccessible", e);
        } catch (InvocationTargetException e) {
            throw new ActionControllerException("Error invoking action " + method + ": " + e.getCause().getMessage());
        } finally {
            processCleanup();
        }
    }

    void processCleanup() {

    }

}
