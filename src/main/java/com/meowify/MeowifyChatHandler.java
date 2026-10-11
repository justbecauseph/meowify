package com.meowify;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Meowify.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MeowifyChatHandler {

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        if (!MeowifyConfig.ENABLED.get()) {
            return;
        }

        String original = event.getMessage();
        if (original == null || original.trim().isEmpty()) {
            return;
        }

        // Never modify commands
        if (original.startsWith("/")) {
            return;
        }

        String meowified = Meowifier.meowify(
                original,
                MeowifyConfig.MODE.get(),
                MeowifyConfig.PRESERVE_URLS.get(),
                MeowifyConfig.PRESERVE_NUMBERS.get(),
                MeowifyConfig.APPEND_CAT_FACE.get()
        );

        if (!meowified.equals(original)) {
            event.setMessage(meowified);
            MeowifySoundHelper.playMeow(1.0F);
        }
    }
}
