package app.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.DateTime;

import java.io.IOException;

public class JsonDateTimeSerializer extends StdSerializer<DateTime> {
    private JsonDateTimeSerializer() { this(null); }
    private JsonDateTimeSerializer(Class<DateTime> t) {
        super(t);
    }

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(dateTime.toString());
    }
}
