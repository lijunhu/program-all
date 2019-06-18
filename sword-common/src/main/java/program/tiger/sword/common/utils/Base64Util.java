package program.tiger.sword.common.utils;

/**
 * @author junhu.li
 * @ClassName Base64Util
 * @Description TODO
 * @date 2019-06-1716:14
 * @Version 1.0.0
 */
public class Base64Util {

    private static final char[] S_BASE64CHAR = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };


    public static String encode(byte[] data)
    {
        return encode(data, 0, data.length);
    }

    public static String encode(byte[] data, int off, int len) {
        if (len <= 0)
            return "";
        char[] out = new char[len / 3 * 4 + 4];
        int rindex = off;
        int windex = 0;
        int rest = len;
        while (rest >= 3) {
            int i = ((data[rindex] & 0xFF) << 16) + ((data[(rindex + 1)] & 0xFF) << 8) + (data[(rindex + 2)] & 0xFF);

            out[(windex++)] = S_BASE64CHAR[(i >> 18)];
            out[(windex++)] = S_BASE64CHAR[(i >> 12 & 0x3F)];
            out[(windex++)] = S_BASE64CHAR[(i >> 6 & 0x3F)];
            out[(windex++)] = S_BASE64CHAR[(i & 0x3F)];
            rindex += 3;
            rest -= 3;
        }
        if (rest == 1) {
            int i = data[rindex] & 0xFF;
            out[(windex++)] = S_BASE64CHAR[(i >> 2)];
            out[(windex++)] = S_BASE64CHAR[(i << 4 & 0x3F)];
            out[(windex++)] = '=';
            out[(windex++)] = '=';
        } else if (rest == 2) {
            int i = ((data[rindex] & 0xFF) << 8) + (data[(rindex + 1)] & 0xFF);
            out[(windex++)] = S_BASE64CHAR[(i >> 10)];
            out[(windex++)] = S_BASE64CHAR[(i >> 4 & 0x3F)];
            out[(windex++)] = S_BASE64CHAR[(i << 2 & 0x3F)];
            out[(windex++)] = '=';
        }
        return new String(out, 0, windex);
    }
}
