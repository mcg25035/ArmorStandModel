package dev.codingbear.asm.command.entity;

import dev.codingbear.asm.ModelEntity;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.IntegerArgument;

public class DeleteCommand {
    public CommandAPICommand command(){
        return new CommandAPICommand("delete")
                .withPermission(CommandPermission.OP)
                .withArguments(new IntegerArgument("index"))
                .executes((sender, args) -> {
                    int index = (int) args.get("index")-1;
                    if (ModelEntity.entities.size() <= index){
                        sender.sendMessage("Entity not found");
                        return;
                    } else if (0 > index){
                        sender.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).delete();
                    sender.sendMessage("§aEntity deleted");
                });
    }
}
