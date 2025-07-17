plugins {
    java
    kotlin("jvm")
    `maven-publish`
}

kotlin {
    jvmToolchain(11)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${libs.versions.kotlin.get()}")
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.google.code.gson)

    testImplementation(libs.junit)
    testImplementation(libs.kluent)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)
    testImplementation("org.mockito:mockito-core:2.7.22")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.sys1yagi"
            artifactId = "mastodon4j-core"
            version = "2.0.0-SNAPSHOT"

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