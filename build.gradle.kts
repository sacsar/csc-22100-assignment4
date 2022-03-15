plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

repositories {
    mavenCentral()
    google()
}

application {
    mainClass.set("csc22100.assignment4.GameOfLifeApplication")
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.slf4j:slf4j-api:1.7.36")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("com.google.dagger:dagger:2.+")

    runtimeOnly("org.slf4j:slf4j-simple:1.7.36")

    annotationProcessor("com.google.dagger:dagger-compiler:2.+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
