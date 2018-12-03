package io.pivotal.camelboot.config;

import org.apache.camel.Exchange;
import org.apache.camel.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Iterator;
import java.util.Set;





public class Validator{


    private static javax.validation.Validator executableValidator;

    public void validate(Exchange exchange) throws ValidationException {

        Object body = exchange.getIn().getBody();

        executableValidator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violations = executableValidator.validate(body);

        if  (violations.size() != 0){
            Iterator iter = violations.iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }

        }

    }
}

