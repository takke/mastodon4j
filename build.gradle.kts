plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    kotlin("plugin.serialization") version libs.versions.kotlin.get() apply false
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${libs.versions.kotlin.get()}")
}

subprojects {
    // coreプロジェクトはKMPなのでjavaプラグインを適用しない
    if (name != "core") {
        apply(plugin = "java")
        
        java {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        val sourcesJar = tasks.register<Jar>("sourcesJar") {
            dependsOn("classes")
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }

        val javadocJar = tasks.register<Jar>("javadocJar") {
            dependsOn("javadoc")
            archiveClassifier.set("javadoc")
            from(tasks["javadoc"])
        }

        artifacts {
            archives(sourcesJar)
            archives(javadocJar)
        }
    }
}