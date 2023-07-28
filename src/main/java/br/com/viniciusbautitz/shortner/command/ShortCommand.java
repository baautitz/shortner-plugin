package br.com.viniciusbautitz.shortner.command;

import br.com.viniciusbautitz.shortner.Main;
import br.com.viniciusbautitz.shortner.model.Link;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ShortCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("short.use")) {
            sender.sendMessage("§cVocê não possui permissão para executar este comando.");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUtilize /short <<link>|info|remove> [nome]");
            return false;
        }

        List<String> subCommands = Arrays.asList("info", "remove");
        if (subCommands.contains(args[0].toLowerCase())) {
            if (args.length == 1) {
                sender.sendMessage("§cUtilize /short <info|remove> <nome>");
                return false;
            }

            switch (args[0].toLowerCase()) {
                case "info":
                    if (!sender.hasPermission("short.get")) {
                        sender.sendMessage("§cVocê não possui permissão para consultar um link.");
                        return false;
                    }

                    sender.sendMessage("§eConsultando link §f" + args[1] + "§e...");
                    new Thread(() -> {
                        try {
                            Link link = Main.getLinkController().getLinkByName(args[1]);
                            if (link == null) {
                                sender.sendMessage("§cNão foi possível encontrar o link §f" + args[1] + "§c.");
                                return;
                            }

                            sender.sendMessage("");
                            sender.sendMessage("§aInformações de §f" + link.getName() + "§a:");
                            sender.sendMessage("");
                            sender.sendMessage("§aLink: §f" + link.getLink());
                            sender.sendMessage("§aAutor: §f" + link.getAuthor() + " - " + link.getOrigin());
                            sender.sendMessage("");
                        } catch (Exception e) {
                            sender.sendMessage("§cOcorreu um erro ao executar este comando, contate um membro de nossa equipe. [SHORTNER::SHORTCOMMAND::INFO]");
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
                case "remove":
                    if (!sender.hasPermission("short.remove")) {
                        sender.sendMessage("§cVocê não possui permissão para excluir um link.");
                        return false;
                    }

                    sender.sendMessage("§eConsultando link §f" + args[1] + "§e...");
                    new Thread(() -> {
                        try {
                            Link link = Main.getLinkController().getLinkByName(args[1]);
                            if (link == null) {
                                sender.sendMessage("§cNão foi possível encontrar o link §f" + args[1] + "§c.");
                                return;
                            }

                            boolean deleteLink = Main.getLinkController().deleteByName(link.getName());
                            if (deleteLink)
                                sender.sendMessage("§aLink §f" + link.getName() + "§a excluído com sucesso.");
                        } catch (Exception e) {
                            sender.sendMessage("§cOcorreu um erro ao executar este comando, contate um membro de nossa equipe. [SHORTNER::SHORTCOMMAND::REMOVE]");
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;
            }
            return true;
        }

        String linkArgs = args[0];

        if (!sender.hasPermission("short.create")) {
            sender.sendMessage("§cVocê não possui permissão para criar um link.");
            return false;
        }

        if (!linkArgs.startsWith("http://") && !linkArgs.startsWith("https://")) {
            sender.sendMessage("§cO link deve iniciar com http:// ou https://.");
            return true;
        }

        Link link = new Link(null, args[0], "Shortner Plugin", "Bautitz");
        if (args.length > 1) link.setName(args[1]);

        sender.sendMessage("§eCriando link...");
        new Thread(() -> {
            try {
                if (Main.getLinkController().getLinkByName(link.getName()) != null) {
                    sender.sendMessage("§cO link §f" + link.getName() + "§c já existe.");
                    return;
                }

                Link createdLink = Main.getLinkController().createLink(link);
                if (createdLink != null) {
                    sender.sendMessage("");
                    sender.sendMessage("§aLink §f" + createdLink.getName() + "§a criado com sucesso!");
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        TextComponent baseComponent = new TextComponent("§aClique ");

                        TextComponent aquiComponent = new TextComponent("§f§lAQUI ");
                        aquiComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "vnici.us/" + createdLink.getName()));

                        baseComponent.addExtra(aquiComponent);
                        baseComponent.addExtra(new TextComponent("§apara copiar."));

                        player.spigot().sendMessage(baseComponent);
                    }
                    sender.sendMessage("");
                }
            } catch (Exception e) {
                sender.sendMessage("§cOcorreu um erro ao executar este comando, contate um membro de nossa equipe. [SHORTNER::SHORTCOMMAND::CREATE]");
                throw new RuntimeException(e);
            }
        }).start();

        return true;
    }
}
