import ru.vs.build_script.printHelloBuildScript

group = "org.example"
version = "1.0-SNAPSHOT"


plugins {
    id("empty_plugin")
}

printHelloBuildScript("dev-helper")