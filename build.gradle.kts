import com.vanniktech.maven.publish.SonatypeHost

plugins {
    java
    id("java-library")
    id("com.vanniktech.maven.publish") version "0.29.0"
}
group = "io.github.lontten"
version = "0.0.5.RELEASE"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.FAIL
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(rootProject.group.toString(), rootProject.name, rootProject.version.toString())
    pom {
        name = rootProject.name
        description = "lontten-utils"
        inceptionYear = "2024"
        url = "https://github.com/lontten/lontten-utils/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "lontten"
                name = "lontten"
                url = "https://github.com/lontten/"
            }
        }
        scm {
            url = "https://github.com/lontten/lontten-utils/"
            connection = "scm:git:git://github.com/lontten/lontten-utils.git"
            developerConnection = "scm:git:ssh://github.com:lontten/lontten-utils.git"
        }
    }
}


repositories {
    maven { url = uri("https://repo.maven.apache.org/maven2") }
    maven { url = uri("https://jitpack.io") }
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}
dependencies {
    api(libs.lontten.common)

    // springboot
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.18")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.7.18")
    implementation("org.springframework.security:spring-security-crypto:6.3.0")

    // es8
    implementation("co.elastic.clients:elasticsearch-java:8.15.3")
    // mybatis-plus
    implementation("com.baomidou:mybatis-plus:3.5.7")
    implementation("cn.hutool:hutool-all:5.8.25")

    implementation(libs.bundles.jackson)

    // test
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
