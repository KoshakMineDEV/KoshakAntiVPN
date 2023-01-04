package com.koshakmine.antivpn;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends PluginBase {
    public static PluginLogger log;
    public static Main instance;
    @Override
    public void onLoad() {
        instance = this;
        log = this.getLogger();
    }

    public double requestIPScore(String ip) {
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
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinedEvent(), this);
    }
}
