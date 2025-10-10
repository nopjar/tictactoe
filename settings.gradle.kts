rootProject.name = "tictactoe"

include(":tictactoe-lib")
project(":tictactoe-lib").projectDir = file("lib")

include(":tictactoe-app")
project(":tictactoe-app").projectDir = file("app")