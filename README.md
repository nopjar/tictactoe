# TicTacToe

A modular TicTacToe implementation for Java 21.
The lib module contains the reusable core API and domain logic. 
The app module is a minimal console example showing how to use the library.  
(This project purely serves as my submission for an application at Jetbrains.)

## Features
- Clean API for board, game, moves
- Pluggable input handlers and renderers
- Human and AI players (easy to add more)
- Simple state machine and rules abstraction (easy to add more states)
- Unit tests for core components

## Modules
- lib: Core API and domain (intended for reuse)
- app: Example console runner

## Build
- Unix/macOS: `./gradlew build`
- Windows: `gradlew.bat build`

## Run example (app)
- Unix/macOS: `./gradlew :app:run`
- Windows: `gradlew.bat :app:run`

## Use the library
- Add the lib module as a dependency in your project.
- Implement your own InputHandler/Renderer if needed.
- Choose player types (human/AI) and orchestrate a Game via the API.

## Tests
- `./gradlew test`

## License
MIT — see LICENSE.
