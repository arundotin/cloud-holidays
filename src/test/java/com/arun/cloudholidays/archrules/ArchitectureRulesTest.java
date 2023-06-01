package com.arun.cloudholidays.archrules;


import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.properties.HasOwner.Predicates.With;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.core.domain.JavaFieldAccess.AccessType.SET;
import static com.tngtech.archunit.core.domain.JavaFieldAccess.Predicates.accessType;
import static com.tngtech.archunit.core.domain.JavaFieldAccess.Predicates.target;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;

public class ArchitectureRulesTest {


    @Test
    public void classes_out_of_flights_package_should_not_access_flights() {
        var cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var flightsPackageRules = classes()
                .that().resideInAPackage("..flights..")
                .should().onlyBeAccessed().byAnyPackage("..flights..");

        flightsPackageRules.check(cloudHolidaysClasses);
    }

    @Test
    public void classes_out_of_hotels_package_should_not_access_hotels() {
        var cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var hotelsPackagesRules = classes()
                .that().resideInAPackage("..hotels..")
                .should().onlyBeAccessed().byAnyPackage("..hotels..");

        hotelsPackagesRules.check(cloudHolidaysClasses);
    }

    @Test
    public void service_classes_should_be_annotated_as_Service() {
        var cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var serviceAnnotationRules = classes()
                .that()
                .haveSimpleNameEndingWith("Service")
                .should().beAnnotatedWith(Service.class);

        serviceAnnotationRules.check(cloudHolidaysClasses);


    }

    @Test
    public void prevent_usageOf_Autowired_annotation_in_fields() {
        var cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var preventFieldInjectionRules = noFields()
                .should().beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired");

        preventFieldInjectionRules.check(cloudHolidaysClasses);

    }

    @Test
    public void api_objects_should_only_be_records_for_immutability() {
        var cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        var immutableApiObjectRules = classes().that().resideInAPackage("..api..").should().beRecords();

        immutableApiObjectRules.check(cloudHolidaysClasses);

    }

    @Test
    public void methods_of_Spring_Beans_should_not_change_State() {
        var cloudHolidaysClasses = new ClassFileImporter().importPackages("com.arun.cloudholidays");

        methods().that()
                .areDeclaredInClassesThat().areAnnotatedWith(Service.class)
                .should(notHaveMethodsThatWriteOnInstanceVariables())
                .check(cloudHolidaysClasses);


    }

    private ArchCondition<JavaMethod> notHaveMethodsThatWriteOnInstanceVariables() {
        return new ArchCondition<>("should not write a field of @Service class") {
            @Override
            public void check(JavaMethod javaMethod, ConditionEvents conditionEvents) {
                javaMethod.getFieldAccesses().stream()
                        .filter(accessType(SET))
                        //.filter(target(With.owner(annotatedWith(Service.class))))
                        .forEach(access -> conditionEvents.add(violated(access, access.getDescription())));
            }


        };

    }


}
