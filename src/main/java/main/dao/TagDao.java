package main.dao;

import main.beans.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends CrudRepository<Tag, Long>{
    Tag findTagById(Long id);
}
