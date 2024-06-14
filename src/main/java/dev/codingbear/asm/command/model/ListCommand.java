package dev.codingbear.asm.command.model;

import dev.codingbear.asm.Model;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;

import java.util.List;

public class ListCommand {
    public CommandAPICommand command() {
        return new CommandAPICommand("list")
                .withArguments(new IntegerArgument("page"))
                .executes((player, args) -> {
                    int page = (int) (args.get("page"))-1;

                    List<String> models = Model.listModels();
                    models.sort(String::compareTo);
                    int itemPerPage = 9;

                    int totalPage = models.size() / itemPerPage;
                    if (models.size() % itemPerPage != 0) {
                        totalPage++;
                    }

                    if (page < 0) {
                        player.sendMessage("Page number is out of range.");
                        return;
                    }

                    if (page > totalPage) {
                        player.sendMessage("Page number is out of range.");
                        return;
                    }

                    int startIndex = Math.max(page * itemPerPage, 0);
                    int endIndex = Math.min(startIndex + itemPerPage, models.size());

                    player.sendMessage("§cPage §e" + (page+1) + " §7/ §e" + totalPage);
                    for (int i = startIndex; i < endIndex; i++) {
                        player.sendMessage(("§e"+(i+1) + ". §f" + models.get(i)));
                    }
                });
    }
}
