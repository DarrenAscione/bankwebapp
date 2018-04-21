package sg.edu.sutd.bank.webapp.model;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;


public class TransactionStatusTest {

    @Test
    public void of_NULL() {
        TransactionStatus value = TransactionStatus.of(null);
        assertEquals(value, null);
    }

    @Test
    public void of_APPROVED() {
        TransactionStatus value = TransactionStatus.of("APPROVED");
        assertEquals(value, TransactionStatus.APPROVED);
    }

    @Test
    public void of_DECLINED() {
        TransactionStatus value = TransactionStatus.of("DECLINED");
        assertEquals(value, TransactionStatus.DECLINED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void of_Error() {
        TransactionStatus value = TransactionStatus.of("Error");
    }
}