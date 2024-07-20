rootProject.name = "PetMaster-OG"

// Execute bootstrap.sh
exec {
    workingDir(rootDir)
    commandLine("sh", "bootstrap.sh")
}

include("libs:MCShared-OG")
include("libs:Utilities-OG")
