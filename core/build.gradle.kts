plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    `maven-publish`
}

kotlin {
    jvmToolchain(11)
    
    jvm("android")
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${libs.versions.kotlin.get()}")
                // 既存の依存関係（段階的移行のため保持）
                implementation(libs.com.squareup.okhttp3.okhttp)
                implementation(libs.com.google.code.gson)
                // プラットフォーム固有のKtorエンジン
                implementation(libs.ktor.client.okhttp)
            }
        }
        
        val androidTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.kluent)
                implementation(libs.mockito.kotlin)
                implementation(libs.com.squareup.okhttp3.mockwebserver)
                implementation("org.mockito:mockito-core:2.7.22")
            }
        }
    }
}

repositories {
    google()
    mavenCentral()
}

publishing {
    publications {
        named<MavenPublication>("android") {
            groupId = "com.sys1yagi"
            artifactId = "mastodon4j-core"
            version = "3.0.0-SNAPSHOT"

            pom {
                name.set("mastodon4j-core")
                description.set("Mastodon4j Core Library")
                url.set("https://github.com/sys1yagi/mastodon4j")
            }
        }
    }
}