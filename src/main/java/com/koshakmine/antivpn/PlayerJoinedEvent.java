package com.koshakmine.antivpn;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.scheduler.AsyncTask;


public class PlayerJoinedEvent implements Listener {

    private KoshakAntiVPN plugin;

    public PlayerJoinedEvent(KoshakAntiVPN plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerJoinedEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = player.getRawAddress();
        if(!player.hasPermission("kantivpn.bypass")) {
            plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                @Override
                public void onRun() {
                    plugin.requestIPScore(ip).thenAcceptAsync((score) -> {
                        if(score > 0.98) {
                            player.kick(plugin.getKickMessage(), false);
                        }
                    }).join();
                }
            });
        }
    }
}
