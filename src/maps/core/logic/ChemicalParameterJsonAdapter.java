package maps.core.logic;

import java.lang.reflect.*;
import com.google.gson.*;

/**
 * Вспомогательный класс для сериализации в JSON-формат набора химических параметров
 * {@link core.logic.ChemicalParameter}.
 */
public class ChemicalParameterJsonAdapter implements JsonSerializer<ChemicalParameter> {
  public JsonElement serialize(ChemicalParameter src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject obj = new JsonObject();
    obj.addProperty("name", src.name().toLowerCase());
    obj.addProperty("title", src.getTitle());
    obj.addProperty("shortTitle", src.getShortTitle());
    obj.addProperty("formula", src.getFormula());
    obj.addProperty("unit", src.getUnit());
    obj.addProperty("isBackground", src.getIsBackground());
    obj.add("methods", context.serialize(src.getMethods()));
    return obj;
  }
}
