package program.tiger.sword.common.respone;

import org.junit.Test;

/**
 * @author junhu.li
 * @ClassName TestRespone
 * @Description TODO
 * @date 2019-02-2214:48
 * @Version 1.0.0
 */
public class TestRespone {


    @Test
    public void testLombok(){
        Respone<String> respone = Respone.<String>builder().build();
    }
}
