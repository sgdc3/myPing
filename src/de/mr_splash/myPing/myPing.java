package de.mr_splash.myPing;

import de.mr_splash.myPing.Commands.PingCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class myPing extends Plugin
{

    public String prefix;
    public String other_prefix;

    @Override
    public void onEnable()
    {
        registerCommands();
        loadcfg();
    }


    private void registerCommands()
    {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingCommand(this));
    }

    private void loadcfg()
    {
        try
        {
            if(!getDataFolder().exists())
            {
                getDataFolder().mkdir();
            }
            File file = new File(getDataFolder().getPath(), "config.yml");
            if(!file.exists())
            {
                file.createNewFile();
            }

            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            if(configuration.get("settings.prefix") == null)
            {
                configuration.set("settings.prefix", "&6Your ping: &b%ping% ms");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            if(configuration.get("settings.other_prefix") == null)
            {
                configuration.set("settings.other_prefix", "&6%player%&3 his ping is:&b %ping%");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            prefix = ChatColor.translateAlternateColorCodes('&', configuration.getString("settings.prefix"));
            other_prefix = ChatColor.translateAlternateColorCodes('&', configuration.getString("settings.other_prefix"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
