package serviceTests;

import org.junit.jupiter.api.*;
import model.*;
import service.*;
import dataAccess.*;

public class ClearServiceTests {
    @Test
    public void testPositiveClear() {
        UserDao userDao = MemoryUserDao.getInstance();
        UserData userData = new UserData("user", "pass", "email");
        userDao.createUser(userData);

        Assertions.assertNotNull(userDao.getUser(userData));

        ClearService service = ClearService.getInstance();
        service.clear();

        Assertions.assertNull(userDao.getUser(userData));
    }
}
