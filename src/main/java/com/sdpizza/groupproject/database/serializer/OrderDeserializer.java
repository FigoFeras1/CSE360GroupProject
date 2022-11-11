package com.sdpizza.groupproject.database.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.model.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderDeserializer extends StdDeserializer<Order> {
    public OrderDeserializer() {
        this(null);
    }

    public OrderDeserializer(Class<Order> i) {
        super(i);
    }

    public static Order deserialize(String json) {
        ObjectMapper objMapper = new ObjectMapper();
        Order order = new Order();
        try { order = objMapper.readValue(json, Order.class); }
        catch (Exception ex) { ex.printStackTrace(); }

        return order;
    }
    @Override
    public Order deserialize(JsonParser parser, DeserializationContext ctx)
    throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode node = parser.getCodec().readTree(parser);
        List<Item> items = new ArrayList<>();
        int i = 0;
        while (true) {
            JsonNode curr = node.get(i++);
            if (curr == null) break;
            items.add(objMapper.readValue(curr.toString(), Item.class));
        }
        Order order = new Order();
        order.setItems(items);

        return order;
    }
}
