plugins {
    id("java")
    id("maven-publish")
    id("io.freefair.lombok") version "6.5.0-rc1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.phrog"
description = "Core"

val pluginVersion: String by project
version = pluginVersion

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    maven("https://jitpack.io")
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
  compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT") { exclude(group = "net.kyori") }
  compileOnly("org.jetbrains:annotations:24.0.1")

  implementation("dev.jorel:commandapi-shade:8.7.6")
  implementation("net.kyori:adventure-platform-bukkit:4.3.0")
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(17)
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()  
  }

  shadowJar {
    relocate("net.kyori", "dev.phrog.core.shaded.net.kyori")
    relocate("org.jetbrains.annotations", "dev.phrog.core.shaded.org.jetbrains")
    relocate("dev.jorel.commandapi", "dev.phrog.core.shaded.commandapi")
    archiveClassifier.set("")
    manifest {
      attributes(
        mapOf(
          "Implementation-Title" to project.name,
          "Implementation-Version" to project.version,
          "Implementation-Vendor" to project.group,
          "Main-Class" to "dev.phrog.core.Core"
        )
      )
    }
    archiveFileName.set("Core-${pluginVersion}.jar")
    minimize()
  }

  compileJava.get().dependsOn(clean)
  build.get().dependsOn(shadowJar)
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components.getByName("java"))
        }
    }
}