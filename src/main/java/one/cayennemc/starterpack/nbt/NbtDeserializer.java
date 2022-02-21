package one.cayennemc.starterpack.nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.*;

import java.util.Map;

public class NbtDeserializer {
    public NbtElement deserialize(JsonElement json) {
        if (json instanceof JsonPrimitive primitive) {
            if (primitive.isBoolean()) {
                return NbtByte.of(primitive.getAsBoolean());
            } else if (primitive.isNumber()) {
                Number number = json.getAsNumber();

                if (number instanceof Byte) {
                    return NbtByte.of(primitive.getAsByte());
                } else if (number instanceof Short) {
                    return NbtShort.of(primitive.getAsShort());
                } else if (number instanceof Long) {
                    return NbtLong.of(primitive.getAsLong());
                } else if (number instanceof Float) {
                    return NbtFloat.of(primitive.getAsFloat());
                } else if (number instanceof Double) {
                    return NbtDouble.of(primitive.getAsDouble());
                } else {
                    return NbtInt.of(primitive.getAsInt());
                }
            } else if (primitive.isString()) {
                return NbtString.of(primitive.getAsString());
            }
        }

        if (json instanceof JsonArray array) {
            NbtList list = new NbtList();

            for (JsonElement element: array) {
                list.add(deserialize(element));
            }

            return list;
        }

        if (json instanceof JsonObject object) {
            NbtCompound compound = new NbtCompound();

            for (Map.Entry<String, JsonElement> entry: object.entrySet()) {
                compound.put(entry.getKey(), deserialize(entry.getValue()));
            }

            return compound;
        }

        return new NbtCompound();
    }
}
