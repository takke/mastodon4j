plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    `maven-publish`
}

kotlin {
    jvmToolchain(11)
    
    jvm("android")
    
    // iOS用の設定を追加
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.androidx.annotation)
        }
        
        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${libs.versions.kotlin.get()}")
            // 既存の依存関係（段階的移行のため保持）
            implementation(libs.com.squareup.okhttp3.okhttp)
            implementation(libs.com.google.code.gson)
            // プラットフォーム固有のKtorエンジン
            implementation(libs.ktor.client.okhttp)
        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.kluent)
            implementation(libs.mockito.kotlin)
            implementation(libs.com.squareup.okhttp3.mockwebserver)
            implementation("org.mockito:mockito-core:2.7.22")
        }

        iosMain.dependencies {
            // iOS用のKtorエンジン
            implementation(libs.ktor.client.darwin)
        }
        
        iosTest.dependencies {
            // iOS用のテスト依存関係（必要に応じて追加）
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
            artifactId = "mastodon4j-core-android"
            version = "3.0.0-SNAPSHOT"

            pom {
                name.set("mastodon4j-core-android")
                description.set("Mastodon4j Core Library for Android")
                url.set("https://github.com/sys1yagi/mastodon4j")
            }
        }
        
        // iOS用のフレームワーク出力設定
        create<MavenPublication>("ios") {
            groupId = "com.sys1yagi"
            artifactId = "mastodon4j-core-ios"
            version = "3.0.0-SNAPSHOT"

            pom {
                name.set("mastodon4j-core-ios")
                description.set("Mastodon4j Core Library for iOS")
                url.set("https://github.com/sys1yagi/mastodon4j")
            }
        }
    }
}