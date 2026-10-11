package com.meowify;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(Meowify.MODID)
public class Meowify {
    public static final String MODID = "meowify";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Meowify() {
        LOGGER.info("Initializing Meowify - I'm a cat! (=^･ω･^=)");

        // Make sure Forge knows this mod doesn't need to be installed on the server
        ModLoadingContext.get().registerExtensionPoint(
                IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(
                        () -> IExtensionPoint.DisplayTest.IGNORESERVERONLY,
                        (remoteVersion, isFromServer) -> true
                )
        );

        // Register client config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MeowifyConfig.SPEC);

        // Register client event listeners
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.register(MeowifyChatHandler.class);
            MinecraftForge.EVENT_BUS.addListener(MeowifyCommand::register);
        }
    }
}
