package main.dao;

import main.beans.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yura Kourlyand
 */

@Repository
public interface MessageDao extends CrudRepository<Message, Long> {
}
