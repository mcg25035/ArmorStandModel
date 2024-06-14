package dev.codingbear.asm.command.model;

import dev.codingbear.asm.Model;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;

public class DeleteCommand {
    public CommandAPICommand command(){
        return new CommandAPICommand("delete")
                .withPermission(CommandPermission.OP)
                .withArguments(new StringArgument("filename"))
                .executes((sender, args) -> {
                    String filename = (String) args.get("filename");
                    if (Model.deleteModel(filename)){
                        sender.sendMessage("Model " + filename + " deleted");
                    }
                    else{
                        sender.sendMessage("Model " + filename + " not found");
                    }
                });
    }
}
