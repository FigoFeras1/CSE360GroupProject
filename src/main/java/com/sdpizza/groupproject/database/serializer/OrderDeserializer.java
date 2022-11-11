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
import java.util.Iterator;
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
        json = json.substring(1);
        json = json.replace("\\", "");

        try { order = objMapper.readValue(json, Order.class); }
        catch (Exception ex) { ex.printStackTrace(); }

        return order;
    }
    @Override
    public Order deserialize(JsonParser parser, DeserializationContext ctx)
    throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        List<Item> items = new ArrayList<>();
        JsonNode rootNode =  parser.getCodec().readTree(parser);
        JsonNode itemsNode = rootNode.get("items");
        Iterator<JsonNode> it = itemsNode.elements();

        while (it.hasNext()) {
            items.add(objMapper.readValue(it.next().toString(), Item.class));
        }

        Order order = new Order();
        order.setItems(items);

        return order;
    }
}
