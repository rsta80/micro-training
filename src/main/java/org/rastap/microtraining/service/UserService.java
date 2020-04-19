package org.rastap.microtraining.service;

import org.rastap.microtraining.model.Post;
import org.rastap.microtraining.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     List<User> findAll();

     Optional<User> findById(Integer id);

     User save(User u);

     List<User> update(User newU);

     void delete(User u);

     Post savePost(User u, Post p);

     Optional<Post> findPostById(User u, Integer id);


}
