package org.rastap.microtraining.service.impl;

import org.rastap.microtraining.model.Post;
import org.rastap.microtraining.model.User;
import org.rastap.microtraining.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        users.add(new User(1, "Andres", LocalDate.now(), new ArrayList<>()));
        users.add(new User(2, "Nuria", LocalDate.now(), new ArrayList<>()));
        users.add(new User(3, "Julio", LocalDate.now(), new ArrayList<>()));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User u) {
        if (u.getId() == null) {
            u.setId(users.size() + 1);
        }
        users.add(u);
        return u;
    }

    @Override
    public List<User> update(User newU) {
        return users = users.stream().map(u -> u.getId().equals(newU.getId()) ? newU : u).collect(Collectors.toList());
    }

    @Override
    public void delete(User u) {
        users.remove(u);
    }

    @Override
    public Post savePost(User u, Post p) {

        List<Post> posts = u.getPosts();
        if (p.getId() == null) {
            p.setId(posts.size() + 1);
        }
        posts.add(p);
        return p;

    }

    @Override
    public Optional<Post> findPostById(User u, Integer id) {
        return u.getPosts().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

}
