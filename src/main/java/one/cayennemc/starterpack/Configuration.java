package one.cayennemc.starterpack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import one.cayennemc.starterpack.nbt.NbtDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Configuration {
    private final String configPath;
    private final List<ItemStack> itemStacks;

    public Configuration(String path) {
        this.configPath = path;
        this.itemStacks = new ArrayList<>();
    }

    public boolean isExists() {
        return Files.exists(Path.of(configPath));
    }

    public void restore() throws IOException {
        Path.of("config/").toFile().mkdirs();
        Files.copy(Objects.requireNonNull(
                    this.getClass().getClassLoader().getResourceAsStream("starterpack.json"),
                    "Couldn't find the configuration file in the JAR"), Path.of(configPath));
    }

    public void load() throws FileNotFoundException {
        JsonObject configObject = JsonParser.parseReader(new FileReader(configPath)).getAsJsonObject();
        JsonArray array = configObject.getAsJsonArray("Items");
        NbtDeserializer deserializer = new NbtDeserializer();
        for (JsonElement element: array) {
            JsonObject object = element.getAsJsonObject().getAsJsonObject("Item");
            System.out.println(object.toString());
            NbtCompound compound = (NbtCompound) deserializer.deserialize(object);
            System.out.println(compound.toString());
            ItemStack item = ItemStack.fromNbt(compound);
            itemStacks.add(item);
        }
    }

    public List<ItemStack> getItemStacks() {
        return new ArrayList<>(itemStacks);
    }
}
