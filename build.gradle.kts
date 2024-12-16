plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
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

extra["springCloudVersion"] = "2023.0.3"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
//	implementation("org.apache.kafka:kafka-streams")
//	implementation("org.jetbrains.kotlin:kotlin-reflect")
//	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.7.0")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-api:2.7.0")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.7.0")
//	implementation("org.springframework.amqp:spring-rabbit-stream")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
//	implementation("org.springframework.boot:spring-boot-starter-graphql")
//	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
//	implementation("org.springframework.cloud:spring-cloud-starter")
//	implementation("org.springframework.cloud:spring-cloud-starter-config")
//	implementation("org.springframework.cloud:spring-cloud-stream")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-rabbit")
//	implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
//	implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka-streams")
//	implementation("org.springframework.kafka:spring-kafka")

	// tests
	testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
//	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.5.4")
	testImplementation("io.mockk:mockk:1.13.13")
	testImplementation("io.projectreactor:reactor-test")
//	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
//	testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
//	testImplementation("org.springframework.graphql:spring-graphql-test")
//	testImplementation("org.springframework.kafka:spring-kafka-test")
//	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:kafka")
	testImplementation("org.testcontainers:mongodb")
	testImplementation("org.testcontainers:rabbitmq")
//	testImplementation("org.testcontainers:testcontainers")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
