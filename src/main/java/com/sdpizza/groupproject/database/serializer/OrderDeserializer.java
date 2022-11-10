package com.sdpizza.groupproject.database.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.Order;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrderDeserializer extends StdDeserializer<Order> {
    public OrderDeserializer() {
        this(null);
    }

    public OrderDeserializer(Class<Order> i) {
        super(i);
    }

    @Override
    public Order deserialize(JsonParser parser, DeserializationContext ctx)
    throws IOException, JacksonException {
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode node = parser.getCodec().readTree(parser);
        Pizza pizza = (Pizza) objMapper.readValue(node.get(0).toString(), Item.class);

        return null;
    }
}
