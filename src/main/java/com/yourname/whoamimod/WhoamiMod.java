package com.yourname.whoamimod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class WhoamiMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("whoami")
                .executes(context -> {
                    String rawName = System.getProperty("user.name");
                    //huh
                    final String userName = (rawName == null || rawName.isBlank()) ? "unknown" : rawName;
                    context.getSource().sendFeedback(() -> Text.literal(userName), false);
                    return 1;
                })
            );
        });

        //./exit
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("exit")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("exit"), false);
                    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                        System.err.println("The user has logged out:1");
                        System.err.println("Game crashed! Crash Linux logged out!");
                        new Thread(() -> {
                            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                            Runtime.getRuntime().halt(1);
                        }).start();
                        return 1;
                    } else {
                        context.getSource().sendError(Text.literal("§4Error Server not supported"));
                        return 0;
                    }
                })
            );
        });
    }
}