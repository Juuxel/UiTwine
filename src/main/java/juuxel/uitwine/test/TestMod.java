package juuxel.uitwine.test;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import spinnery.common.BaseContainerScreen;

public class TestMod {
    private static final Identifier LIBGUI_GUI_ID = new Identifier("uitwine", "test_gui/libgui");
    private static final Identifier SPINNERY_GUI_ID = new Identifier("uitwine", "test_gui/spinnery");

    public static void init() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

        ContainerProviderRegistry.INSTANCE.registerFactory(
            LIBGUI_GUI_ID,
            (syncId, id, player, buf) -> new LTestGui(syncId, player)
        );

        ContainerProviderRegistry.INSTANCE.registerFactory(
            SPINNERY_GUI_ID,
            (syncId, id, player, buf) -> new STestGui(syncId, player.inventory)
        );

        CommandRegistry.INSTANCE.register(false, dispatcher -> {
            LiteralArgumentBuilder<ServerCommandSource> root = CommandManager.literal("uitwist");
            LiteralArgumentBuilder<ServerCommandSource> libgui =
                CommandManager.literal("libgui").executes(context -> {
                    ContainerProviderRegistry.INSTANCE.openContainer(
                        LIBGUI_GUI_ID,
                        context.getSource().getPlayer(),
                        buf -> {}
                    );
                    return 1;
                });
            LiteralArgumentBuilder<ServerCommandSource> spinnery =
                CommandManager.literal("spinnery").executes(context -> {
                    ContainerProviderRegistry.INSTANCE.openContainer(
                        SPINNERY_GUI_ID,
                        context.getSource().getPlayer(),
                        buf -> {}
                    );
                    return 1;
                });

            root.then(libgui);
            root.then(spinnery);
            dispatcher.register(root);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

        ScreenProviderRegistry.INSTANCE.<LTestGui>registerFactory(
            LIBGUI_GUI_ID, menu -> new CottonInventoryScreen<>(menu, menu.getPlayer())
        );

        ScreenProviderRegistry.INSTANCE.registerFactory(
            SPINNERY_GUI_ID,
            (syncId, id, player, buf) -> new BaseContainerScreen<>(
                new LiteralText(""),
                new STestGui(syncId, player.inventory),
                player
            )
        );
    }
}
