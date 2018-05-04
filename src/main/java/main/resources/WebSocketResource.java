package main.resources;

import main.beans.Message;
import main.dao.ConversationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Yura Kourlyand
 */


@Controller
@Service
public class WebSocketResource {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ConversationDao conDao;

//    @MessageMapping("/topic/conversation")
//    @ResponseBody
//    public void dualchat(Message message) {
//         forward message to destination
//        System.err.println(message + " LOOOK AT MEEE MA DUDE");
//        String destination = "/topic/user/" + message.conversation.userAId;
//        String destination2 = "/topic/user/" + message.conversation.userBId;
//        simpMessagingTemplate.convertAndSend(destination,message);
//        simpMessagingTemplate.convertAndSend(destination2,message);
//    }


}
