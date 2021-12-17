package com.willfp.ecoarmor.commands

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.command.CommandHandler
import com.willfp.eco.core.command.impl.PluginCommand

class CommandEcoarmor(plugin: EcoPlugin) : PluginCommand(plugin, "ecoarmor", "ecoarmor.command.ecoarmor", false) {
    init {
        addSubcommand(CommandReload(plugin))
            .addSubcommand(CommandGive(plugin))
    }

    override fun getHandler(): CommandHandler {
        return CommandHandler { sender, _ ->
            sender.sendMessage(
                plugin.langYml.getMessage("invalid-command")
            )
        }
    }
}