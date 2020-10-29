package program.tiger.sword.http;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import program.tiger.sword.common.utils.Base64Util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author junhu.li
 * @ClassName Main
 * @Description TODO
 * @date 2020/6/917:45
 * @Version 1.0.0
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String data = "H4sIAAAAAAAAExWNUQrDMAxD7+LvURpcx1lvU1vRAUrZz9jd54CQeCChr+QjpzR5yV3pHB3hCvqgpgP9jWkXG0FDAJG0qxfp4ZHQAbWp0ZkL3Wk5W2XQV3vJprcsr23dfOrGtrHt8vsDktuwC30AAAA=";
        byte[] bytes = Base64.decode(data.getBytes());
        System.out.println(new String(bytes, Charset.forName("utf-8")));
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        GZIPInputStream gis = new GZIPInputStream(bis);
        String out = new BufferedReader(new InputStreamReader(gis))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(out);
    }
}
