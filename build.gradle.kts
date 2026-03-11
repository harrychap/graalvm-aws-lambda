plugins {
    id("java")
    id("org.graalvm.buildtools.native") version "0.11.5"
}

group = "org.local"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-core:1.4.0")
    implementation("com.amazonaws:aws-lambda-java-runtime-interface-client:2.4.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("runtime")
            mainClass.set("com.amazonaws.services.lambda.runtime.api.client.AWSLambda")
            buildArgs.addAll(
                "--no-fallback",
                "--enable-url-protocols=http",
                "--initialize-at-build-time=org.slf4j",
                "-H:+AllowIncompleteClasspath",
                "-H:+ReportExceptionStackTraces"
            )
        }
    }
    metadataRepository {
        enabled.set(true)
    }
}

tasks.register<Jar>("fatJar") {
    archiveClassifier.set("all")
    from(sourceSets.main.get().output)
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Zip>("packageLambda") {
    dependsOn("nativeCompile")
    archiveFileName.set("lambda.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
    from(layout.buildDirectory.dir("native/nativeCompile")) {
        include("runtime")
        filePermissions { unix("755") }
    }
    from("src/main/config") {
        include("bootstrap")
        filePermissions { unix("755") }
    }
}