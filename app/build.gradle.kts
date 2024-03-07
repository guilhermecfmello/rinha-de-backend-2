import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.br.rinhadebackend2"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring web
	implementation("org.springframework.boot:spring-boot-starter-web")
	// Actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	// Serializer
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("io.micrometer:micrometer-tracing-bridge-brave")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.7.4")

	// Validators
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
