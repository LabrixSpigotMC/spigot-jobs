package net.nothing.jobs.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class JobCreationUI implements UnserInventoryInterface{

    private final String name;
    private final Gui gui;

    public JobCreationUI(String name){
        this.name = name;
        gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # #",
                        "# . . . . . . . #",
                        "# . . . . . . . #",
                        "# . . . . . . . #",
                        "# # # # # # # # #",
                        ". . . . . . < > +")
                .addIngredient('#', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))
                .build();

        gui.setItem(45, new SimpleItem(new ItemBuilder(Material.BLADE_POTTERY_SHERD).setDisplayName("§8» §a§l+ §8«").addLoreLines("§8§oCreate job")));
    }

    @Override
    public void open(Player player) {
        final Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .build();

    }

    @Override
    public String getTitle() {
        return this.name;
    }
}
