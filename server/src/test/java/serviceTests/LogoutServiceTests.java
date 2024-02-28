package serviceTests;

import org.junit.jupiter.api.*;
import request.*;
import service.*;

public class LogoutServiceTests {
    private static String authToken;
    @BeforeAll
    public static void setup() {
        RegisterService registerService = RegisterService.getInstance();
        RegisterRequest registerRequest = new RegisterRequest("user", "pass", "email");
        authToken = registerService.register(registerRequest).authToken();
    }

    @Test
    public void testLogoutPositive() {
        LogoutService logoutService = LogoutService.getInstance();
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        Assertions.assertNull(logoutService.logout(logoutRequest).message());
    }

    @Test
    public void testLogoutNegative() {
        LogoutService logoutService = LogoutService.getInstance();
        LogoutRequest logoutRequest = new LogoutRequest("notAuth");
        Assertions.assertNotNull(logoutService.logout(logoutRequest).message());
    }
}
