package org.rastap.microtraining.controller;

import org.rastap.microtraining.exceptions.ObjectNotFoundException;
import org.rastap.microtraining.model.Post;
import org.rastap.microtraining.model.User;
import org.rastap.microtraining.service.PostRepository;
import org.rastap.microtraining.service.UserRepository;
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

@RestController
@RequestMapping("/jpa/users")
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {

        this.userRepository = userRepository;
        this.postRepository = postRepository;

    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/hateoas")
    public ResponseEntity<?> findAllHateoas() {

        List<EntityModel<User>> users = userRepository.findAll().stream()
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
        return userRepository.findById(id)
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

        User u = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User " + id + " not found"));
        Link link = linkTo(methodOn(this.getClass()).findAll()).withSelfRel();
        return new EntityModel<>(u, link);
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody User u) {
        User savedUser = userRepository.save(u);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<User> replaceUser(@Valid @RequestBody User u) {
        User updatedUser = userRepository.save(u);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/posts")
    public List<Post> getPosts(@PathVariable Integer id) {

        User u = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User: " + id + " does not exist"));
        return u.getPosts();

    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Object> savePosts(@Valid @RequestBody Post p, @PathVariable int id) {

        p.setUser(userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User " + id + " not found")));
        Post savedPost = postRepository.save(p);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/posts")
                .buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/posts/{post-id}")
    public Post getPosts(@PathVariable Integer id, @PathVariable("post-id") Integer postId) {

        userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User " + id + " not found"));
        return postRepository.findById(postId).orElseThrow(() -> new ObjectNotFoundException("Post " + id + " not found"));

    }


}
