package com.meowify;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;

public class MeowifyCommand {

    public static void register(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("meowify")
                .executes(ctx -> {
                    showStatus(ctx.getSource());
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        boolean newState = !MeowifyConfig.ENABLED.get();
                        MeowifyConfig.ENABLED.set(newState);
                        MeowifyConfig.SPEC.save();
                        ctx.getSource().sendSuccess(() -> Component.literal("[Meowify] ")
                                .withStyle(ChatFormatting.GOLD)
                                .append(Component.literal("Meowification is now ")
                                        .withStyle(ChatFormatting.YELLOW))
                                .append(Component.literal(newState ? "ENABLED (=^･ω･^=)" : "DISABLED")
                                        .withStyle(newState ? ChatFormatting.GREEN : ChatFormatting.RED)), false);
                        if (newState) {
                            MeowifySoundHelper.playMeow(1.0F);
                        }
                        return 1;
                    })
                )
                .then(Commands.literal("mode")
                    .then(Commands.literal("furry")
                        .executes(ctx -> setMode(ctx.getSource(), MeowifyMode.FURRY))
                    )
                    .then(Commands.literal("uwu")
                        .executes(ctx -> setMode(ctx.getSource(), MeowifyMode.FURRY))
                    )
                    .then(Commands.literal("meow")
                        .executes(ctx -> setMode(ctx.getSource(), MeowifyMode.MEOW))
                    )
                    .then(Commands.literal("puns")
                        .executes(ctx -> setMode(ctx.getSource(), MeowifyMode.PUNS))
                    )
                    .then(Commands.literal("suffix")
                        .executes(ctx -> setMode(ctx.getSource(), MeowifyMode.SUFFIX))
                    )
                )
                .then(Commands.literal("sound")
                    .executes(ctx -> {
                        boolean newState = !MeowifyConfig.PLAY_CAT_SOUND.get();
                        MeowifyConfig.PLAY_CAT_SOUND.set(newState);
                        MeowifyConfig.SPEC.save();
                        ctx.getSource().sendSuccess(() -> Component.literal("[Meowify] ")
                                .withStyle(ChatFormatting.GOLD)
                                .append(Component.literal("Cat sound effects: ")
                                        .withStyle(ChatFormatting.YELLOW))
                                .append(Component.literal(newState ? "ON" : "OFF")
                                        .withStyle(newState ? ChatFormatting.GREEN : ChatFormatting.RED)), false);
                        if (newState) {
                            MeowifySoundHelper.playMeow(1.0F);
                        }
                        return 1;
                    })
                )
                .then(Commands.literal("test")
                    .then(Commands.argument("text", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String text = StringArgumentType.getString(ctx, "text");
                            String meowed = Meowifier.meowify(
                                text,
                                MeowifyConfig.MODE.get(),
                                MeowifyConfig.PRESERVE_URLS.get(),
                                MeowifyConfig.PRESERVE_NUMBERS.get(),
                                MeowifyConfig.APPEND_CAT_FACE.get()
                            );
                            ctx.getSource().sendSuccess(() -> Component.literal("[Meowify Preview] ")
                                    .withStyle(ChatFormatting.GOLD)
                                    .append(Component.literal(meowed)
                                            .withStyle(ChatFormatting.AQUA)), false);
                            MeowifySoundHelper.playMeow(1.0F);
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("imacat")
                    .executes(ctx -> {
                        ctx.getSource().sendSuccess(() -> Component.literal("(=^･ω･^=) I'm a cat! Meow! :3")
                                .withStyle(ChatFormatting.LIGHT_PURPLE), false);
                        MeowifySoundHelper.playPurr();
                        return 1;
                    })
                )
        );

        // Also register /meow shortcut
        dispatcher.register(
            Commands.literal("meow")
                .executes(ctx -> {
                    ctx.getSource().sendSuccess(() -> Component.literal("Meow! (=^･ω･^=)")
                            .withStyle(ChatFormatting.LIGHT_PURPLE), false);
                    MeowifySoundHelper.playMeow(1.0F);
                    return 1;
                })
                .then(Commands.argument("text", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        String text = StringArgumentType.getString(ctx, "text");
                        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.connection != null) {
                            Minecraft.getInstance().player.connection.sendChat(text);
                        }
                        return 1;
                    })
                )
        );
    }

    private static int setMode(CommandSourceStack source, MeowifyMode mode) {
        MeowifyConfig.MODE.set(mode);
        MeowifyConfig.SPEC.save();
        source.sendSuccess(() -> Component.literal("[Meowify] ")
                .withStyle(ChatFormatting.GOLD)
                .append(Component.literal("Mode changed to: ")
                        .withStyle(ChatFormatting.YELLOW))
                .append(Component.literal(mode.getDisplayName() + " (" + mode.name() + ")")
                        .withStyle(ChatFormatting.GREEN)), false);
        MeowifySoundHelper.playMeow(1.0F);
        return 1;
    }

    private static void showStatus(CommandSourceStack source) {
        boolean enabled = MeowifyConfig.ENABLED.get();
        MeowifyMode mode = MeowifyConfig.MODE.get();
        boolean sound = MeowifyConfig.PLAY_CAT_SOUND.get();

        source.sendSuccess(() -> Component.literal("=== Meowify Mod (v1.0.0) ===")
                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD), false);
        source.sendSuccess(() -> Component.literal("Status: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(enabled ? "ENABLED (=^･ω･^=)" : "DISABLED")
                        .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED)), false);
        source.sendSuccess(() -> Component.literal("Mode: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(mode.getDisplayName())
                        .withStyle(ChatFormatting.AQUA)), false);
        source.sendSuccess(() -> Component.literal("Cat Sound: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(sound ? "ON" : "OFF")
                        .withStyle(sound ? ChatFormatting.GREEN : ChatFormatting.RED)), false);
        source.sendSuccess(() -> Component.literal("Commands:")
                .withStyle(ChatFormatting.YELLOW), false);
        source.sendSuccess(() -> Component.literal("  /meowify toggle  - Toggle on/off")
                .withStyle(ChatFormatting.GRAY), false);
        source.sendSuccess(() -> Component.literal("  /meowify mode <furry|meow|puns|suffix>  - Change mode")
                .withStyle(ChatFormatting.GRAY), false);
        source.sendSuccess(() -> Component.literal("  /meowify sound  - Toggle sound effect")
                .withStyle(ChatFormatting.GRAY), false);
        source.sendSuccess(() -> Component.literal("  /meowify test <text>  - Test meowification")
                .withStyle(ChatFormatting.GRAY), false);
        source.sendSuccess(() -> Component.literal("  /meowify imacat  - I'm a cat!")
                .withStyle(ChatFormatting.GRAY), false);
    }
}
