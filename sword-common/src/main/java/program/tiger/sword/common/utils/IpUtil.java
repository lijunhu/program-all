package program.tiger.sword.common.utils;

public class IpUtil {
    /**
     *
     * @param ip
     * @return
     */
    public static long ipToLong(String ip) {
        ip = ip.trim();
        final String[] ipNums = ip.split("\\.");
        return (Long.parseLong(ipNums[0]) << 24)
                + (Long.parseLong(ipNums[1]) << 16)
                + (Long.parseLong(ipNums[2]) << 8)
                + (Long.parseLong(ipNums[3]));
    }
}
