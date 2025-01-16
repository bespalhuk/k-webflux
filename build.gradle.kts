plugins {
	kotlin("jvm") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "bespalhuk"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.7.0")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-rabbit")

	// tests
	testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
	testImplementation("io.mockk:mockk:1.13.13")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
	testImplementation("org.testcontainers:kafka")
	testImplementation("org.testcontainers:mongodb")
	testImplementation("org.testcontainers:rabbitmq")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

configurations.all {
	exclude(group = "com.vaadin.external.google", module = "android-json")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
