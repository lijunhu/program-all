package program.tiger.sword;

import cn.afterturn.easypoi.cache.manager.FileLoaderImpl;
import cn.afterturn.easypoi.cache.manager.POICacheManager;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author junhu.li
 * @ClassName ExcelListener
 * @Description TODO
 * @date 2020/4/615:08
 * @Version 1.0.0
 */
public class ExcelListener implements ApplicationListener<ApplicationReadyEvent> {


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        POICacheManager.setFileLoader(new FileLoaderImpl());
    }
}
