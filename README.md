# Battleship GUI

## Overview
While reading through "Head First Java" by B. Bates and K. Sierra I followed along with a Battleship-like game in the console. This game was one player and had a smaller grid size than the traditional board game. I wanted to try and recreate a two-player Battleship using the Java Swing library. The project in the Battleship GUI directory is the result.
## Purpose
The purpose of this project is to strengthen OOP and DSA principles. It also required designing a clean user-friendly interface. I learned how to implement a classic TCP chat server that includes multi-threading to allow player clients to communicate with each other. 
## Usage
The BattleshipServer class's main method must be ran first. Two instances of the BattleshipClient class's main method must be ran to connect to the server. Before starting, each player must plot their respective fleet of ships on the right grid and click place. From there, the game is played just as the classic Battleship board game by making a guess and submitting it.
### Status
This project is functional but a few features are still in progress:
- Turn order enforcement
- Game completed end processes