package com.koshakmine.antivpn;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;


@Getter
public class KoshakAntiVPN extends PluginBase {
    private PluginLogger log;
    private String kickMessage;
    private Config config;

    @Override
    public void onLoad() {
        log = this.getLogger();
    }

    public CompletableFuture<Double> requestIPScore(String ip) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("https://check.getipintel.net/check.php?ip=" + ip + "&contact=vnikire@gmail.com");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                return Double.parseDouble(content.toString());
            } catch (Exception e) {
                return 0.0;
            }
        });
    }

    @Override
    public void onEnable() {

        saveDefaultConfig();

        config = new Config(
                new File(this.getDataFolder(), "config.yml"),
                Config.YAML,
                new LinkedHashMap<>() {{
                    put("KICK_MESSAGE", "[Koshak's AntiVPN] §cYou are using VPN!. Please, rejoin without it.");
                }}
        );

        this.kickMessage = TextFormat.colorize('&', config.get("KICK_MESSAGE",
                "&b[Koshak's AntiVPN] §cYou are using VPN!. Please, rejoin without it."));



        getServer().getPluginManager().registerEvents(new PlayerJoinedEvent(this), this);
    }
}
