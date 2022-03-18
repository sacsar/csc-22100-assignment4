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

val YOUR_NAME = "YOUR_NAME"

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.slf4j:slf4j-api:1.7.36")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("com.google.dagger:dagger:2.+")

    runtimeOnly("org.slf4j:slf4j-simple:1.7.36")

    annotationProcessor("com.google.dagger:dagger-compiler:2.+")


    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter:4.4.0")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    val uiTest by registering(Test::class) {
        useJUnitPlatform() {
            includeTags("ui")
        }
    }
    val packageAssignment by registering(Zip::class ) {
        archiveFileName.set("${project.name}-$YOUR_NAME.zip")
        from(layout.projectDirectory) {
            include("*.gradle.kts")
            include("src/**")
            include("gradle/**")
            include("gradlew*")
        }

        outputs.upToDateWhen { false }

        finalizedBy("copyZip")

    }
    val copyZip by registering(Copy::class) {
        from(packageAssignment.get().outputs)
    }

    val source2pdf by registering(JavaExec::class) {
        classpath = files("${project.projectDir}/source2pdf-all.jar")

        args = listOf("src", "-o", "${project.projectDir}/$YOUR_NAME.pdf")
    }

    "build" {
        dependsOn(uiTest)
    }
}