package com.preware.chatslowmode.events;

import com.preware.chatslowmode.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class SlowChatEvent implements Listener {

    public Main main;
    public static boolean slowmode = false;

    public SlowChatEvent(Main main) {
        this.main = main;
    }


    public static HashMap<UUID, Long> delayTime = new HashMap<>();

    @EventHandler
    public void onTalk(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("chatslowmode.bypass.slowmode")) {
            event.setMessage(event.getMessage());
            return;
        }

        if (slowmode) {
            if (delayTime.containsKey(player.getUniqueId())) {
                if (System.currentTimeMillis() < delayTime.get(player.getUniqueId())) {
                    event.setCancelled(true);

                    long cooldownEnds = delayTime.get(player.getUniqueId());
                    long currentTime = System.currentTimeMillis();
                    long difference = cooldownEnds - currentTime;

                    int differenceInSeconds = (int) (difference / 1000);

                    player.sendMessage(main.color("&e&lYou may speak in " + differenceInSeconds));

                    if (differenceInSeconds == 0) {
                        delayTime.remove(player.getUniqueId());
                    }
                }

            } else {

                long currentTime2 = System.currentTimeMillis();
                long cooldownTimer = main.getConfig().getInt("chatSlowMode.chatDelay") * 1000L;
                long currentTime2PlusCooldownTimer = currentTime2 + cooldownTimer;

                delayTime.put(player.getUniqueId(), currentTime2PlusCooldownTimer);
            }
        }
    }




    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        delayTime.remove(player.getUniqueId());
    }



}
