package com.aetrion.activesupport;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 */
public class StringsTest extends TestCase {

    private static final Map<String, String> singularToPlural = new HashMap<String, String>();
    private static final Map<String, String> mixtureToTitleCase = new HashMap<String, String>();
    private static final Map<String, String> camelToUnderscore = new HashMap<String, String>();
    private static final Map<String, String> camelToUnderscoreWithoutReverse = new HashMap<String, String>();
    private static final Map<String, String> camelWithPackageToUnderscoreWithSlash = new HashMap<String, String>();
    private static final Map<String, String> classNameToForeignKeyWithUnderscore = new HashMap<String, String>();
    private static final Map<String, String> classNameToTableName = new HashMap<String, String>();
    private static final Map<String, String> underscoreToHuman = new HashMap<String, String>();
    private static final Map<String, String> ordinalNumbers = new HashMap<String, String>();
    private static final Map<String, String> underscoresToDashes = new HashMap<String, String>();
    private static final Map<String, String> underscoreToLowerCamel = new HashMap<String, String>();

    static {

        singularToPlural.put("search", "searches");
        singularToPlural.put("switch", "switches");
        singularToPlural.put("fix", "fixes");
        singularToPlural.put("box", "boxes");
        singularToPlural.put("process", "processes");
        singularToPlural.put("address", "addresses");
        singularToPlural.put("case", "cases");
        singularToPlural.put("stack", "stacks");
        singularToPlural.put("wish", "wishes");
        singularToPlural.put("fish", "fish");

        singularToPlural.put("category", "categories");
        singularToPlural.put("query", "queries");
        singularToPlural.put("ability", "abilities");
        singularToPlural.put("agency", "agencies");
        singularToPlural.put("movie", "movies");
        singularToPlural.put("company", "companies");

        singularToPlural.put("archive", "archives");

        singularToPlural.put("index", "indices");

        singularToPlural.put("wife", "wives");
        singularToPlural.put("safe", "saves");
        singularToPlural.put("half", "halves");

        singularToPlural.put("move", "moves");

        singularToPlural.put("salesperson", "salespeople");
        singularToPlural.put("person", "people");

        singularToPlural.put("spokesman", "spokesmen");
        singularToPlural.put("man", "men");
        singularToPlural.put("woman", "women");

        singularToPlural.put("basis", "bases");
        singularToPlural.put("diagnosis", "diagnoses");

        singularToPlural.put("datum", "data");
        singularToPlural.put("medium", "media");
        singularToPlural.put("analysis", "analyses");

        singularToPlural.put("node_child", "node_children");
        singularToPlural.put("child", "children");

        singularToPlural.put("experience", "experiences");
        singularToPlural.put("day", "days");

        singularToPlural.put("comment", "comments");
        singularToPlural.put("foobar", "foobars");
        singularToPlural.put("newsletter", "newsletters");

        singularToPlural.put("old_news", "old_news");
        singularToPlural.put("news", "news");

        singularToPlural.put("series", "series");
        singularToPlural.put("species", "species");

        singularToPlural.put("quiz", "quizzes");

        singularToPlural.put("perspective", "perspectives");

        singularToPlural.put("ox", "oxen");
        singularToPlural.put("photo", "photos");
        singularToPlural.put("buffalo", "buffaloes");
        singularToPlural.put("tomato", "tomatoes");
        singularToPlural.put("dwarf", "dwarves");
        singularToPlural.put("elf", "elves");
        singularToPlural.put("information", "information");
        singularToPlural.put("equipment", "equipment");
        singularToPlural.put("bus", "buses");
        singularToPlural.put("status", "statuses");
        singularToPlural.put("status_code", "status_codes");
        singularToPlural.put("mouse", "mice");

        singularToPlural.put("louse", "lice");
        singularToPlural.put("house", "houses");
        singularToPlural.put("octopus", "octopi");
        singularToPlural.put("virus", "viri");
        singularToPlural.put("alias", "aliases");
        singularToPlural.put("portfolio", "portfolios");

        singularToPlural.put("vertex", "vertices");
        singularToPlural.put("matrix", "matrices");

        singularToPlural.put("axis", "axes");
        singularToPlural.put("testis", "testes");
        singularToPlural.put("crisis", "crises");

        singularToPlural.put("rice", "rice");
        singularToPlural.put("shoe", "shoes");

        singularToPlural.put("horse", "horses");
        singularToPlural.put("prize", "prizes");
        singularToPlural.put("edge", "edges");

        mixtureToTitleCase.put("active_record", "Active Record");
        mixtureToTitleCase.put("ActiveRecord", "Active Record");
        mixtureToTitleCase.put("action web service", "Action Web Service");
        mixtureToTitleCase.put("Action Web Service", "Action Web Service");
        mixtureToTitleCase.put("Action web service", "Action Web Service");
        mixtureToTitleCase.put("actionwebservice", "Actionwebservice");
        mixtureToTitleCase.put("Actionwebservice", "Actionwebservice");

        camelToUnderscore.put("Product", "product");
        camelToUnderscore.put("SpecialGuest", "special_guest");
        camelToUnderscore.put("ApplicationController", "application_controller");
        camelToUnderscore.put("Area51Controller", "area51_controller");
        camelToUnderscore.put("InnerClass$Test", "inner_class__test");

        camelToUnderscoreWithoutReverse.put("HTMLTidy", "html_tidy");
        camelToUnderscoreWithoutReverse.put("HTMLTidyGenerator", "html_tidy_generator");
        camelToUnderscoreWithoutReverse.put("FreeBSD", "free_bsd");
        camelToUnderscoreWithoutReverse.put("HTML", "html");

        camelWithPackageToUnderscoreWithSlash.put("admin.Product", "admin/product");
        camelWithPackageToUnderscoreWithSlash.put("users.commission.Department", "users/commission/department");
        camelWithPackageToUnderscoreWithSlash.put("usersSection.CommissionDepartment", "users_section/commission_department");

        classNameToForeignKeyWithUnderscore.put("Person", "person_id");
        classNameToForeignKeyWithUnderscore.put("application.billing.Account", "account_id");

        classNameToTableName.put("PrimarySpokesman", "primary_spokesmen");
        classNameToTableName.put("NodeChild", "node_children");

        underscoreToHuman.put("employee_salary", "Employee salary");
        underscoreToHuman.put("employee_id", "Employee");
        underscoreToHuman.put("underground", "Underground");

        ordinalNumbers.put("0", "0th");
        ordinalNumbers.put("1", "1st");
        ordinalNumbers.put("2", "2nd");
        ordinalNumbers.put("3", "3rd");
        ordinalNumbers.put("4", "4th");
        ordinalNumbers.put("5", "5th");
        ordinalNumbers.put("6", "6th");
        ordinalNumbers.put("7", "7th");
        ordinalNumbers.put("8", "8th");
        ordinalNumbers.put("9", "9th");
        ordinalNumbers.put("10", "10th");
        ordinalNumbers.put("11", "11th");
        ordinalNumbers.put("12", "12th");
        ordinalNumbers.put("13", "13th");
        ordinalNumbers.put("14", "14th");
        ordinalNumbers.put("20", "20th");
        ordinalNumbers.put("21", "21st");
        ordinalNumbers.put("22", "22nd");
        ordinalNumbers.put("23", "23rd");
        ordinalNumbers.put("24", "24th");
        ordinalNumbers.put("100", "100th");
        ordinalNumbers.put("101", "101st");
        ordinalNumbers.put("102", "102nd");
        ordinalNumbers.put("103", "103rd");
        ordinalNumbers.put("104", "104th");
        ordinalNumbers.put("110", "110th");
        ordinalNumbers.put("1000", "1000th");
        ordinalNumbers.put("1001", "1001st");

        underscoresToDashes.put("street", "street");
        underscoresToDashes.put("street_address", "street-address");
        underscoresToDashes.put("person_street_address", "person-street-address");

        underscoreToLowerCamel.put("product","product");
    underscoreToLowerCamel.put("special_guest","specialGuest");
    underscoreToLowerCamel.put("application_controller","applicationController");
    underscoreToLowerCamel.put("area51_controller","area51Controller");
    }

    public void testPluralizePlurals() {
        assertEquals("plurals", Strings.pluralize("plurals"));
        assertEquals("Plurals", Strings.pluralize("Plurals"));
    }

    public void testPluralize() {
        for (Map.Entry<String, String> entry : singularToPlural.entrySet()) {
            String singular = entry.getKey();
            String plural = entry.getValue();
            assertEquals(plural, Strings.pluralize(singular));
            assertEquals(Strings.capitalize(plural),
                    Strings.pluralize(Strings.capitalize(singular)));
        }
    }

    public void testSingularize() {
        for (Map.Entry<String, String> entry : singularToPlural.entrySet()) {
            String singular = entry.getKey();
            String plural = entry.getValue();
            assertEquals(singular, Strings.singularize(plural));
            assertEquals(Strings.capitalize(singular),
                    Strings.singularize(Strings.capitalize(plural)));
        }
    }

    public void testTitleize() {
        for (Map.Entry<String, String> entry : mixtureToTitleCase.entrySet()) {
            assertEquals(entry.getValue(), Strings.titleize(entry.getKey()));
        }
    }

    public void testCamelize() {
        for (Map.Entry<String, String> entry : camelToUnderscore.entrySet()) {
            assertEquals(entry.getKey(), Strings.camelize(entry.getValue()));
        }
    }

    public void testUnderscore() {
        for (Map.Entry<String, String> entry : camelToUnderscore.entrySet()) {
            assertEquals(entry.getValue(), Strings.underscore(entry.getKey()));
        }
        for (Map.Entry<String, String> entry : camelToUnderscoreWithoutReverse.entrySet()) {
            assertEquals(entry.getValue(), Strings.underscore(entry.getKey()));
        }
    }

    public void testCamelizeWithPackage() {
        for (Map.Entry<String, String> entry : camelWithPackageToUnderscoreWithSlash.entrySet()) {
            assertEquals(entry.getKey(), Strings.camelize(entry.getValue()));
        }
    }

    public void testUnderscoreWithSlashes() {
        for (Map.Entry<String, String> entry : camelWithPackageToUnderscoreWithSlash.entrySet()) {
            assertEquals(entry.getValue(), Strings.underscore(entry.getKey()));
        }
    }

    public void testDepackage() {
        assertEquals("Account", Strings.depackage("application.billing.Account"));
    }

    public void testForeignKey() {
        for (Map.Entry<String, String> entry : classNameToForeignKeyWithUnderscore.entrySet()) {
            assertEquals(entry.getValue(), Strings.foreignKey(entry.getKey()));
        }
    }

    public void testTableize() {
        for (Map.Entry<String, String> entry : classNameToTableName.entrySet()) {
            assertEquals(entry.getValue(), Strings.tableize(entry.getKey()));
        }
    }

    public void testClassify() {
        for (Map.Entry<String, String> entry : classNameToTableName.entrySet()) {
            assertEquals(entry.getKey(), Strings.classify(entry.getValue()));
        }
    }

    public void testHumanize() {
        for (Map.Entry<String, String> entry : underscoreToHuman.entrySet()) {
            assertEquals(entry.getValue(), Strings.humanize(entry.getKey()));
        }
    }

    public void testOrdinal() {
        for (Map.Entry<String, String> entry : ordinalNumbers.entrySet()) {
            assertEquals(entry.getValue(), Strings.ordinalize(entry.getKey()));
            assertEquals(entry.getValue(), Strings.ordinalize(Long.parseLong(entry.getKey())));
        }
    }

    public void testDasherize() {
        for (Map.Entry<String, String> entry : underscoresToDashes.entrySet()) {
            assertEquals(entry.getValue(), Strings.dasherize(entry.getKey()));
        }
    }

    public void testUnderscoredAsReverseOfDasherize() {
        for (Map.Entry<String, String> entry : underscoresToDashes.entrySet()) {
            assertEquals(entry.getKey(), Strings.underscore(Strings.dasherize(entry.getKey())));
        }
    }

    public void testUnderscoreToLowerCamel() {
        for (Map.Entry<String, String> entry : underscoreToLowerCamel.entrySet()) {
            assertEquals(entry.getValue(), Strings.camelize(entry.getKey(), true));
        }
    }

    // other tests

    public void testCapitalize() {
        assertEquals("Foo bar baz", Strings.capitalize("foo bar baz"));
    }

    public void testClassNameToTableName() {
        assertEquals("companies", Strings.tableize("com.aetrion.activerecord.fixture.Companies"));
    }

}
