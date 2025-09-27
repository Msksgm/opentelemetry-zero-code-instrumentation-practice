import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// otel まわりで必要なパッケージ
	implementation(platform(SpringBootPlugin.BOM_COORDINATES))
	implementation(platform("io.opentelemetry:opentelemetry-bom:1.44.0"))
	implementation(platform("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom:2.10.0"))
	implementation("io.opentelemetry.instrumentation:opentelemetry-spring-boot-starter")

	// 用途
	// Propagator Distribution（tracecontext、b3）に必要
	// - https://opentelemetry.io/docs/specs/otel/context/api-propagators/#propagators-distribution
	// 変更点
	// - application.yaml（application.properties）に otel.propagators=tracecontext,b3 を追加するとき
	implementation("io.opentelemetry:opentelemetry-extension-trace-propagators")

	// 名前
	// - opentelemetry-samplers
	// Maven Repository の URL
	// - https://mvnrepository.com/artifact/io.opentelemetry.contrib/opentelemetry-samplers
	// 用途
	// - サンプリングのカスタマイズに必要
	// - このリポジトリでは、actuator の drop に使用する
	implementation("io.opentelemetry.contrib:opentelemetry-samplers:1.33.0-alpha")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// 名前
	// - spring-boot-starter-aop
	// Maven Repository の URL
	// - https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop
	// 用途
	// - アノテーションを利用したトレースに必要
	// - AOP であるため、アノテーションを付与しただけでは動作せず、Constructor Injection などでインスタンス化する必要がある
	implementation("org.springframework.boot:spring-boot-starter-aop")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
