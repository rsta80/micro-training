package org.rastap.microtraining.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(max = 150, message = "Name too long")
    private String author;

    @NotNull
    private String post;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(onMethod_ = @JsonIgnore)
    private User user;

}
