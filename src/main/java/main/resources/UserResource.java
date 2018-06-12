package main.resources;

import main.beans.Conversation;
import main.beans.Message;
import main.beans.Tag;
import main.beans.User;
import main.dao.ConversationDao;
import main.dao.MessageDao;
import main.dao.TagDao;
import main.dao.UserDao;
import main.resources.dto.MessageDTO;
import main.resources.dto.UserCredentials;
import main.validators.signUpValidation.ValidationCheckList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class UserResource {

    @Autowired
    public MessageDao messageDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public TagDao tagDao;

    @Autowired
    public ConversationDao conDao;

    @Autowired
    public WebSocketResource webSocketResource;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<User> get(@PathVariable Long id) {
        User user = userDao.findUserById(id);
        user.conversations = null;
        return ResponseEntity.ok(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private User getLoggedUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.lastTimeOnline = new Date();
            return userDao.findUserById(user.id);
        }
        return null;
    }


    private ResponseEntity unauthorizedStatus() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied, Please log in!");
    }


    @RequestMapping(path = "/getConversations", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getConversationsOfUser(HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return unauthorizedStatus();
        return ResponseEntity.ok(conDao.findConversationsByUserAIdOrUserBId(user.id, user.id));
    }


    @Transactional
    @RequestMapping(path = "/sendMessage/{userId}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity postMessage(HttpSession session, @RequestBody MessageDTO message, @PathVariable long userId) {
        User user = getLoggedUser(session);
        User receivingUser = userDao.findUserById(userId);
        if (user == null) return unauthorizedStatus();
        if (receivingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Target user was not found");
        }
        Conversation conversation;
        Conversation con1 = conDao.findConversationByUserAIdAndUserBId(user.id, receivingUser.id);
        Conversation con2 = conDao.findConversationByUserAIdAndUserBId(receivingUser.id, user.id);
        conversation = (con1 != null) ? con1 : con2;

        if (conversation == null) {
            conversation = conDao.save(new Conversation(user.id, receivingUser.id));
            user.conversations.add(conversation);
            receivingUser.conversations.add(conversation);
            userDao.save(user);
            userDao.save(receivingUser);
            String destination = "/topic/user/" + conversation.userAId;
            String destination2 = "/topic/user/" + conversation.userBId;
            messagingTemplate.convertAndSend(destination, conversation);
            messagingTemplate.convertAndSend(destination2, conversation);
        }
        if (!StringUtils.isEmpty(message.message)) {
            Message message1 = new Message(message.message, conversation, user.id);
            messageDao.save(message1);
            conversation.messages.add(message1);
            conDao.save(conversation);
            String destination = "/topic/user/" + message1.conversation.userAId;
            String destination2 = "/topic/user/" + message1.conversation.userBId;
            messagingTemplate.convertAndSend(destination, message1);
            messagingTemplate.convertAndSend(destination2, message1);
            return ResponseEntity.ok(message1);
        }
        return ResponseEntity.ok(conversation);
    }


    @RequestMapping(path = "/findUserMatches", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getUserMatches(HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return unauthorizedStatus();
        List<User> result = userDao.findDistinctByTagsInAndRequiredTagsIn(user.requiredTags, user.tags);
        result = result.stream()
                .filter(u -> !u.id.equals(user.id))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<User> login(HttpSession session, @RequestBody UserCredentials credentials) {
        if (credentials == null
                || credentials.password == null
                || credentials.username == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = userDao.findUserByUserName(credentials.username);
        if (user == null) return ResponseEntity.notFound().build();
        if (passwordEncoder.matches(credentials.password, user.getPassword()) || user.getPassword().equals(credentials.password)) {
            user.lastTimeOnline = new Date();
            userDao.save(user);
            session.setAttribute("user", user);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }


    @RequestMapping(path = "isLoggedIn", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity isLoggedIn(HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) {
            return unauthorizedStatus();
        } else {
            return ResponseEntity.ok(user);
        }
    }


    @Transactional
    @RequestMapping(path = "/removeTag/{tagId}/{isMyTag}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity removeTag(HttpSession session, @PathVariable Long tagId, @PathVariable boolean isMyTag) {
        User user = getLoggedUser(session);
        if (user == null) return unauthorizedStatus();
        Tag tag = tagDao.findTagById(tagId);
        if (tag == null) return ResponseEntity.notFound().build();
        if (isMyTag) user.tags.remove(tag);
        else user.requiredTags.remove(tag);
        userDao.save(user);
        return ResponseEntity.ok(user);
    }


    @Transactional
    @RequestMapping(path = "/addTag/{tagId}/{isMyTag}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity addTag(HttpSession session, @PathVariable Long tagId, @PathVariable boolean isMyTag) {
        User user = getLoggedUser(session);
        if (user == null) return unauthorizedStatus();
        Tag tag = tagDao.findTagById(tagId);
        if (tag == null) return ResponseEntity.notFound().build();
        if (isMyTag) {
            if (user.tags.contains(tag))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already have this tag");
            else {
                user.tags.add(tag);
            }
        } else {
            if (user.requiredTags.contains(tag))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already have this tag");
            else {
                user.requiredTags.add(tag);
            }
        }
        userDao.save(user);
        return ResponseEntity.ok(user);
    }


    @RequestMapping(path = "/tags", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getAllTags() {
        return ResponseEntity.ok(tagDao.findAll());
    }


    @Transactional
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity signUp(HttpSession session, @RequestBody User user) {
        try {
            ValidationCheckList.validateClientFields(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        user.dateJoined = new Date();
        user.id = null;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.err.println("Password after hash: " + user.getPassword());
        userDao.save(user);
        return login(session, new UserCredentials(user.userName, user.getPassword()));
    }
}
