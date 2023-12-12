plugins {
	application
	kotlin("jvm") version "1.9.0"
}

val junitVersion = "5.6.2"

apply(plugin = "idea")

version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.toVersion("8")
	targetCompatibility = JavaVersion.toVersion("8")
}

repositories {
	mavenCentral()
}

application {
	mainClass.set("ca.kiaira.Main")
}

dependencies {
	// Java
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))
	implementation(kotlin("gradle-plugin", version = "1.5.0"))

	// Kotlin
	implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.4.2")

	// Logging
	implementation("ch.qos.logback:logback-classic:1.2.9") {
		exclude("org.slf4j", "slf4j-jdk14")
	}
	implementation("com.michael-bull.kotlin-inline-logger", "kotlin-inline-logger-jvm", "1.0.2")

	// Testing
	testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
	testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
	testImplementation("io.mockk", "mockk", "1.10.0")
}

tasks {
	compileKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
	compileTestKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
	}
}
