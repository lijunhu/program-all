package program.tiger.sword.file;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author junhu.li
 * @ClassName ParseTlbConfig
 * @Description TODO
 * @date 2019/12/1118:16
 * @Version 1.0.0
 */
public class ParseTlbConfig {


    @Test
    public void ParseConfig() throws Exception {
        List<String> location = Lists.newArrayList();
        String filePath = "";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String lineText;
        while (!(lineText = br.readLine()).equals("\r\n")) {
            if (locationStart(lineText)) {
                location.add(lineText);
            }


        }


    }

    public static boolean locationStart(String lineText) {
        return false;
    }

    public static boolean locationEnd(String lineTest) {
        return false;
    }
}

