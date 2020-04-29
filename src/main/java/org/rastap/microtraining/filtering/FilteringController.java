package org.rastap.microtraining.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Controller implement in order to see how works static and dynamic filtering
 * Static like JsonIgnore annotation
 * <p>
 * Dynamic filtering using Jackson libs in controller
 * <p>
 * A simple approach could be Java8 Stream filtering for lists
 */
@RestController
public class FilteringController {

    /**
     * Using Jackson dynamic filtering
     *
     * @return object mapping applying filtering property, provider and Jackson mapping for the object
     */
    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {

        SomeBean bean = new SomeBean("Value1", "Value2", "Value3");
        SimpleBeanPropertyFilter filterProperty = SimpleBeanPropertyFilter.filterOutAllExcept("field1");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBean", filterProperty);
        MappingJacksonValue mapping = new MappingJacksonValue(bean);
        mapping.setFilters(filters);

        return mapping;
        //return bean;
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> retrieveListBean() {
        return Arrays.asList(new SomeBean("Value11", "Value21", "Value31"), new SomeBean("Value21", "Value22", "Value32"));
    }

}
