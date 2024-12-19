plugins {
    id("java")
    id("org.springframework.boot") version "3.1.4" // Spring Boot 플러그인 추가
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.9.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // Spring Boot Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Boot JPA (Data Access)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Thymeleaf (컨트롤러 작동)
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // H2 Database (테스트용)
    runtimeOnly("com.h2database:h2:2.2.224")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Kotlin 기본 의존성
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework:spring-tx:6.0.12")
    // 테스트 라이브러리
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        // Spring Boot 의존성 BOM
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.1.4")
    }
}

tasks.test {
    useJUnitPlatform()
}
