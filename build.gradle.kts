import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java")
}

group = "com.hujiayucc.chatnio"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:2.0.1")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) })
    manifest {
        val jdkVersion = org.gradle.api.JavaVersion.current()
        val javaVersion = jdkVersion.majorVersion.toString()
        val gradleVersion = project.gradle.gradleVersion

        attributes(
            mapOf(
                "Main-Class" to "com.hujiayucc.chatnio.Main",
                "Implementation-Version" to version,
                "Built-By" to "hujiayucc",
                "Built-Date" to SimpleDateFormat("yyyy-MM-dd").format(Date()),
                "JDK-Version" to jdkVersion.toString(),
                "Java-Version" to javaVersion,
                "Gradle-Version" to gradleVersion
            )
        )
    }
}
