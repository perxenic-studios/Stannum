package dev.perxenic.stannic.infra.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.function.BiConsumer;

import static dev.perxenic.stannic.Stannic.REGISTRATE;

@EventBusSubscriber
public class StannicDatagen {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void gatherDataHighPriority(GatherDataEvent event) {
        addExtraRegistrateData();
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Nothing atm
    }

    private static void addExtraRegistrateData() {
        StannicRegistrateTags.addGenerators();

        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            provideDefaultLang("interface", provider::add);
        });
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/stannic/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
