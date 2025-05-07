# Lightning Strike

A classic 2D space shooter game built with Java Swing.

## Game Features

- Player-controlled spaceship with smooth movement
- Enemy ships with random movement patterns
- Shooting mechanics for both player and enemies
- Health system with visual health bar
- Score tracking system
- Background music
- Explosion effects
- Game over screen with final score

## Controls

- **Arrow Keys**: Move the spaceship
  - Up: Move up
  - Down: Move down
  - Left: Move left
  - Right: Move right
- **Z Key**: Shoot bullets

## Game Mechanics

- Player starts with 5 health points
- Each enemy bullet hit reduces 1 health point
- Direct collision with enemy ships results in instant game over
- Score increases by 1 for each enemy destroyed
- Game ends when health reaches 0 or player collides with an enemy

## Requirements

- Java Runtime Environment (JRE) 8 or higher
- Required files in resources folder:
  - `player.png`: Player spaceship image
  - `enemy.png`: Enemy spaceship image
  - `explosion.png`: Explosion effect image
  - `bgm.wav`: Background music file

## How to Run

1. Make sure you have Java installed on your system
2. Compile all Java files:
   ```
   javac *.java
   ```
3. Run the game:
   ```
   java GameFrame
   ```

## File Structure

- `GameFrame.java`: Main window frame
- `GamePanel.java`: Main game panel with game logic
- `Player.java`: Player spaceship class
- `Enemy.java`: Enemy spaceship class
- `Bullet.java`: Player bullet class
- `EnemyBullet.java`: Enemy bullet class
- `AudioManager.java`: Background music manager

## Development

This game was developed using:
- Java Swing for graphics
- Java Sound API for audio
- Object-oriented programming principles

## Future Improvements

- Add different types of enemies
- Implement power-ups
- Add high score system
- Add different levels
- Add sound effects
- Add pause menu
- Add difficulty levels 