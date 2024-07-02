import korlibs.korge.gradle.GameCategory
import korlibs.korge.gradle.Orientation

plugins {
    id("com.soywiz.korge") version "5.4.0"

    java
    `maven-publish`
}

val v = "0.1.0"
group = "xyz.calcugames.mathart"
version = if (project.hasProperty("snapshot")) "$v-SNAPSHOT" else v
description = "Math Art Simulator made with KorGE"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.calcugames.xyz/repository/maven-releases/")
    maven("https://repo.calcugames.xyz/repository/maven-snapshots/")
}

korge {
    id = group.toString()
    version = rootProject.version.toString()
    exeBaseName = rootProject.name
    name = rootProject.name
    description = project.description.toString()
    orientation = Orientation.LANDSCAPE

    entryPoint = "main"
    jvmMainClassName = "xyz.calcugames.mathart.MainKt"

    copyright = "Copyright (c) Calculus Games"

    authorName = "Calculus Games"
    authorEmail = "gmitch215@calcugames.xyz"
    authorHref = "https://calcugames.xyz"

    gameCategory = GameCategory.SIMULATION
    backgroundColor = 0x5813ff

    targetJvm()
    targetJs()
}

tasks {
    clean {
        delete("kotlin-js-store")
    }
}

publishing {
    publications {
        getByName<MavenPublication>("kotlinMultiplatform") {
            pom {
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }

                val github = "CalculusGames/MathArt"
                scm {
                    connection = "scm:git:git://github.com/$github.git"
                    developerConnection = "scm:git:ssh://github.com/$github.git"
                    url = "https://github.com/$github"
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }

            val releases = "https://repo.calcugames.xyz/repository/maven-releases/"
            val snapshots = "https://repo.calcugames.xyz/repository/maven-snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshots else releases)
        }
    }
}