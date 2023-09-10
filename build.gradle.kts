val jvmVersion = "17"
val kotestVersion = "5.7.2"

plugins {
    kotlin("jvm") version "1.9.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("io.kotest", "kotest-runner-junit5", kotestVersion)
    testImplementation("io.kotest", "kotest-property", kotestVersion)
    testImplementation("io.kotest", "kotest-assertions-core", kotestVersion)
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = jvmVersion
        }
    }
    compileTestKotlin {
        kotlinOptions{
            jvmTarget = jvmVersion
        }
    }
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
