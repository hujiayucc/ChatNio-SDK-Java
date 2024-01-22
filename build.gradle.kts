import java.io.FileInputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java")
    id("maven-publish")
    id("signing")
}

group = "io.github.hujiayucc"
version = "1.0.3"

val groupId = group
val artifactId = rootProject.name
val publishVersion = "$version"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:2.0.1")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val jdkVersion = JavaVersion.current()
val javaVersion = jdkVersion.majorVersion
val gradleVersion = project.gradle.gradleVersion

tasks.jar {
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) })
    manifest {
        attributes(
            mapOf(
                //"Main-Class" to "com.hujiayucc.chatnio.Main",
                "Implementation-Version" to version,
                "Built-By" to "hujiayucc",
                "Built-Date" to SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()),
                "JDK-Version" to jdkVersion.toString(),
                "Java-Version" to javaVersion,
                "Gradle-Version" to gradleVersion
            )
        )
    }
}

val sourcesJar = task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(project.sourceSets["main"].allSource)
    manifest {
        attributes(
            mapOf(
                "Implementation-Version" to version,
                "Built-By" to "hujiayucc",
                "Built-Date" to SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()),
                "JDK-Version" to jdkVersion.toString(),
                "Java-Version" to javaVersion,
                "Gradle-Version" to gradleVersion
            )
        )
    }
    exclude("**/Main.class")
}

val javaDocJar = task<Jar>("javaDocJar") {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
    manifest {
        attributes(
            mapOf(
                "Implementation-Version" to version,
                "Built-By" to "hujiayucc",
                "Built-Date" to SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()),
                "JDK-Version" to System.getProperty("java.version"),
                "Java-Version" to System.getProperty("java.version"),
                "Gradle-Version" to gradle.gradleVersion
            )
        )
    }
    exclude("**/Main.class")
}

ext["signing.keyId"] = ""
ext["signing.password"] = ""
ext["signing.secretKeyRingFile"] = ""
ext["ossrhUsername"] = ""
ext["ossrhPassword"] = ""

val secretPropsFile = project.rootProject.file("local.properties")
val p = Properties()
if (secretPropsFile.exists()) {
    p.load(FileInputStream(secretPropsFile))
    p.forEach { name, value ->
        ext[name.toString()] = value
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = groupId
            artifactId = artifactId
            version = publishVersion

            artifact(tasks.jar)
            artifact(sourcesJar)
            artifact(javaDocJar)

            pom {
                name.set(artifactId)
                description.set("ChatNio SDK")
                url.set("https://github.com/hujiayucc/ChatNio-SDK-Java")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                packaging = "jar"

                developers {
                    developer {
                        id.set("hujiayucc")
                        name.set("hujiayucc")
                        email.set("hujiayucc@qq.com")
                    }
                }

                scm {
                    connection.set("scm:git:github.com/hujiayucc/ChatNio-SDK-Java.git")
                    developerConnection.set("scm:git:ssh://github.com/hujiayucc/ChatNio-SDK-Java.git")
                    url.set("https://github.com/hujiayucc/ChatNio-SDK-Java/tree/master")
                }

                withXml {
                    asNode().appendNode("dependencies").let { dependenciesNode ->
                        configurations.getByName("implementation").allDependencies.forEach {
                            dependenciesNode.appendNode("dependency").apply {
                                appendNode("groupId", it.group)
                                appendNode("artifactId", it.name)
                                appendNode("version", it.version)
                            }
                        }
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "mavenCentral"
            val releasesRepoUrl = URI("https://s01.oss.sonatype.org/content/repositories/releases/")
            val snapshotsRepoUrl = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = p["ossrhUsername"].toString()
                password = p["ossrhPassword"].toString()
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
