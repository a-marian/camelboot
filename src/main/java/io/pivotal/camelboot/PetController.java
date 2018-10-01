package io.pivotal.camelboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class PetController {


    private static final String[] PETS = new String[]{"Snoopy", "Fido", "Tony the Tiger"};

    @GetMapping(value = "/pets/{id}")
    public Map<String, String> petById(@PathVariable("id") Integer id) {
        if (id != null && id > 0 && id <= PETS.length + 1) {
            int index = id - 1;
            String pet = PETS[index];
            return Collections.singletonMap("name", pet);
        } else {
            return Collections.emptyMap();
        }
    }
}
