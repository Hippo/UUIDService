package rip.hippo.uuidservice;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hippo
 */
public enum UUIDService {
  ;

  private static final Map<String, String> PLAYER_UUID_MAP = new HashMap<>();
  private static final JsonParser JSON_PARSER = new JsonParser();
  private static final String API_URL = "https://api.mojang.com/users/profiles/minecraft/";

  public static String getUUID(String name) {
    return PLAYER_UUID_MAP.computeIfAbsent(name, ignored -> {
      Player player = Bukkit.getPlayer(name);
      if (player != null) {
        return player.getUniqueId().toString().replace("-", "");
      }
      try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(API_URL + name).openStream()))) {
        JsonObject jsonObject = JSON_PARSER.parse(bufferedReader).getAsJsonObject();
        return jsonObject.get("id").getAsString();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
