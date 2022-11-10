package com.sdpizza.groupproject.database.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.model.Order;

import java.io.IOException;
import java.util.Map;

public class OrderSerializer extends StdSerializer<Order> {
    public OrderSerializer() {
        this(null);
    }

    public OrderSerializer(Class<Order> t) {
        super(t);
    }

    @Override
    public void serialize(Order order, JsonGenerator gen, SerializerProvider provider) throws IOException {
//        gen.writeStartObject();
//        gen.writeFieldName("items");
        gen.writeStartArray();
        for (Item item : order.getItems()) {
            gen.writeObject(item);
//            gen.writeNumberField("quantity", entry.getValue());
        }
        gen.writeEndArray();

//        gen.writeObjectField(order.getItems().toString());
//        gen.writeEndObject();
    }
}
