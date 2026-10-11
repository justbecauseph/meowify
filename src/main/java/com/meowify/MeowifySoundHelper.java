package com.meowify;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;

public class MeowifySoundHelper {
    private static final RandomSource RANDOM = RandomSource.create();

    private static final SoundEvent[] CAT_SOUNDS = new SoundEvent[] {
            SoundEvents.CAT_AMBIENT,
            SoundEvents.CAT_PURREOW,
            SoundEvents.CAT_PURR,
            SoundEvents.CAT_BEG_FOR_FOOD,
            SoundEvents.CAT_STRAY_AMBIENT
    };

    public static void playMeow(float volumeMultiplier) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (!MeowifyConfig.PLAY_CAT_SOUND.get()) return;

        SoundEvent sound = CAT_SOUNDS[RANDOM.nextInt(CAT_SOUNDS.length)];
        float pitch = 1.0F;
        if (MeowifyConfig.CAT_SOUND_PITCH_VARIATION.get()) {
            pitch = 0.9F + RANDOM.nextFloat() * 0.3F;
        }

        float volume = (float) (MeowifyConfig.CAT_SOUND_VOLUME.get() * volumeMultiplier);
        mc.getSoundManager().play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }

    public static void playPurr() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        float volume = (float) (double) MeowifyConfig.CAT_SOUND_VOLUME.get();
        mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.CAT_PURR, 1.0F, volume));
    }
}
