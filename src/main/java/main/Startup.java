package main;

import main.beans.*;
import main.dao.ConversationDao;
import main.dao.MessageDao;
import main.dao.TagDao;
import main.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class Startup {

    public static final Logger log = LoggerFactory.getLogger(Startup.class);

    @Autowired
    public UserDao userDao;

    @Autowired
    public TagDao tagDao;

    @Autowired
    public ConversationDao conversationDao;

    @Autowired
    public MessageDao messageDao;

    @PostConstruct
    public void onStart() {

        System.out.println("STARTUPPP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("STARTUPPP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        Tag massage = new Tag("massage", "masajiztit");
        Tag electrician = new Tag("Electrician", "hashmelai");
        Tag computerTechnician = new Tag("ComputerTechnician", "tehnai");

        List<Tag> masseuseTags = Arrays.asList(
                new Tag("House Maid", "Ozeret bait"),
                new Tag("Cook", "Tabah"),
                massage
        );

        List<Tag> electTags = Arrays.asList(
                new Tag("Private Instructor", "NaN"),
                new Tag("Personal Trainer", "NaN"),
                new Tag("Cleaner", "Batman"),
                electrician
        );

        tagDao.saveAll(masseuseTags);
        tagDao.saveAll(electTags);

        tagDao.save(computerTechnician);

        User user = new User("username", "password", "sd@asd.co", "Yura Kourlyand");
        user.profileImagePath = "/img/pepe.png";
        user.summary = "Hello world!";
        user.tags = Arrays.asList(computerTechnician);
        user.requiredTags = Arrays.asList(computerTechnician);
        user.address = new Address(GeneralArea.Hadarom,"Merkaz");


        User masseuse = new User("masseuse", "pass", "hook-up@gmail.com", "Jana Bana");
        masseuse.tags = masseuseTags;
        masseuse.requiredTags = Arrays.asList(electrician);
        masseuse.profileImagePath = "/img/pepe.png";

        masseuse.address = new Address(GeneralArea.Hadarom,"Merkaz");


        User electricianUser = new User("matt332", "pass2022", "mat@google.microsoft", "Boring man");
        electricianUser.tags = electTags;
        electricianUser.requiredTags = Arrays.asList(massage, computerTechnician);
        electricianUser.profileImagePath = "/img/pepe.png";
        electricianUser.address = new Address(GeneralArea.Hadarom,"Merkaz");

        userDao.save(user);
        userDao.save(masseuse);
        userDao.save(electricianUser);

        System.err.println("user id: " + user.id + " masseuse id: " + masseuse.id + " electrician id: " + electricianUser.id);

    }
}
