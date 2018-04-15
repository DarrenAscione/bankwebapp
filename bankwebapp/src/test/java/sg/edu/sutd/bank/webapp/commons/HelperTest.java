import org.junit.Test;
import sg.edu.sutd.bank.webapp.commons.Helper;

import static org.junit.Assert.*;

public class HelperTest {

    @Test
    public void input_normalizer() {
        String input = "dfd¥";
        String output = Helper.input_normalizer(input);
        assertEquals(output, "dfd");
    }

    @Test
    public void xss_match() {
        assertTrue(Helper.xss_match("<script>!!</script>"));
        assertFalse(Helper.xss_match("astring"));
    }

}