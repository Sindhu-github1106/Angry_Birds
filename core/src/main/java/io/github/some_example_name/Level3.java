
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

public class Level3 implements Screen {
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
    private Bird redBird, chuckBird, bombBird;
    private Bird currentBird;
    private Catapult catapult;

    Block glassOne;
    Block glassTwo;
    Block glassThree;
    Block stoneOne;
    Block woodTwo;
    Block woodThree;
    Block woodOne;
    Pig smallpig;
    Pig smallpig2;
    Pig bigpig;
    Pig kingpig;
    Sprite winScreen;
    Sprite loseScreen;
    Sprite nextLevel;
    Sprite backSprite;
    Sprite retrySprite;
    Sprite Menu;
    String theme;
    Sprite winSprite;
    Sprite loseSprite;
    public int nbdone;

    private Vector2 initialCatapultPosition;
    private float dragRadius;
    private boolean isReleased;
    private boolean isBirdMovingToCatapult;

    public Level3(Game game, String s) {
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

        redBird = new Bird("red");
        redBird.setTexture("birdred.png");
        redBird.setSize(50.0f, 50.0f);
        redBird.setPos(0, 122);
        redBird.damage=1;

        chuckBird = new Bird("chuck");
        chuckBird.setTexture("chuck.png");
        chuckBird.setSize(50.0f, 50.0f);
        chuckBird.setPos(60, 122);
        chuckBird.damage=3;

        bombBird = new Bird("bomb");
        bombBird.setTexture("bomb.png");
        bombBird.setSize(50.0f, 50.0f);
        bombBird.setPos(120, 122);
        bombBird.damage=2;

        catapult = new Catapult();
        catapult.setTexture("catapult.png");
        catapult.setSize(75.0f, 150.0f);
        catapult.setPos(1f * 100, 1.20f * 100);

//        smallpig = new Pig("small");
//        smallpig.setTexture("small.png");
//        smallpig.setSize(.40f*100,.40f*100);
//
//        bigpig = new Pig("big");
//        bigpig.setTexture("jaitrika.png");
//        bigpig.setSize(.75f*100,.75f*100);
//
//        woodTwo = new Block("wood",2);
//        woodTwo.setTexture("wood2.png");
//        woodTwo.setSize(1*100,.2f*100);
//
//        woodOne = new Block("wood",1);
//        woodOne.setTexture("wood1.png");
//        woodOne.setSize(1f*100,1f*100);
        woodTwo = new Block("wood",2);
        woodTwo.setTexture("wood2.png");
        woodTwo.setSize(1*100,.2f*100);
        woodTwo.health=1;

        stoneOne = new Block("stone",1);
        stoneOne.setTexture("stoone1.png");
        stoneOne.setSize(1f*100,1f*100);
        stoneOne.health=3;

        woodThree = new Block("wood",3);
        woodThree.setTexture("wood3.png");
        woodThree.setSize(1f*100,1f*100);
        woodThree.health=1;

        woodOne = new Block("wood",1);
        woodOne.setTexture("wood1.png");
        woodOne.setSize(1f*100,1f*100);
        woodOne.health=1;


        glassOne = new Block("glass",1);
        glassOne.setTexture("glass1.png");
        glassOne.setSize(1f*100,1f*100);
        glassOne.health=2;

        glassThree = new Block("glass",1);
        glassThree.setTexture("glass3.png");
        glassThree.setSize(1f*100,1f*100);
        glassThree.health=2;

        smallpig = new Pig("small");
        smallpig.setTexture("small.png");
        smallpig.setSize(.40f*100,.40f*100);
        smallpig.health=1;

        smallpig2 = new Pig("small");
        smallpig2.setTexture("small.png");
        smallpig2.setSize(.40f*100,.40f*100);
        smallpig2.health=1;

        kingpig=new Pig("king");
        kingpig.setTexture("kingpiggie.png");
        kingpig.setSize(1f*100,1f*100);
        kingpig.health=3;

        currentBird = null;
        isReleased = false;
        isBirdMovingToCatapult = false;
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
        if (redBird != null) redBird.sprite.draw(spriteBatch);
        if (chuckBird != null) chuckBird.sprite.draw(spriteBatch);
        if (bombBird != null) bombBird.sprite.draw(spriteBatch);

        nextLevel.setPosition(3.3f*100,.8f*100);
        backSprite.setPosition(2.7f*100,.8f*100);
        retrySprite.setPosition(3.3f*100,.8f*100);

        winScreen.setPosition(2.5f*100,1f*100);
        loseScreen.setPosition(2f*100,1f*100);
        Menu.setPosition(7.2f*100,4.1f*100);
        Menu.draw(spriteBatch);

        if (kingpig!=null) {
            kingpig.setPos(6.6f * 99, 2.15f * 100);
            kingpig.sprite.draw(spriteBatch);
        }

        if (smallpig != null) {
            smallpig.setPos(5.8f * 100, 1.4f * 100);
            smallpig.sprite.draw(spriteBatch);
        }

        if (smallpig2 != null) {
            smallpig2.setPos(5.8f * 100, 2.55f * 100);
            smallpig2.sprite.draw(spriteBatch);
        }

        if (stoneOne!=null) {
            stoneOne.setPos(5.5f * 100, 1.2f * 100);
            stoneOne.sprite.draw(spriteBatch);
        }

        if (woodOne!=null) {
            woodOne.setPos(5.5f * 100, 2.35f * 100);
            woodOne.sprite.draw(spriteBatch);
        }

        if (woodTwo!=null) {
            woodTwo.setPos(5.5f * 100, 2.18f * 100);
            woodTwo.sprite.draw(spriteBatch);
        }

        if (woodThree!=null) {
            woodThree.setPos(4.5f * 100, 1.2f * 100);
            woodThree.sprite.draw(spriteBatch);
        }

        if (glassThree!=null) {
            glassThree.setPos(5.5f * 100, 3.3f * 100);
            glassThree.sprite.draw(spriteBatch);
        }

        if (glassOne!=null) {
            glassOne.setPos(6.5f * 100, 1.2f * 100);
            glassOne.sprite.draw(spriteBatch);
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
                    game.setScreen(new HomeScreen(this.game));//MAYBE IMPLEMENT A SCREEN TELL ALL LEVELS HAVE BEEN COMPLETED (STATS :PIGS POPPED) START NEW GAME?EXIT GAME?
                } else if (backSpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    game.setScreen(new LevelMap_l3unclocked(game, "night_l3unclocked.png"));
                }
            } else if (winScreenDraw==2) {
                if (retrySpriteBounds.contains(mousePos.x, 500-mousePos.y)){
                    game.setScreen(new Level3(this.game,this.theme));
                } else if (backSpriteBounds.contains(mousePos.x, 500-mousePos.y)) {
                    game.setScreen(new LevelMap_l3unclocked(game, "night_l3unclocked.png"));
                }
            }

            if (MenuBounds.contains(mousePos.x,500-mousePos.y)){
                game.setScreen(new Menu(this.game,3,this.theme));

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
            else if (redBird != null && redBird.sprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                selectedBird = redBird;}

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
                else if (currentBird == redBird) {
                    redBird.texture.dispose();
                    redBird=null;
                }


                currentBird = null;
                isReleased = false;
                if (smallpig==null &&smallpig2==null && kingpig==null){
                    winScreenDraw=1;
                }

                // Progress to next bird or end game
                if ( chuckBird == null && bombBird == null && redBird==null) {
                    if (smallpig==null &&smallpig2==null && kingpig==null){
                        winScreenDraw=1;
                    } else {
                        winScreenDraw=2;
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
        if (kingpig != null && birdBounds.overlaps(kingpig.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Big Pig!");
            handleCollision(kingpig);
        }

        // Check collision with smallpig
        if (smallpig != null && birdBounds.overlaps(smallpig.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Small Pig!");
            handleCollision(smallpig);
        }

        if (smallpig2 != null && birdBounds.overlaps(smallpig2.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Small Pig!");
            handleCollision(smallpig2);
        }

        // Check collision with woodOne
        if (stoneOne != null && birdBounds.overlaps(stoneOne.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block One!");
            handleCollision(stoneOne);
        }

        if (glassThree != null && birdBounds.overlaps(glassThree.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block Two!");
            handleCollision(glassThree);
        }

        if (glassOne != null && birdBounds.overlaps(glassOne.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block Two!");
            handleCollision(glassOne);
        }

        if (woodOne != null && birdBounds.overlaps(woodOne.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block Two!");
            handleCollision(woodOne);
        }

        // Check collision with woodTwo
        if (woodTwo != null && birdBounds.overlaps(woodTwo.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block Two!");
            handleCollision(woodTwo);
        }

        if (woodThree != null && birdBounds.overlaps(woodThree.sprite.getBoundingRectangle())) {
            System.out.println("Collision with Wood Block Two!");
            handleCollision(woodThree);
        }
    }

    private void handleCollision(Object target) { // Use Object or a common superclass/interface
        if (target instanceof Pig) {
            Pig pig = (Pig) target;

            if (pig == smallpig) {
                pig.texture.dispose();
                smallpig = null;
                System.out.println("Small Pig removed from the game.");
            } else if (pig == smallpig2) {
                pig.texture.dispose();
                smallpig2 = null;
            } else if (pig == kingpig) {
                if (pig.health==3) {
                    float posx = kingpig.posx;
                    float posy = kingpig.posy;
                    float sizex = kingpig.sizex;
                    float sizey = kingpig.sizey;
                    kingpig.setTexture("jaitrikabroken.png");
                    kingpig.setPos(posx, posy);
                    kingpig.setSize(sizex, sizey);
                    kingpig.health = 2;

                    System.out.println("Big Pig removed from the game.");
                } else if (pig.health<=2 && (nbdone==2 || nbdone==3)) {
                    float posx = kingpig.posx;
                    float posy = kingpig.posy;
                    float sizex = kingpig.sizex;
                    float sizey = kingpig.sizey;
                    kingpig.setTexture("kinginjured.png");
                    kingpig.setPos(posx, posy);
                    kingpig.setSize(sizex, sizey);
                    kingpig.health = 1;
                    System.out.println("Big Pig removed from the game.");
                } else if (pig.health==1 && (nbdone==2 || nbdone==3)){
                    pig.texture.dispose();
                    kingpig=null;
                }
            }
            // Optionally, add score increment, play sound, etc.
        } else if (target instanceof Block) {
            Block block = (Block) target;

            if (block == stoneOne) {

                if (block.health==3) {
                    float posx = stoneOne.posx;
                    float posy = stoneOne.posy;
                    float sizex = stoneOne.sizex;
                    float sizey = stoneOne.sizey;
                    stoneOne.setTexture("stoone1broken.png");
                    stoneOne.setPos(posx, posy);
                    stoneOne.setSize(sizex, sizey);
                    stoneOne.health = 2;
                } else if (block.health==2 && (nbdone==2 || nbdone==3)) {
                    float posx = stoneOne.posx;
                    float posy = stoneOne.posy;
                    float sizex = stoneOne.sizex;
                    float sizey = stoneOne.sizey;
                    stoneOne.setTexture("stoone1cracked.png");
                    stoneOne.setPos(posx, posy);
                    stoneOne.setSize(sizex, sizey);
                    stoneOne.health = 1;
                }
                if (block.health==1 && (nbdone==2 || nbdone==3)){
                    block.texture.dispose();
                    stoneOne=null;
                }


                if (glassThree!=null){
                    if (glassThree.health==2) {
                        float posx = glassThree.posx;
                        float posy = glassThree.posy;
                        float sizex = glassThree.sizex;
                        float sizey = glassThree.sizey;
                        glassThree.setTexture("glass3broken.png");
                        glassThree.setPos(posx, posy);
                        glassThree.setSize(sizex, sizey);
                        glassThree.health = 1;
                        System.out.println("Wood Block One removed from the game.");
                    }
                    if (glassThree.health<=1 && (nbdone==2 || nbdone==3)){
                        glassThree.texture.dispose();
                        glassThree=null;
                    }
                }
                if (woodOne!=null){
                    woodOne.texture.dispose();
                    woodOne=null;
                }
                if (woodTwo!=null){
                    woodTwo.texture.dispose();
                    woodTwo=null;
                }
                System.out.println("Wood Block One removed from the game.");
            } else if (block == woodTwo) {
                block.texture.dispose();
                woodTwo = null;
                if (glassThree!=null){
                    if (glassThree.health==2) {
                        float posx = glassThree.posx;
                        float posy = glassThree.posy;
                        float sizex = glassThree.sizex;
                        float sizey = glassThree.sizey;
                        glassThree.setTexture("glass3broken.png");
                        glassThree.setPos(posx, posy);
                        glassThree.setSize(sizex, sizey);
                        glassThree.health = 1;
                        System.out.println("Wood Block One removed from the game.");
                    }
                    if (glassThree.health<=1 && (nbdone==2 || nbdone==3)){
                        glassThree.texture.dispose();
                        glassThree=null;
                    }
                }
                if (woodOne!=null){
                    woodOne.texture.dispose();
                    woodOne=null;
                }
            } else if (block == woodOne) {
                woodOne.texture.dispose();
                woodOne = null;
                if (glassThree!=null){
                    if (glassThree.health==2) {
                        float posx = glassThree.posx;
                        float posy = glassThree.posy;
                        float sizex = glassThree.sizex;
                        float sizey = glassThree.sizey;
                        glassThree.setTexture("glass3broken.png");
                        glassThree.setPos(posx, posy);
                        glassThree.setSize(sizex, sizey);
                        glassThree.health = 1;
                        System.out.println("Wood Block One removed from the game.");
                    }
                    if (glassThree.health<=1 && (nbdone==2 || nbdone==3)){
                        glassThree.texture.dispose();
                        glassThree=null;
                    }
                }
            } else if (block == woodThree) {
                block.texture.dispose();
                woodThree = null;
                System.out.println("Wood Block Two removed from the game.");
            } else if (block == glassOne) {
                if (block.health==2) {
                    float posx = glassOne.posx;
                    float posy = glassOne.posy;
                    float sizex = glassOne.sizex;
                    float sizey = glassOne.sizey;
                    glassOne.setTexture("glass1broken.png");
                    glassOne.setPos(posx, posy);
                    glassOne.setSize(sizex, sizey);
                    glassOne.health = 1;
                    System.out.println("Wood Block One removed from the game.");
                }
                if (block.health<=1 && (nbdone==2 || nbdone==3)){
                    block.texture.dispose();
                    glassOne=null;
                }
                if (currentBird == bombBird || currentBird == chuckBird){
                    glassOne=null;
                }
                if (kingpig!=null){
                    kingpig.health=kingpig.health-1;
                }
            } else if (block == glassThree) {
                if (block.health==2) {
                    float posx = glassThree.posx;
                    float posy = glassThree.posy;
                    float sizex = glassThree.sizex;
                    float sizey = glassThree.sizey;
                    glassThree.setTexture("glass3broken.png");
                    glassThree.setPos(posx, posy);
                    glassThree.setSize(sizex, sizey);
                    glassThree.health = 1;
                    System.out.println("Wood Block One removed from the game.");
                }
                if (block.health==1 && (nbdone==2 || nbdone==3)){
                    block.texture.dispose();
                    glassThree=null;
                }
                if (currentBird == bombBird || currentBird == chuckBird){
                    glassThree=null;
                }
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
    }
}

