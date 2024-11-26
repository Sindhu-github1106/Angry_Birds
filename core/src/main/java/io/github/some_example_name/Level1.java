package io.github.some_example_name;

import io.github.some_example_name.Classes.Bird;
import io.github.some_example_name.Classes.Block;
import io.github.some_example_name.Classes.Catapult;
import io.github.some_example_name.Classes.Pig;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Rectangle;

public class Level1 implements Screen {
    private final Game game;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Texture bg;
    private Texture ground;
    private Bird redBird, chuckBird, bombBird;
    private Bird currentBird;
    private Catapult catapult;
    private Block woodOne, woodTwo;
    private Pig smallpig, bigpig;

    String theme;

    private Vector2 initialCatapultPosition;
    private float dragRadius;
    private boolean isReleased;
    private boolean isBirdMovingToCatapult;

    public Level1(Game game, String s) {
        this.game = game;
        if (s.equals("night.png") || s.equals("night_resume.png")) {
            this.bg = new Texture("night.jpg");
        } else if (s.equals("day.png") || s.equals("day_resume.png")) {
            this.bg = new Texture("bg.jpg");
        } else if (s.equals("spooky.png") || s.equals("spooky_resume.png")) {
            this.bg = new Texture("halloween.png");
        }

        this.theme = s;
        this.initialCatapultPosition = new Vector2(100, 235);
        this.dragRadius = 50;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 500);
        ground = new Texture("ground.jpg");

        // Initialize Birds
        redBird = new Bird("red");
        redBird.setTexture("birdred.png");
        redBird.setSize(50.0f, 50.0f);
        redBird.setPos(0, 122);

        chuckBird = new Bird("chuck");
        chuckBird.setTexture("chuck.png");
        chuckBird.setSize(50.0f, 50.0f);
        chuckBird.setPos(50, 122);

        bombBird = new Bird("bomb");
        bombBird.setTexture("bomb.png");
        bombBird.setSize(50.0f, 50.0f);
        bombBird.setPos(100, 122);

        // Initialize Catapult
        catapult = new Catapult();
        catapult.setTexture("catapult.png");
        catapult.setSize(75.0f, 150.0f);
        catapult.setPos(100, 120);

        // Initialize Pigs
        smallpig = new Pig("small");
        smallpig.setTexture("small.png");
        smallpig.setSize(.40f * 100, .40f * 100);
        smallpig.setPos(580, 140); // Position (580, 140)

        bigpig = new Pig("big");
        bigpig.setTexture("jaitrika.png");
        bigpig.setSize(.75f * 100, .75f * 100);
        bigpig.setPos(580, 235); // Position (580, 235)

        // Initialize Blocks
        woodTwo = new Block("wood", 2);
        woodTwo.setTexture("wood2.png");
        woodTwo.setSize(100, 20);
        woodTwo.setPos(550, 218); // Position (550, 218)

        woodOne = new Block("wood", 1);
        woodOne.setTexture("wood1.png");
        woodOne.setSize(100, 100);
        woodOne.setPos(550, 120); // Position (550, 120)

        currentBird = null;
        isReleased = false;
        isBirdMovingToCatapult = false;
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);

        // Apply the viewport and set up the sprite batch
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // Draw Background and Ground
        spriteBatch.draw(bg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(ground, 0, 0, viewport.getWorldWidth(), 122);

        // Draw Catapult
        if (catapult != null) {
            catapult.sprite.draw(spriteBatch);
        }

        // Draw Birds
        if (redBird != null) redBird.sprite.draw(spriteBatch);
        if (chuckBird != null) chuckBird.sprite.draw(spriteBatch);
        if (bombBird != null) bombBird.sprite.draw(spriteBatch);

        // Draw Pigs
        if (bigpig != null) {
            bigpig.sprite.draw(spriteBatch);
        }

        if (smallpig != null) {
            smallpig.sprite.draw(spriteBatch);
        }

        // Draw Blocks
        if (woodOne != null) {
            woodOne.sprite.draw(spriteBatch);
        }

        if (woodTwo != null) {
            woodTwo.sprite.draw(spriteBatch);
        }

        spriteBatch.end();

        // Handle Input and Update Bird Position
        handleInput();
        updateBirdPosition(delta);
    }

    private void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);

        if (Gdx.input.justTouched() && currentBird == null && !isBirdMovingToCatapult) {
            // Bird selection
            Bird selectedBird = null;
            if (redBird != null && redBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = redBird;
            } else if (chuckBird != null && chuckBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = chuckBird;
            } else if (bombBird != null && bombBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = bombBird;
            }

            if (selectedBird != null) {
                currentBird = selectedBird;
                isBirdMovingToCatapult = true;
            }
        }

        if (currentBird != null && !isReleased) {
            Vector2 center = new Vector2(initialCatapultPosition.x, initialCatapultPosition.y);
            Vector2 dragPosition = new Vector2(touchPos.x, touchPos.y);
            Vector2 offset = dragPosition.cpy().sub(center); // Use cpy() to avoid modifying dragPosition

            if (isBirdMovingToCatapult) {
                // Move bird to catapult position smoothly
                float dx = (initialCatapultPosition.x - currentBird.posx) * 0.1f;
                float dy = (initialCatapultPosition.y - currentBird.posy) * 0.1f;
                currentBird.setPos(currentBird.posx + dx, currentBird.posy + dy);

                // Check if bird has reached catapult position
                if (Math.abs(currentBird.posx - initialCatapultPosition.x) < 1 &&
                    Math.abs(currentBird.posy - initialCatapultPosition.y) < 1) {
                    isBirdMovingToCatapult = false;
                }
            } else {
                // Handle dragging within drag radius
                if (offset.len() > dragRadius) {
                    offset.nor().scl(dragRadius);
                }

                Vector2 clampedPosition = center.cpy().add(offset);
                currentBird.setPos(clampedPosition.x - currentBird.sprite.getWidth() / 2,
                    clampedPosition.y - currentBird.sprite.getHeight() / 2);
                currentBird.isDragged = true;

                if (!Gdx.input.isTouched() && currentBird.isDragged) {
                    // Launch the bird
                    currentBird.velocity.set(initialCatapultPosition.x - currentBird.posx,
                        initialCatapultPosition.y - currentBird.posy).scl(3);
                    isReleased = true;
                    currentBird.isDragged = false;
                }
            }
        }
    }

    private void updateBirdPosition(float delta) {
        if (isReleased && currentBird != null) {
            // Apply gravity
            currentBird.velocity.y -= 105 * delta;
            // Update bird's position
            currentBird.setPos(currentBird.posx + currentBird.velocity.x * delta,
                currentBird.posy + currentBird.velocity.y * delta);

            // Check if bird hits ground
            if (currentBird.posy <= 122) {
                // Remove current bird
                removeCurrentBird();

                // Progress to next bird or end game
                if (redBird == null && chuckBird == null && bombBird == null) {
                    System.out.println("Birds are finished");
                    // Optionally, trigger end-of-level logic here
                }
            }

            // **Check for collisions after updating position**
            checkCollision();
        }
    }

    private void checkCollision() {
        if (currentBird == null) return; // No bird to check

        Rectangle birdBounds = currentBird.sprite.getBoundingRectangle();

        // Check collision with bigpig
        if (bigpig != null && birdBounds.overlaps(bigpig.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Big Pig!");
            handleCollision(bigpig);
        }

        // Check collision with smallpig
        if (smallpig != null && birdBounds.overlaps(smallpig.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Small Pig!");
            handleCollision(smallpig);
        }

        // Check collision with woodOne
        if (woodOne != null && birdBounds.overlaps(woodOne.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block One!");
            handleCollision(woodOne);
        }

        // Check collision with woodTwo
        if (woodTwo != null && birdBounds.overlaps(woodTwo.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block Two!");
            handleCollision(woodTwo);
        }
    }

    private void handleCollision(Object target) { // Use Object or a common superclass/interface
        if (target instanceof Pig) {
            Pig pig = (Pig) target;
            pig.texture.dispose();
            if (pig == smallpig) {
                smallpig = null;
                System.out.println("Small Pig removed from the game.");
            } else if (pig == bigpig) {
                bigpig = null;
                System.out.println("Big Pig removed from the game.");
            }
            // Optionally, add score increment, play sound, etc.
        } else if (target instanceof Block) {
            Block block = (Block) target;
            block.texture.dispose();
            if (block == woodOne) {
                woodOne = null;
                System.out.println("Wood Block One removed from the game.");
            } else if (block == woodTwo) {
                woodTwo = null;
                System.out.println("Wood Block Two removed from the game.");
            }
            // Similarly, handle other block types if any
        }

        // **Do not stop the bird's movement upon collision**
        // The bird will continue its trajectory until it hits the ground
    }

    private void removeCurrentBird() {
        if (currentBird == redBird) {
            redBird.texture.dispose();
            redBird = null;
            System.out.println("Red Bird removed from the game.");
        } else if (currentBird == chuckBird) {
            chuckBird.texture.dispose();
            chuckBird = null;
            System.out.println("Chuck Bird removed from the game.");
        } else if (currentBird == bombBird) {
            bombBird.texture.dispose();
            bombBird = null;
            System.out.println("Bomb Bird removed from the game.");
        }

        currentBird = null;
        isReleased = false;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose only initialized resources
        spriteBatch.dispose();
        bg.dispose();
        ground.dispose();
        if (redBird != null) redBird.texture.dispose();
        if (chuckBird != null) chuckBird.texture.dispose();
        if (bombBird != null) bombBird.texture.dispose();
        if (bigpig != null) bigpig.texture.dispose();
        if (smallpig != null) smallpig.texture.dispose();
        if (woodOne != null) woodOne.texture.dispose();
        if (woodTwo != null) woodTwo.texture.dispose();
        if (catapult != null) catapult.texture.dispose();
    }
}
