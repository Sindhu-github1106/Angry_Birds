package io.github.some_example_name;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.Classes.Bird;
import io.github.some_example_name.Classes.Block;
import io.github.some_example_name.Classes.Catapult;
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
import io.github.some_example_name.Classes.Pig;

public class Level1 implements Screen {
    private final Game game;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private int gameState = 0; // 0 = playing, 1 = win, 2 = lose
    private Texture bg;
    private Texture ground;
    private Texture winScreenTexture;
    private Texture loseScreenTexture;
    private Texture nextLevelTexture;
    private Texture backTexture;
    private Texture retryTexture;
    private Texture MenuTexture;
    private Bird redBird, bombBird;
    private Bird currentBird;
    private Catapult catapult;
    Block glassOne;
    Pig smallpig;
    Sprite winScreen;
    Sprite loseScreen;
    Sprite nextLevel;
    Sprite backSprite;
    Sprite retrySprite;
    Sprite Menu;
    public String theme;
    private Vector2 initialCatapultPosition;
    private float dragRadius;
    private boolean isReleased;
    private boolean isBirdMovingToCatapult;
    public Level1(Game game, String s) {
        this.game = game;
        if (s.equals("night.png") || s.equals("night_resume.png")) {
            this.bg = new Texture("night.jpg");
        } else if (s.equals("day.png") || s.equals("day_resume.png")){
            this.bg = new Texture("bg.jpg");
        } else if (s.equals("spooky.png") || s.equals("spooky_resume.png")){
            this.bg = new Texture("halloween.png");
        }
        this.theme = s;
        this.initialCatapultPosition = new Vector2(100, 235);
        this.dragRadius = 50;
    }
    public String getLevelMapStartTheme(String theme) {
        String map_theme = "";
        if (theme.equals("night.jpg")) {
            map_theme = "night.png";
        } else if (theme.equals("bg.jpg")) {
            map_theme = "day.png";
        } else if (theme.equals("halloween.jpg")) {
            map_theme = "spooky.png";
        }
        return map_theme;
    }
    public String getLevelMapResumeTheme(String theme){
        String map_theme = "";
        if (theme.equals("night.jpg")){
            map_theme="night_resume.png";
        }
        else if(theme.equals("bg.jpg")){
            map_theme="day_resume.png";
        }
        else if(theme.equals("halloween.jpg")){
            map_theme="spooky_resume.png";
        }
        return map_theme;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 500);
        ground = new Texture("ground.jpg");

        nextLevelTexture = new Texture("nextlevel.png");
        nextLevel = new Sprite(nextLevelTexture);
        nextLevel.setSize(1.4f*100,0.5f*100);

        backTexture = new Texture("back.png");
        backSprite = new Sprite(backTexture);
        backSprite.setSize(0.5f*100,0.5f*100);

        retryTexture = new Texture("retry.png");
        retrySprite = new Sprite(retryTexture);
        retrySprite.setSize(1.4f*100,0.5f*100);

        MenuTexture = new Texture("menu.png");
        Menu = new Sprite(MenuTexture);
        Menu.setSize(0.7f*100,0.7f*100);

        winScreenTexture = new Texture("winoverlay.png");
        winScreen = new Sprite(winScreenTexture);
        winScreen.setSize(2.5f*100,3.5f*100);

        loseScreenTexture = new Texture("loseoverlay.png");
        loseScreen = new Sprite(loseScreenTexture);
        loseScreen.setSize(4f*100,3.5f*100);

        // Initialize birds, catapult, blocks, and pigs
        redBird = new Bird("red");
        redBird.setTexture("birdred.png");
        redBird.setSize(50.0f, 50.0f);
        redBird.setPos(0, 122);

        bombBird = new Bird("bomb");
        bombBird.setTexture("bomb.png");
        bombBird.setSize(50.0f, 50.0f);
        bombBird.setPos(60, 122);

        catapult = new Catapult();
        catapult.setTexture("catapult.png");
        catapult.setSize(75.0f, 150.0f);
        catapult.setPos(1f * 100, 1.20f * 100);

        smallpig = new Pig("small");
        smallpig.setTexture("small.png");
        smallpig.setSize(.40f*100,.40f*100);

        glassOne = new Block("glass",1);
        glassOne.setTexture("glass1.png");
        glassOne.setSize(1f*150,1f*150);
        glassOne.health=2;

        currentBird = null;
        isReleased = false;
        isBirdMovingToCatapult = false;
    }

    public void change(){
        game.setScreen(new Level2(this.game,theme));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // Draw background and ground
        spriteBatch.draw(bg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(ground, 0, 0, viewport.getWorldWidth(), 122);

        // Draw game objects
        catapult.sprite.draw(spriteBatch);

        if (redBird != null) redBird.sprite.draw(spriteBatch);
        if (bombBird != null) bombBird.sprite.draw(spriteBatch);

        if (smallpig != null) {
            smallpig.setPos(5.8f*105,1.4f*108);
            smallpig.sprite.draw(spriteBatch);
        }

        if (glassOne != null) {
            glassOne.setPos(5.5f*100,1.2f*100);
            glassOne.sprite.draw(spriteBatch);
        }

        Menu.setPosition(0.2f*100,4.1f*100);
        Menu.draw(spriteBatch);

        if (gameState == 1) {
            winScreen.setPosition(2.5f*100,1f*100);
            winScreen.draw(spriteBatch);
            nextLevel.setPosition(3.3f*100,.8f*100);
            nextLevel.draw(spriteBatch);
            backSprite.setPosition(2.7f*100,.8f*100);
            backSprite.draw(spriteBatch);
        } else if (gameState == 2) { // Lose state
            loseScreen.setPosition(2f*100,1f*100);
            loseScreen.draw(spriteBatch);
            retrySprite.setPosition(3.3f*100,.8f*100);
            retrySprite.draw(spriteBatch);
            backSprite.setPosition(2.7f*100,.8f*100);
            backSprite.draw(spriteBatch);
        }

        spriteBatch.end();

        input();
        handleInput();
        updateBirdPosition(delta);
        checkCollisions();
        checkGameState();
    }
    private void checkCollisions() {
        if (currentBird == null || !isReleased) return;

        Rectangle birdBounds = currentBird.sprite.getBoundingRectangle();

        if (glassOne != null) {
            Rectangle glassOneBounds = glassOne.sprite.getBoundingRectangle();
            if (birdBounds.overlaps(glassOneBounds)) {
                if (glassOne.health == 2 && currentBird == redBird) {
                    glassOne.health -= 1; // Reduce health to 1
                    glassOne.setTexture("glass1broken.png"); // Change texture to broken glass
                    currentBird.damage -= 1; // Use up red bird's damage

                    // Destroy red bird since its damage is used up
                    destroyBird();
                }
                // If glass block has 1 health and bomb bird hits it
                else if (glassOne.health == 1 && currentBird == bombBird) {
                    glassOne.health -= 1; // Reduce health to 0
                    glassOne.texture.dispose(); // Dispose of texture
                    glassOne = null; // Remove glass block
                    currentBird.damage -= 1; // Use up one damage

                    // If bomb bird used both its damages, destroy it
                    if (currentBird.damage <= 0) {
                        destroyBird();
                    }
                }
                // If glass block has 2 health and bomb bird hits it
                else if (glassOne.health == 2 && currentBird == bombBird) {
                    glassOne.health -= 2; // Directly reduce to 0
                    glassOne.texture.dispose(); // Dispose of texture
                    glassOne = null; // Remove glass block
                    destroyBird(); // Destroy bomb bird since it used both damages
                }
            }
        }

        if (smallpig != null && (glassOne == null)) {
            Rectangle pigBounds = smallpig.sprite.getBoundingRectangle();
            if (birdBounds.overlaps(pigBounds)) {
                // Reduce pig health based on bird damage
                smallpig.health -= currentBird.damage;

                // Destroy pig if its health reaches 0
                if (smallpig.health <= 0) {
                    smallpig.texture.dispose();
                    smallpig = null;
                }

                // Destroy bird
                destroyBird();
            }
        }
    }
    private void checkGameState() {
        // Win condition: all pigs destroyed
        if (smallpig == null) {
            gameState = 1;
        }
        // Lose condition: all birds used and pig still alive
        else if ((redBird == null && bombBird == null)) {
            gameState = 2;
        }
    }
    private void destroyBird() {
        if (currentBird == redBird) {
            redBird.texture.dispose();
            redBird = null;
        } else if (currentBird == bombBird) {
            bombBird.texture.dispose();
            bombBird = null;
        }
        currentBird = null;
        isReleased = false;
    }


    public void input() {
        Rectangle nextLevelBounds = nextLevel.getBoundingRectangle();
        Rectangle retrySpriteBounds = retrySprite.getBoundingRectangle();
        Rectangle backSpriteBounds = backSprite.getBoundingRectangle();
        Rectangle MenuBounds = Menu.getBoundingRectangle();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            if (gameState == 1) {
                if (nextLevelBounds.contains(mousePos.x, 500-mousePos.y)) {
                    game.setScreen(new Level2(this.game, theme));
                } else if (backSpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    String them = getLevelMapResumeTheme(String.valueOf(this.bg));
                    game.setScreen(new LevelMap_Resume(game, them));
                }
            } else if (gameState == 2) {
                if (retrySpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    game.setScreen(new Level1(this.game, this.theme));
                } else if (backSpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    String them_2 = getLevelMapStartTheme(String.valueOf(this.bg));
                    game.setScreen(new LevelMap_Start(game, them_2));
                }
            }

            if (MenuBounds.contains(mousePos.x, 500-mousePos.y)) {
                game.setScreen(new Menu(this.game, 1, this.theme));
            }
        }
    }
    private void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);

        if (Gdx.input.justTouched() && currentBird == null && !isBirdMovingToCatapult) {

            Bird selectedBird = null;
            if (redBird != null && redBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = redBird;

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
            Vector2 offset = dragPosition.sub(center);

            if (isBirdMovingToCatapult) {
                float dx = (initialCatapultPosition.x - currentBird.posx) * 0.1f;
                float dy = (initialCatapultPosition.y - currentBird.posy) * 0.1f;
                currentBird.setPos(currentBird.posx + dx, currentBird.posy + dy);

                if (Math.abs(currentBird.posx - initialCatapultPosition.x) < 1 &&
                    Math.abs(currentBird.posy - initialCatapultPosition.y) < 1) {
                    isBirdMovingToCatapult = false;
                }
            } else {
                if (offset.len() > dragRadius) {
                    offset.nor().scl(dragRadius);
                }

                Vector2 clampedPosition = center.add(offset);
                currentBird.setPos(clampedPosition.x - currentBird.sprite.getWidth() / 2,
                    clampedPosition.y - currentBird.sprite.getHeight() / 2);
                currentBird.isDragged = true;

                if (!Gdx.input.isTouched() && currentBird.isDragged) {
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
            currentBird.velocity.y -= 105 * delta;
            currentBird.setPos(currentBird.posx + currentBird.velocity.x * delta,
                currentBird.posy + currentBird.velocity.y * delta);

            if (currentBird.posy <= 122) {
                if (currentBird == redBird) {
                    redBird.texture.dispose();
                    redBird = null;
                }

                else if (currentBird == bombBird) {
                    bombBird.texture.dispose();
                    bombBird=null;
                }

                currentBird = null;
                isReleased = false;

                if (redBird == null && bombBird == null) {
                    System.out.println("Birds are finished");
                }
            }
        }
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
        spriteBatch.dispose();
        if (bg != null) bg.dispose();
        if (ground != null) ground.dispose();
    }
}
