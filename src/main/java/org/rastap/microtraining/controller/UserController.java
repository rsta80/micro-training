package org.rastap.microtraining.controller;

import lombok.extern.slf4j.Slf4j;
import org.rastap.microtraining.exceptions.ObjectNotFoundException;
import org.rastap.microtraining.model.Post;
import org.rastap.microtraining.model.User;
import org.rastap.microtraining.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/hateoas")
    public ResponseEntity<?> findAllHateoas() {

        List<EntityModel<User>> users = userService.findAll().stream()
                .map(user -> new EntityModel<>(user, linkTo(methodOn(this.getClass()).findById(user.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(users);

    }

    /**
     * Basic method findById
     *
     * @param id User identifier
     * @return User Entity
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        log.info("Calling in-memory user by its id");
        return userService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User " + id + " not found"));
    }

    /**
     * HATEOAS method adding hyperlink to all users
     *
     * @param id User identifier
     * @return User Entity
     */
    @GetMapping("/hateoas/{id}")
    public EntityModel<User> findByIdHateoas(@PathVariable Integer id) {
        User u = userService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User " + id + " not found"));

        Link link = linkTo(methodOn(this.getClass()).findAll()).withSelfRel();
        return new EntityModel<>(u, link);
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody User u) {
        User savedUser = userService.save(u);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public List<User> replaceUser(@Valid @RequestBody User u) {
        return userService.update(u);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        User u = userService.findById(id).orElseThrow(() -> new ObjectNotFoundException("User: " + id + " does not exist"));
        userService.delete(u);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/posts")
    public List<Post> getPosts(@PathVariable Integer id) {

        User u = userService.findById(id).orElseThrow(() -> new ObjectNotFoundException("User: " + id + " does not exist"));
        return u.getPosts();

    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Object> savePosts(@Valid @RequestBody Post p, @PathVariable int id) {

        User u = userService.findById(id).orElseThrow(() -> new ObjectNotFoundException("User " + id + " not found"));
        Post savedPost = userService.savePost(u, p);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/posts")
                .buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/posts/{post-id}")
    public Post getPosts(@PathVariable Integer id, @PathVariable("post-id") Integer postId) {

        return userService.findPostById(this.findById(id), postId)
                .orElseThrow(() -> new ObjectNotFoundException("Post " + id + " not found"));

    }


}
