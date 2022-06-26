# UUIDService
A simple service to get player information.

# Integrating
```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("rip.hippo:UUIDService:2.1.1")
}
```

If you decide to shade the dependency, be sure to install the `PlayerListener` for better caching.

```java
// Plugin main
@Override
public void onEnable() {
    UUIDService.install(this);
}
``` 

# Usage
```java
// Getting player name from uuid
UUID uuid = ...;
String uuid = ...; // this may contain dashes however it is not required

Optional<String> nameOption = UUIDService.getName(uuid);

// Getting player uuid from name
String name = ...;

Optional<UUID> uuidOption = UUIDService.getUUID(name);


// Flush cache
UUIDService.flush();
```

There is also a utility method to create a `UUID` from a `UUID` string without dashes.
```java
String dashless = "f4a3dde95c00409da71d7f23507de0f2";
UUID uuid = UUIDService.createUUID(dashless);
```