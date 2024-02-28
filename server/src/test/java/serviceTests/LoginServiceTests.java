package serviceTests;

import org.junit.jupiter.api.*;
import request.LoginRequest;
import request.RegisterRequest;
import service.*;

public class LoginServiceTests {
    @BeforeAll
    public static void setup() {
        RegisterService registerService = RegisterService.getInstance();
        RegisterRequest registerRequest = new RegisterRequest("user", "pass", "email");
        registerService.register(registerRequest);
    }

    @Test
    public void testLoginPositive() {
        LoginService loginService = LoginService.getInstance();
        LoginRequest loginRequest = new LoginRequest("user", "pass");
        Assertions.assertNotNull(loginService.login(loginRequest).authToken());
    }

    @Test
    public void testLoginNegative() {
        LoginService loginService = LoginService.getInstance();
        LoginRequest loginRequest = new LoginRequest("user", "notPass");
        Assertions.assertNull(loginService.login(loginRequest).authToken());
    }
}
