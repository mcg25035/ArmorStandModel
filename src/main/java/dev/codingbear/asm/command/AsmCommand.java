package dev.codingbear.asm.command;

import dev.codingbear.asm.command.entity.ConfigCommand;
import dev.codingbear.asm.command.entity.CreateCommand;
import dev.codingbear.asm.command.model.DeleteCommand;
import dev.codingbear.asm.command.model.ListCommand;
import dev.codingbear.asm.command.model.ScanCommand;
import dev.jorel.commandapi.CommandAPICommand;

public class AsmCommand {
    public CommandAPICommand command() {
        return new CommandAPICommand("asm")
                .withSubcommand(model())
                .withSubcommand(entity());
    }

    public CommandAPICommand model() {
        return new CommandAPICommand("model")
                .withSubcommand(new ListCommand().command())
                .withSubcommand(new ScanCommand().command())
                .withSubcommand(new DeleteCommand().command());
    }

    public CommandAPICommand entity() {
        return new CommandAPICommand("entity")
                .withSubcommand(new CreateCommand().command())
                .withSubcommand(new ConfigCommand().command())
                .withSubcommand(new dev.codingbear.asm.command.entity.ListCommand().command())
                .withSubcommand(new dev.codingbear.asm.command.entity.DeleteCommand().command());

    }
}
