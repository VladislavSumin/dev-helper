import ru.vs.build_script.printHelloBuildScript

group = "org.example"
version = "1.0-SNAPSHOT"

//buildscript {
//    dependencies{
//        classpath("ru.vs:buildScript:0.1.0")
//    }
//    repositories{
//        mavenLocal()
//        mavenCentral()
//        google()
//    }
//}
//
//apply{plugin("ru.vs.empty_plugin")}
plugins {
    id("ru.vs.empty_plugin")
}

printHelloBuildScript("dev-helper")