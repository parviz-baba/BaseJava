package com.basejava.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateGsonAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("year", date.getYear());
        object.addProperty("month", date.getMonthValue());
        object.addProperty("day", date.getDayOfMonth());
        return object;
    }

    @Override
    public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();
        int year = obj.get("year").getAsInt();
        int month = obj.get("month").getAsInt();
        int day = obj.get("day").getAsInt();
        return LocalDate.of(year, month, day);
    }
}
