package rip.hippo.uuidservice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Hippo
 */
public final class PlayerListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent playerJoinEvent) {
    Player player = playerJoinEvent.getPlayer();
    UUIDService.register(player);
  }
}
