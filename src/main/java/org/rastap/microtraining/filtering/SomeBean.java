package org.rastap.microtraining.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * There are two options of static filtering, via JsonIgnore, or JsonIgnoreProperties, it can be used if its necessary not
 * to send a concern Field to the client. Example Field3
 * It's better not to use JsonIgnoreProperties 'cause it hardcode the coding, for example if the name of variable change
 */
@Data
@AllArgsConstructor
//Static filtering
@JsonIgnoreProperties(value = {"field1"})
//dynamic filtering
//@JsonFilter("SomeBeanFilter")
public class SomeBean {

    private String field1;
    private String field2;
    @Getter(onMethod_ = @JsonIgnore)
    private String field3;

}
