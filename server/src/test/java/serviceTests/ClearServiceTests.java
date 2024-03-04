package serviceTests;

import dataAccess.interfaces.UserDao;
import dataAccess.memory.MemoryUserDao;
import org.junit.jupiter.api.*;
import model.*;
import service.*;

import java.util.EmptyStackException;

public class ClearServiceTests {
    @Test
    public void testPositiveClear() {
        UserDao userDao = MemoryUserDao.getInstance();
        UserData userData = new UserData("user", "pass", "email");
        userDao.createUser(userData);

        Assertions.assertNotNull(userDao.getUser(userData));

        ClearService service = ClearService.getInstance();
        try {
            service.clear();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Assertions.assertNull(userDao.getUser(userData));
    }
}
