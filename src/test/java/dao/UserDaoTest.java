package dao;

import domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    @Test
    public void addAndGet(){
        UserDao userDao = new UserDao();
        userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.add(new User("1", "lulu", "cat"));
        User user = userDao.findById("1");
        assertEquals("lulu", user.getName());
    }

}