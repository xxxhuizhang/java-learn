package string.reg;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {


    @Test
    public void  test(){
        String numStr = "aaaa11.234bbbb";

        Matcher matcher = Pattern.compile("\\d+\\.?\\d+").matcher(numStr);

        /*

        1.  matcher.matches()
        Attempts to match the entire region against the pattern.

        2. matcher.find()
        Attempts to find the next subsequence of the input sequence that matches
        the pattern.

         */

        if (matcher.find()){
            String group = matcher.group();
        }

    }


}
