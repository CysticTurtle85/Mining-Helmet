package net.cystic.mininghelmet.client;

//#if SODIUM_DYNAMIC_LIGHTS
import net.cystic.mininghelmet.MiningHelmet;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

/**
 * Sodium Dynamic Lights registers its loader for item light sources (assets/&lt;mod&gt;/dynamiclights/item/*.json)
 * too late to take part in the game's first resource load, so the helmet stayed dark until the player pressed
 * F3+T. This listener runs during that first load and asks Sodium Dynamic Lights to read the files.
 */
public final class SodiumDynamicLightsFix implements ResourceManagerReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiningHelmet.MOD_ID);
    private static final Method LOAD = findLoader();

    private static Method findLoader() {
        ClassLoader loader = SodiumDynamicLightsFix.class.getClassLoader();
        try {
            Class.forName("toni.sodiumdynamiclights.SodiumDynamicLights", false, loader);
            return Class.forName("dev.lambdaurora.lambdynlights.api.item.ItemLightSources", false, loader)
                    .getMethod("load", ResourceManager.class);
        } catch (ReflectiveOperationException | LinkageError e) {
            return null;
        }
    }

    /** Whether Sodium Dynamic Lights is installed. */
    public static boolean isNeeded() {
        return LOAD != null;
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        try {
            LOAD.invoke(null, manager);
        } catch (ReflectiveOperationException | RuntimeException e) {
            LOGGER.warn("Couldn't load Sodium Dynamic Lights item light sources; the helmet will light up after F3+T", e);
        }
    }
}
//#endif
