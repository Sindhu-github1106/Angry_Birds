
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

public class Level2 implements Screen {
    private final Game game;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    int winScreenDraw = 0;
    private Texture bg;
    private Texture ground;
    private Texture loseScreenTexture;
    private Texture winScreenTexture;
    private Texture nextLevelTexture;
    private Texture backTexture;
    private Texture retryTexture;
    private Texture MenuTexture;
    private Texture winTexture;
    private Texture loseTexture;
    private Bird chuckBird, bombBird;
    private Bird currentBird;
    private Catapult catapult;
    Block woodOne, woodTwo;
    Pig smallpig, bigpig;
    Sprite winScreen;
    Sprite loseScreen;
    Sprite nextLevel;
    Sprite backSprite;
    Sprite retrySprite;
    Sprite Menu;
    String theme;
    Sprite winSprite;
    Sprite loseSprite;
    int nbdone=0;

    private Vector2 initialCatapultPosition;
    private float dragRadius;
    private boolean isReleased;
    private boolean isBirdMovingToCatapult;

    public Level2(Game game, String s) {
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

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(800, 500);
        ground = new Texture("ground.jpg");
        nextLevelTexture = new Texture("nextlevel.png");
        nextLevel = new Sprite(nextLevelTexture);
        nextLevel.setSize(1.4f*100,0.5f*100);

        retryTexture = new Texture("retry.png");
        retrySprite = new Sprite(retryTexture);
        retrySprite.setSize(1.4f*100,0.5f*100);


        backTexture = new Texture("back.png");
        backSprite = new Sprite(backTexture);
        backSprite.setSize(0.5f*100,0.5f*100);

        MenuTexture = new Texture("menu.png");
        Menu= new Sprite(MenuTexture);
        Menu.setSize(0.5f*100,0.5f*100);
        winScreenTexture = new Texture("winoverlay.png");
        winScreen = new Sprite(winScreenTexture);
        winScreen.setSize(2.5f*100,3.5f*100);

        loseScreenTexture = new Texture("loseoverlay.png");
        loseScreen = new Sprite(loseScreenTexture);
        loseScreen.setSize(4f*100,3.5f*100);


        chuckBird = new Bird("chuck");
        chuckBird.setTexture("chuck.png");
        chuckBird.setSize(50.0f, 50.0f);
        chuckBird.setPos(0, 122);

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

        bigpig = new Pig("big");
        bigpig.setTexture("jaitrika.png");
        bigpig.health=20;
        bigpig.setSize(.75f*100,.75f*100);

        woodTwo = new Block("wood",2);
        woodTwo.setTexture("wood2.png");
        woodTwo.setSize(1*100,.2f*100);

        woodOne = new Block("wood",1);
        woodOne.setTexture("wood1.png");
        woodOne.setSize(1f*100,1f*100);

        currentBird = null;
        isReleased = false;
        isBirdMovingToCatapult = false;
    }
    public void change(){
        game.setScreen(new Level3(this.game,theme));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        spriteBatch.draw(bg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(ground, 0, 0, viewport.getWorldWidth(), 122);

        catapult.sprite.draw(spriteBatch);


        if (chuckBird != null) chuckBird.sprite.draw(spriteBatch);
        if (bombBird != null) bombBird.sprite.draw(spriteBatch);

        if (bigpig != null) {
            bigpig.setPos(5.8f * 98, 2.35f * 100);
            bigpig.sprite.draw(spriteBatch);
        }
        nextLevel.setPosition(3.3f*100,.8f*100);
        backSprite.setPosition(2.7f*100,.8f*100);
        retrySprite.setPosition(3.3f*100,.8f*100);

        winScreen.setPosition(2.5f*100,1f*100);
        loseScreen.setPosition(2f*100,1f*100);
        Menu.setPosition(7.2f*100,4.1f*100);
        Menu.draw(spriteBatch);


        if (smallpig != null) {
            smallpig.setPos(5.8f * 100, 1.4f * 100);
            smallpig.sprite.draw(spriteBatch);
        }

        if (woodOne != null) {
            woodOne.setPos(5.5f * 100, 1.2f * 100);
            woodOne.sprite.draw(spriteBatch);
        }

        if (woodTwo != null) {
            woodTwo.setPos(5.5f * 100, 2.18f * 100);
            woodTwo.sprite.draw(spriteBatch);
        }
        if (winScreenDraw==1) {
            winScreen.draw(spriteBatch);
            nextLevel.draw(spriteBatch);
            backSprite.draw(spriteBatch);
        } else if (winScreenDraw==2){
            loseScreen.draw(spriteBatch);
            retrySprite.draw(spriteBatch);
            backSprite.draw(spriteBatch);
        }
        spriteBatch.end();
        input();
        handleInput();
        updateBirdPosition(delta);
    }
    public void input(){

        Rectangle nextLevelBounds = nextLevel.getBoundingRectangle();
        Rectangle retrySpriteBounds = retrySprite.getBoundingRectangle();
        Rectangle backSpriteBounds = backSprite.getBoundingRectangle();
        Rectangle MenuBounds = Menu.getBoundingRectangle();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            if (winScreenDraw==1){
                if (nextLevelBounds.contains(mousePos.x, 500-mousePos.y)){
                    game.setScreen(new Level3(this.game,theme));
                } else if (backSpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    game.setScreen(new LevelMap_l3unclocked(game, "night_l3unclocked.png"));//IMPLEMENT A LEVEL MAP HERE WHERE LEVEL 3 IS ALSO UNCLOCKED
                }
            } else if (winScreenDraw==2) {
                if (retrySpriteBounds.contains(mousePos.x, 500-mousePos.y)){
                    spriteBatch.dispose();
                    bg.dispose();
                    ground.dispose();
                    if (chuckBird != null) chuckBird.texture.dispose();
                    if (bombBird != null) bombBird.texture.dispose();
                    if (bigpig != null) bigpig.texture.dispose();
                    if (smallpig != null) smallpig.texture.dispose();
                    if (woodOne != null) woodOne.texture.dispose();
                    if (woodTwo != null) woodTwo.texture.dispose();
                    if (catapult != null) catapult.texture.dispose();
                    game.setScreen(new Level2(this.game,this.theme));
                } else if (backSpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    game.setScreen(new LevelMap_Resume(game, "night_resume.png"));
                }
            }
            if (MenuBounds.contains(mousePos.x,500-mousePos.y)){
                game.setScreen(new Menu(this.game,2,this.theme));

            }
        }
    }
    private void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(touchPos);

        if (Gdx.input.justTouched() && currentBird == null && !isBirdMovingToCatapult) {
            // Bird selection
            Bird selectedBird = null;
            if (chuckBird != null && chuckBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = chuckBird;
            } else if (bombBird != null && bombBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = bombBird;
            }

            if (selectedBird != null) {
                currentBird = selectedBird;
                nbdone++;
                isBirdMovingToCatapult = true;
            }
        }

        if (currentBird != null && !isReleased) {
            Vector2 center = new Vector2(initialCatapultPosition.x, initialCatapultPosition.y);
            Vector2 dragPosition = new Vector2(touchPos.x, touchPos.y);
            Vector2 offset = dragPosition.sub(center);

            if (isBirdMovingToCatapult) {
                // Move bird to catapult position
                float dx = (initialCatapultPosition.x - currentBird.posx) * 0.1f;
                float dy = (initialCatapultPosition.y - currentBird.posy) * 0.1f;
                currentBird.setPos(currentBird.posx + dx, currentBird.posy + dy);

                // Check if bird has reached catapult position
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

            // Check if bird hits ground
            if (currentBird.posy <= 122) {
                // Remove current bird
                if (currentBird == chuckBird) {
                    chuckBird.texture.dispose();
                    chuckBird = null;
                }

                else if (currentBird == bombBird) {
                    bombBird.texture.dispose();
                    bombBird=null;
                }

                currentBird = null;
                isReleased = false;

                if (smallpig==null && bigpig==null){
                    winScreenDraw=1;
                }

                // Progress to next bird or end game
                if ( chuckBird == null && bombBird == null) {
                    if (smallpig!=null || bigpig!=null) {
                        winScreenDraw=2;
                        System.out.println("Birds are finished");
                    }
                }
            }
            checkCollision();
        }
    }

    private void checkCollision() {
        if (currentBird == null) return; // No bird to check

        Rectangle birdBounds = currentBird.sprite.getBoundingRectangle();

        // Check collision with bigpig
        if (bigpig != null && birdBounds.overlaps(bigpig.sprite.getBoundingRectangle())) {

            handleCollision(bigpig);
        }

        // Check collision with smallpig
        if (smallpig != null && birdBounds.overlaps(smallpig.sprite.getBoundingRectangle())) {

            handleCollision(smallpig);
        }

        // Check collision with woodOne
        if (woodOne != null && birdBounds.overlaps(woodOne.sprite.getBoundingRectangle())) {

            handleCollision(woodOne);
        }

        // Check collision with woodTwo
        if (woodTwo != null && birdBounds.overlaps(woodTwo.sprite.getBoundingRectangle())) {

            handleCollision(woodTwo);
        }
    }

    private void handleCollision(Object target) { // Use Object or a common superclass/interface
        if (target instanceof Pig) {
            Pig pig = (Pig) target;
            if (pig == smallpig) {
                smallpig = null;
                System.out.println("Small Pig removed from the game.");
            } else if (pig == bigpig) {
                if (pig.health==20) {
                    pig.texture.dispose();
                    float posx = bigpig.posx;
                    float posy = bigpig.posy;
                    float sizex = bigpig.sizex;
                    float sizey = bigpig.sizey;
                    bigpig.setTexture("jaitrikabroken.png");
                    bigpig.setPos(posx, posy);
                    bigpig.setSize(sizex, sizey);
                    bigpig.health = 10;
                    System.out.println("Big Pig removed from the game.");
                } else if (pig.health==10 && nbdone==2){
                    pig.texture.dispose();
                    bigpig=null;
                }
            }
            // Optionally, add score increment, play sound, etc.
        } else if (target instanceof Block) {
            Block block = (Block) target;
            block.texture.dispose();
            if (block == woodOne) {
                woodOne=null;
                if (bigpig!=null){
                    bigpig.health=10;
                    float posx = bigpig.posx;
                    float posy = bigpig.posy;
                    float sizex = bigpig.sizex;
                    float sizey = bigpig.sizey;
                    bigpig.setTexture("jaitrikabroken.png");
                    bigpig.setPos(posx, posy);
                    bigpig.setSize(sizex, sizey);
                }
                if (woodTwo!=null){
                    woodTwo=null;
                }
                if (smallpig!=null){
                    smallpig=null;
                }
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
        if (currentBird == chuckBird) {
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
        spriteBatch.dispose();
        bg.dispose();
        ground.dispose();
        if (chuckBird != null) chuckBird.texture.dispose();
        if (bombBird != null) bombBird.texture.dispose();
        if (bigpig != null) bigpig.texture.dispose();
        if (smallpig != null) smallpig.texture.dispose();
        if (woodOne != null) woodOne.texture.dispose();
        if (woodTwo != null) woodTwo.texture.dispose();
        if (catapult != null) catapult.texture.dispose();
    }
}
