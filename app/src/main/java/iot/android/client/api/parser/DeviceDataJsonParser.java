package iot.android.client.api.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import iot.android.client.model.device.data.AbstractData;

import java.io.IOException;

public class DeviceDataJsonParser extends StdDeserializer<AbstractData> {

    private String type;

    public DeviceDataJsonParser(String type) {
        super((Class<?>) null);
        this.type = type;
    }

    @Override
    public AbstractData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Class<?> aClass = null;
        try {
            aClass = Class.forName("iot.android.client.model.device.data." + type + "Data");
        } catch (ClassNotFoundException e) {
            System.out.println("Нет такого типа данных девайса: " + type + "Data");
        }

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();

        return (AbstractData) mapper.treeToValue(node, aClass);
    }
}
