package string.reg;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {


    @Test
    public void  test(){
        String numStr = "aaaa11.234bbbb";

        Matcher matcher = Pattern.compile("\\d+\\.?\\d+").matcher(numStr);

        if (matcher.find()){
            String group = matcher.group();
        }

    }


}
