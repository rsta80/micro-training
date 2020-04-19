package org.rastap.microtraining.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NonNull
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "All details related to users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(max = 150, message = "The max length should be 150 chars")
    private String name;

    @NotNull(message = "The date must not be empty")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
