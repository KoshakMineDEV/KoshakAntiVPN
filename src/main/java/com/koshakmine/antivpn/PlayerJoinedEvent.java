package com.koshakmine.antivpn;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class PlayerJoinedEvent implements Listener {

    @EventHandler
    public void PlayerJoinedEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = player.getRawAddress();
        if(!player.hasPermission("kantivpn.bypass")) {
            double ipScore = Main.instance.requestIPScore(ip);
            if(ipScore > 0.98) {
                player.kick("[Koshak's AntiVPN] §cYou are using VPN!. Please, rejoin without it." );
            }
        }
    }
}
