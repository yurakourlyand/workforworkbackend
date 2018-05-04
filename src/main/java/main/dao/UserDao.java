package main.dao;

import main.beans.Tag;
import main.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findUserById(Long id);
    User findUserByUserName(String username);
    List<User> findDistinctByTagsInAndRequiredTagsIn(List<Tag> tags, List<Tag> required);
}
