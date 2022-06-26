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
import java.util.Optional;
import java.util.UUID;

/**
 * @author Hippo
 */
public enum UUIDService {
  ;

  private static final Map<String, UUID> PLAYER_UUID_MAP = new HashMap<>();
  private static final Map<String, String> UUID_PLAYER_MAP = new HashMap<>();
  private static final JsonParser JSON_PARSER = new JsonParser();
  private static final String NAME_API_URL = "https://api.mojang.com/users/profiles/minecraft/";
  private static final String UUID_API_URL = "https://api.mojang.com/user/profile/";

  public static Optional<UUID> getUUID(String name) {
    return Optional.ofNullable(
        PLAYER_UUID_MAP.computeIfAbsent(name, ignored -> {
          Player player = Bukkit.getPlayer(name);
          if (player != null) {
            return player.getUniqueId();
          }
          try (BufferedReader bufferedReader =
                   new BufferedReader(new InputStreamReader(new URL(NAME_API_URL + name).openStream()))) {

            JsonObject jsonObject = JSON_PARSER.parse(bufferedReader).getAsJsonObject();
            String uuid = jsonObject.get("id").getAsString();
            return createUUID(uuid);
          } catch (IOException e) {
            return null;
          }
        })
    );
  }

  public static Optional<String> getName(UUID uuid) {
    return getName(uuid.toString());
  }
  public static Optional<String> getName(String uuid) {
    UUID actualUUID = createUUID(uuid);
    String dashless = uuid.replace("-", "");
    return Optional.ofNullable(
        UUID_PLAYER_MAP.computeIfAbsent(dashless, ignored -> {
          Player player = Bukkit.getPlayer(actualUUID);
          if (player != null) {
            return player.getName();
          }

          try (BufferedReader bufferedReader =
                   new BufferedReader(new InputStreamReader(new URL(UUID_API_URL + dashless).openStream()))) {

            JsonObject jsonObject = JSON_PARSER.parse(bufferedReader).getAsJsonObject();
            return jsonObject.get("name").getAsString();
          } catch (IOException e) {
            return null;
          }
        })
    );
  }

  public static UUID createUUID(String uuid) {
    if (uuid.contains("-")) {
      return UUID.fromString(uuid);
    }
    return UUID.fromString(
        uuid.replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
            "$1-$2-$3-$4-$5"
        )
    );
  }
}
