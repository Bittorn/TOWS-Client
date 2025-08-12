package net.bittorn.towsclient.config;


import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import net.bittorn.towsclient.TOWSClient;

@Modmenu(modId = TOWSClient.MOD_ID)
@Config(name = "towsconfig", wrapperName = "TOWSConfig")
public class TOWSConfigModel {
    // Whether the mod should be enabled
    public boolean enabled = true;

    // Server IP address to be looked up
    // Use '*' for wildcards/subdomains
    public String serverIP = "*.callmecarson.live";

    // Whether the mod should be enabled on local worlds
    public boolean enableLocally = false;
}
