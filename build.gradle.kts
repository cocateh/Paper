plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    id("io.papermc.paperweight.core") version "1.1.7-LOCAL-SNAPSHOT"
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(8)
    }

    if (name == "Paper-MojangAPI") {
        return@subprojects
    }

    repositories {
        mavenCentral()
        maven("https://repo1.maven.org/maven2/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://ci.emc.gs/nexus/content/groups/aikar/")
        maven("https://repo.md-5.net/content/repositories/releases/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    }
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            onlyForConfigurations("paperclip")
        }
    }
    maven("https://maven.quiltmc.org/repository/release/") {
        content {
            onlyForConfigurations("paramMappings", "remapper")
        }
    }
    maven("https://files.minecraftforge.net/maven/") {
        content {
            onlyForConfigurations("decompiler")
        }
    }
}

dependencies {
    paramMappings("org.quiltmc:yarn:1.16.5+build.6:mergedv2")
    remapper("org.quiltmc:tiny-remapper:0.4.1")
    decompiler("net.minecraftforge:forgeflower:1.5.498.12")
    paperclip("io.papermc:paperclip:2.0.1")
}

paperweight {
    minecraftVersion.set(providers.gradleProperty("mcVersion"))
    serverProject.set(project(":Paper-Server"))

    paper {
        spigotApiPatchDir.set(layout.projectDirectory.dir("Spigot-API-Patches"))
        spigotServerPatchDir.set(layout.projectDirectory.dir("Spigot-Server-Patches"))

        mappingsPatch.set(layout.projectDirectory.file("build-data/mappings-patch.tiny"))

        additionalSpigotMemberMappings.set(layout.projectDirectory.file("build-data/additional-spigot-member-mappings.csrg"))
        craftBukkitPatchPatchesDir.set(layout.projectDirectory.dir("build-data/craftbukkit-patch-patches"))
    }
}
