package com.aetrion.examples.controllers;

import com.aetrion.actioncontroller.ActionController;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.examples.models.Person;

import java.util.List;

/**
 * Example of a "Person" controller.
 *
 * @author Anthony Eden
 */
public class PersonController extends ActionController {

    public void index() {
        List people = ActiveRecords.findAll(Person.class, null);
        vars().put("people", people);
    }

    public void create() {
        Person person = new Person();
        vars().put("person", person);
        if (request.isPost()) {
            if (person.updateAttributes(getParameters())) {
                redirectTo(urlFor(null, "index"));
            }
        }
    }

    public void update() {
        Person person = new Person();
        vars().put("person", person);
        person.read(getParameters().get("id"));
        if (request.isPost()) {
            if (person.updateAttributes(getParameters())) {
                redirectTo(urlFor(null, "index"));
            }
        }
    }

    public void delete() {
        Person person = new Person();
        person.read(getParameters().get("id"));
        person.destroy();
        redirectTo(urlFor(null, "index"));
    }

}
