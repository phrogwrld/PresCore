package dev.phrog.prescore;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.experimental.Accessors;

public class PresCore extends JavaPlugin {

  @Getter
  @Accessors(fluent = true)
  private static PresCore instance;

  @Override
  public void onEnable() {
    // Plugin startup logic
    getLogger().info("PresCore has been enabled!");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

}