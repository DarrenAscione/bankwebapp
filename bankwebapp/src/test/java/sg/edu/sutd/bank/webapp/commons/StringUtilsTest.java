
import org.junit.Before;
import org.junit.Test;
import sg.edu.sutd.bank.webapp.commons.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void join_empty() {
        List<String> empty = new ArrayList<String>();
        String separator = "";
        assertEquals(StringUtils.join(empty, separator), "");
    }

    @Test
    public void join_not_empty() {
        List<String> supplierNames = Arrays.asList("sup1", "sup2", "sup3");
        String separator = " ";
        assertEquals(StringUtils.join(supplierNames, separator), "sup1 sup2 sup3");
    }
}