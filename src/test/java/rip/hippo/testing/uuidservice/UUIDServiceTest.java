package rip.hippo.testing.uuidservice;

import org.bukkit.Bukkit;
import org.junit.jupiter.api.Test;
import rip.hippo.uuidservice.UUIDService;

import java.util.UUID;

/**
 * @author Hippo
 */
public final class UUIDServiceTest {

  @Test
  public void test() {
    Bukkit.setServer(new MockServer());
    UUID uuid = UUID.fromString("f4a3dde9-5c00-409d-a71d-7f23507de0f2");
    UUIDService.getName(uuid).ifPresent(username -> {
      System.out.println(username);
      UUIDService.getUUID(username).ifPresent(System.out::println);
    });
  }
}
