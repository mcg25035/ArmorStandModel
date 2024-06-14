package dev.codingbear.asm.command.entity;

import dev.codingbear.asm.Model;
import dev.codingbear.asm.ModelEntity;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;

import java.time.Instant;
import java.util.List;

public class ListCommand {
    public String secondToTime(long second) {
        long day = second / 86400;
        second %= 86400;
        long hour = second / 3600;
        second %= 3600;
        long minute = second / 60;
        second %= 60;
        long sec = second;
        if (day > 0) {
            return "§a" + day + " §fday§a " + hour + " §fhour§a " + minute + " §fmin§a " + sec + " §fsec ago";
        }
        if (hour > 0) {
            return "§a" + hour + " §fhour§a " + minute + " §fmin§a " + sec + " §fsec ago";
        }
        if (minute > 0) {
            return "§a" + minute + " §fmin§a " + sec + " §fsec ago";
        }
        return "§a" + sec + " §fsec ago";
    }

    public CommandAPICommand command() {
        return new CommandAPICommand("list")
                .withArguments(new IntegerArgument("page"))
                .executes((player, args) -> {
                    int page = (int) (args.get("page"))-1;

                    List<ModelEntity> modelEntities = ModelEntity.entities;

                    int itemPerPage = 9;

                    int totalPage = modelEntities.size() / itemPerPage;
                    if (modelEntities.size() % itemPerPage != 0) {
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
                    int endIndex = Math.min(startIndex + itemPerPage, modelEntities.size());

                    player.sendMessage("§cPage §e" + (page+1) + " §7/ §e" + totalPage);
                    for (int i = startIndex; i < endIndex; i++) {
                        String time = secondToTime(Instant.now().getEpochSecond() - modelEntities.get(i).spawnAt.getEpochSecond());
                        player.sendMessage(("§e"+(i+1) + ". §f" + time));
                    }
                });
    }
}
