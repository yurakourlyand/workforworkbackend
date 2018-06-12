package main.dao;

import main.beans.Conversation;
import main.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yura Kourlyand
 */
@Repository
public interface ConversationDao extends CrudRepository<Conversation, Long> {
//    List<Conversation> findConversationsByUserAId(long id);
//    List<Conversation> findConversationsByUserBId(long id);
    List<Conversation> findConversationsByUserAIdOrUserBId(long userAId, long userBId);
    Conversation findConversationByUserAIdAndUserBId(long userAId, long userBId);
}
