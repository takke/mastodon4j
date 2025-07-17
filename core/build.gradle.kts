plugins {
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    jvmToolchain(11)
    
    jvm("android")
    
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${libs.versions.kotlin.get()}")
                implementation(libs.com.squareup.okhttp3.okhttp)
                implementation(libs.com.google.code.gson)
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
        create<MavenPublication>("maven") {
            groupId = "com.sys1yagi"
            artifactId = "mastodon4j-core"
            version = "3.0.0-SNAPSHOT"

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("mastodon4j-core")
                description.set("Mastodon4j Core Library")
                url.set("https://github.com/sys1yagi/mastodon4j")
            }
        }
    }
}