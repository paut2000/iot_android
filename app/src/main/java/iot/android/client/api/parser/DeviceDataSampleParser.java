package iot.android.client.api.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import iot.android.client.api.message.DeviceDataSampleMessage;
import iot.android.client.model.device.data.AbstractData;

import java.io.IOException;
import java.util.List;

public class DeviceDataSampleParser extends StdDeserializer<DeviceDataSampleMessage> {

    public DeviceDataSampleParser() {
        this(null);
    }

    protected DeviceDataSampleParser(Class<?> vc) {
        super(vc);
    }

    @Override
    public DeviceDataSampleMessage deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String type = node.get("type").asText();

        ObjectMapper mapper = ((ObjectMapper) jsonParser.getCodec()).registerModule(
                new SimpleModule().addDeserializer(AbstractData.class, new DeviceDataJsonParser(type))
        );

        JsonParser dataListToken = mapper.treeAsTokens(node.get("dataList"));
        List<AbstractData> dataList = mapper.readValue(dataListToken, new TypeReference<List<AbstractData>> () {});

        DeviceDataSampleMessage message = new DeviceDataSampleMessage();
        message.setType(type);
        message.setDataList(dataList);

        return message;
    }

}
