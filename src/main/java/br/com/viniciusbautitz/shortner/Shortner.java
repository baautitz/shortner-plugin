package br.com.viniciusbautitz.shortner;


import br.com.viniciusbautitz.shortner.command.ShortCommand;
import br.com.viniciusbautitz.shortner.controller.LinkController;
import br.com.viniciusbautitz.shortner.service.LinkService;
import br.com.viniciusbautitz.shortner.service.VniciusLinkService;
import org.bukkit.plugin.java.JavaPlugin;

public class Shortner extends JavaPlugin {

    private static LinkController linkController;

    @Override
    public void onEnable() {
        this.getLogger().info("Inicializando plugin...");
        this.saveDefaultConfig();

        String vniciusAuthorization = this.getConfig().getString("vnicius-authorization");
        LinkService vniciusService = new VniciusLinkService(vniciusAuthorization);

        linkController = new LinkController(vniciusService);

        getCommand("short").setExecutor(new ShortCommand());
    }

    public static LinkController getLinkController() {
        return linkController;
    }

}
