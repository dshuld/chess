package serviceTests;

import org.junit.jupiter.api.*;
import request.RegisterRequest;
import service.*;

public class RegisterServiceTests {
    @BeforeEach
    public void setup() {
        ClearService clearService = ClearService.getInstance();
        clearService.clear();
    }

    @Test
    public void testLogoutPositive() {
        RegisterService registerService = RegisterService.getInstance();
        RegisterRequest registerRequest = new RegisterRequest("user","pass","email");
        Assertions.assertNull(registerService.register(registerRequest).message());
    }

    @Test
    public void testLogoutNegative() {
        RegisterService registerService = RegisterService.getInstance();
        RegisterRequest registerRequest = new RegisterRequest(null,null,null);
        Assertions.assertNotNull(registerService.register(registerRequest).message());
    }
}
