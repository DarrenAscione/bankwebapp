package sg.edu.sutd.bank.webapp.model;

import org.junit.Test;
import sg.edu.sutd.bank.webapp.model.ClientAccount;
import sg.edu.sutd.bank.webapp.model.User;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ClientAccountTest {

    final ClientAccount clientAccount = new ClientAccount();

    @Test
    public void getUser() throws NoSuchFieldException, IllegalAccessException {
        final Field field = clientAccount.getClass().getDeclaredField("user");
        field.setAccessible(true);
        field.set(clientAccount, new User(12));
        final User result = clientAccount.getUser();
        final int id = result.getId();
        assertEquals("field wasn't retrieved properly", id, 12);

    }

    @Test
    public void setUser() throws NoSuchFieldException, IllegalAccessException {
        //when
        clientAccount.setUser(new User(12));

        //then
        final Field field = clientAccount.getClass().getDeclaredField("user");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(clientAccount), 12);
    }

    @Test
    public void getAmount() throws NoSuchFieldException, IllegalAccessException {
        final Field field = clientAccount.getClass().getDeclaredField("amount");
        field.setAccessible(true);
        field.set(clientAccount, new BigDecimal(24));
        final BigDecimal result = clientAccount.getAmount();
        assertEquals("field wasn't retrieved properly", result, new BigDecimal(24));
    }

    @Test
    public void setAmount() throws NoSuchFieldException, IllegalAccessException {
        //when
        clientAccount.setAmount(new BigDecimal(24));

        //then
        final Field field = clientAccount.getClass().getDeclaredField("amount");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(clientAccount), new BigDecimal(24));
    }
}