package dev.codingbear.asm.command.entity;

import dev.codingbear.asm.Model;
import dev.codingbear.asm.ModelEntity;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;

import java.util.ArrayList;
import java.util.List;

public class CreateCommand {
    public CommandAPICommand command() {

        return new CommandAPICommand("create")
                .withArguments(new StringArgument("modelName"))
                .withOptionalArguments(
                        new StringArgument("modelSize").replaceSuggestions(
                                ArgumentSuggestions.strings(
                                        "Small",
                                        "Medium",
                                        "Large"
                                )
                        )
                )
                .executesPlayer((player, args) -> {
                    String modelName = (String)(args.get("modelName"));
                    String modelSize = (String)(args.get("modelSize"));
                    ModelEntity.ArmorStandSize size = ModelEntity.ArmorStandSize.MEDIUM;
                    if (("Small").equals(modelSize)) {
                        size = ModelEntity.ArmorStandSize.SMALL;
                    }
                    else if (("Large").equals(modelSize)) {
                         size = ModelEntity.ArmorStandSize.LARGE;
                    }

                    try{
                        Model model = Model.loadModel(Model.getModelFile(modelName));
                        new ModelEntity(model, player.getLocation(), size);
                        player.sendMessage("Model created!");
                    }
                    catch (Exception e){
                        player.sendMessage("Failed to create model");
                        player.sendMessage(e.getMessage());
                    }
                });
    }
}
