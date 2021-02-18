package com.willfp.ecoarmor;

import com.willfp.eco.util.command.AbstractCommand;
import com.willfp.eco.util.display.DisplayModule;
import com.willfp.eco.util.integrations.IntegrationLoader;
import com.willfp.eco.util.plugin.AbstractEcoPlugin;
import com.willfp.eco.util.protocollib.AbstractPacketAdapter;
import com.willfp.ecoarmor.commands.CommandEagive;
import com.willfp.ecoarmor.commands.CommandEareload;
import com.willfp.ecoarmor.commands.TabcompleterEagive;
import com.willfp.ecoarmor.display.ArmorDisplay;
import com.willfp.ecoarmor.effects.Effect;
import com.willfp.ecoarmor.effects.Effects;
import com.willfp.ecoarmor.sets.ArmorSets;
import com.willfp.ecoarmor.sets.util.EffectiveDurabilityListener;
import com.willfp.ecoarmor.sets.util.PotionEffectListener;
import com.willfp.ecoarmor.upgrades.advanced.AdvancementShardListener;
import com.willfp.ecoarmor.upgrades.tier.CrystalListener;
import com.willfp.ecoarmor.upgrades.tier.Tier;
import com.willfp.ecoarmor.util.DiscoverRecipeListener;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class EcoArmorPlugin extends AbstractEcoPlugin {
    /**
     * Instance of EcoArmor.
     */
    @Getter
    private static EcoArmorPlugin instance;

    /**
     * Internal constructor called by bukkit on plugin load.
     */
    public EcoArmorPlugin() {
        super("EcoArmor", 0, 10002, "com.willfp.ecoarmor.proxy", "&c");
        instance = this;
    }

    /**
     * Code executed on plugin enable.
     */
    @Override
    public void enable() {
        Effects.values().stream().filter(Effect::isEnabled).forEach(effect -> this.getEventManager().registerListener(effect));

        this.getExtensionLoader().loadExtensions();

        if (this.getExtensionLoader().getLoadedExtensions().isEmpty()) {
            this.getLog().info("&cNo extensions found");
        } else {
            this.getLog().info("Extensions Loaded:");
            this.getExtensionLoader().getLoadedExtensions().forEach(extension -> this.getLog().info("- " + extension.getName() + " v" + extension.getVersion()));
        }
    }

    /**
     * Code executed on plugin disable.
     */
    @Override
    public void disable() {
        // Nothing needs to be called on disable
    }

    /**
     * Nothing is called on plugin load.
     */
    @Override
    public void load() {
        // Nothing needs to be called on load
    }

    /**
     * Code executed on reload.
     */
    @Override
    public void onReload() {
        Effects.values().forEach(effect -> this.getEventManager().unregisterListener(effect));
        Effects.values().stream().filter(Effect::isEnabled).forEach(effect -> this.getEventManager().registerListener(effect));
        this.getLog().info(ArmorSets.values().size() + " Sets Loaded");
        this.getExtensionLoader().unloadExtensions();
    }

    /**
     * Code executed after server is up.
     */
    @Override
    public void postLoad() {
        // Nothing needs to be called after load.
    }

    /**
     * EcoArmor-specific integrations.
     *
     * @return A list of all integrations.
     */
    @Override
    public List<IntegrationLoader> getIntegrationLoaders() {
        return new ArrayList<>();
    }

    /**
     * EcoArmor-specific commands.
     *
     * @return A list of all commands.
     */
    @Override
    public List<AbstractCommand> getCommands() {
        return Arrays.asList(
                new CommandEareload(this),
                new CommandEagive(this)
        );
    }

    /**
     * Packet Adapters.
     *
     * @return A list of packet adapters.
     */
    @Override
    public List<AbstractPacketAdapter> getPacketAdapters() {
        return new ArrayList<>();
    }

    /**
     * EcoArmor-specific listeners.
     *
     * @return A list of all listeners.
     */
    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(
                new CrystalListener(this),
                new AdvancementShardListener(this),
                new PotionEffectListener(this),
                new EffectiveDurabilityListener(this),
                new DiscoverRecipeListener(this)
        );
    }

    @Override
    public List<Class<?>> getUpdatableClasses() {
        return Arrays.asList(
                ArmorSets.class,
                TabcompleterEagive.class,
                Tier.class
        );
    }

    @Override
    protected @Nullable DisplayModule createDisplayModule() {
        return new ArmorDisplay(this);
    }
}
