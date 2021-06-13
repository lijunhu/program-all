package program.tiger.sword.common.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author junhu.li
 * @ClassName JsonDateDeserializer
 * @Description TODO
 * @date 2019-08-0113:52
 * @Version 1.0.0
 */
public class JsonDateSerializer extends JsonSerializer<Date> {


    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        gen.writeString(format.format(value));
    }
}
