package sg.edu.sutd.bank.webapp.model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UserRoleTest {

    private UserRole userRole;

    @Before
    public void run() {
        userRole = new UserRole();
    }

    @Test
    public void getUser() throws NoSuchFieldException, IllegalAccessException {
        final Field field = userRole.getClass().getDeclaredField("user");
        field.setAccessible(true);
        field.set(userRole, new User());
        final User result = userRole.getUser();
        assertEquals(result.getId(), null);

    }

    @Test
    public void setUser() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        userRole.setUser(user);
        final Field field = userRole.getClass().getDeclaredField("user");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(userRole), user);


    }

    @Test
    public void getRole() throws NoSuchFieldException, IllegalAccessException {
        final Field field = userRole.getClass().getDeclaredField("role");
        field.setAccessible(true);
        field.set(userRole, Role.client);
        final Role result = userRole.getRole();
        assertEquals(result, Role.client);
    }

    @Test
    public void setRole() throws NoSuchFieldException, IllegalAccessException {
        userRole.setRole(Role.client);
        final Field field = userRole.getClass().getDeclaredField("role");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(userRole), Role.client);


    }
}