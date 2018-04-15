package sg.edu.sutd.bank.webapp.commons;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static final long serialVersionUID = 1L;

    private Helper() {}

    public static String input_normalizer(String input) {
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);
        return input.replaceAll("[^\\p{ASCII}]", "");
    }

    public static Boolean xss_match(String input) {
        Pattern pattern = Pattern.compile("<script>");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }






}
