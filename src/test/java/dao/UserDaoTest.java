package dao;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    private UserDao userDao;
    private User user1, user2, user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1", "Lulu", "brown");
        this.user2 = new User("2", "Momo", "ginger");
        this.user3 = new User("2", "Lala", "white");
    }

    @Test
    @DisplayName("Testing insert")
    public void addAndGet() throws SQLException {
        //UserDao userDao = new UserDao();
        //userDao = context.getBean("awsUserDao", UserDao.class);

        // Delete
        userDao.deleteAll();

        //check the number of rows
        assertEquals(0, userDao.getCount());

        // insert
        userDao.add(new User("1", "lulu", "cat"));
        User user = userDao.findById("1");
        assertEquals("lulu", user.getName()); // check name
        assertEquals(1, userDao.getCount()); // check count
    }

    @Test
    @DisplayName("testing getCount")
    void count() throws SQLException {
        //empty db
        userDao.deleteAll();

        // insert
        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
    }

}