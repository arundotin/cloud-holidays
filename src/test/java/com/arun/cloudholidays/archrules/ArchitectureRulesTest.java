package com.arun.cloudholidays.archrules;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

public class ArchitectureRulesTest {

    @Test
    public void classes_out_of_flights_package_should_not_access_flights() {
        JavaClasses cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var flightsPackageRules = classes()
                .that().resideInAPackage("..flights..")
                .should().onlyBeAccessed().byAnyPackage("..flights..");

        flightsPackageRules.check(cloudHolidaysClasses);
    }

    @Test
    public void classes_out_of_hotels_package_should_not_access_hotels() {
        JavaClasses cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var hotelsPackagesRules = classes()
                .that().resideInAPackage("..hotels..")
                .should().onlyBeAccessed().byAnyPackage("..hotels..");

        hotelsPackagesRules.check(cloudHolidaysClasses);
    }

    @Test
    public void service_classes_should_be_annotated_as_Service() {
        JavaClasses cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var serviceAnnotationRules = classes()
                .that()
                .haveSimpleNameEndingWith("Service")
                .should().beAnnotatedWith(Service.class);

        serviceAnnotationRules.check(cloudHolidaysClasses);


    }

    @Test
    public void prevent_usageOf_Autowired_annotation_in_fields() {
        JavaClasses cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var preventFieldInjectionRules = noFields()
                .should().beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired");

        preventFieldInjectionRules.check(cloudHolidaysClasses);

    }

    @Test
    public void api_objects_should_only_be_records_for_immutability() {
        JavaClasses cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var immutableApiObjectRules = classes().that().resideInAPackage("..api..").should().beRecords();

        immutableApiObjectRules.check(cloudHolidaysClasses);

    }


}
