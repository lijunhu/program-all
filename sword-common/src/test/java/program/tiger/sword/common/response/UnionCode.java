package program.tiger.sword.common.response;

/**
 * @author junhu.li
 * @ClassName UnionCode
 * @Description TODO
 * @date 2019-08-0615:14
 * @Version 1.0.0
 */
public class UnionCode {


    public static void main(String[] args) {


        String unionCode = "";
    }


    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

}
