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