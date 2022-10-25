package dao;

import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    public void addAndGet(){
        UserDao userDao = new UserDao();
        userDao.add(new User("1", "lulu", "cat"));
        User user=userDao.findById("1");
        assertEquals("lulu", user.getName());
    }

}