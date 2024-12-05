package io.github.some_example_name;

import java.io.FileWriter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Pixmap;

import java.io.IOException;
import java.util.logging.Level;

public class LevelMap_Start implements Screen {
    private final Game game;
    private final SpriteBatch batch;

    private Texture backgroundImage;
    private Texture backgroundMusicImage;
    private Texture homeButtonImage;
    private Rectangle backgroundMusicRectangle;
    private Rectangle homeButtonRectangle;
    private Texture prev_page;
    private Texture Level_Map;
    private Rectangle prev_page_rect;
    private Music backgroundMusic;
    private Texture triggerLevel1Image, triggerLevel2Image, triggerLevel3Image;
    private Sprite triggerLevel1Sprite, triggerLevel2Sprite, triggerLevel3Sprite;
    private Rectangle triggerLevel1Rect, triggerLevel2Rect, triggerLevel3Rect;
    String theme;

    LevelMap_Start(Game game, String theme) {
        this.game = game;
        this.batch = ((Main) game).getBatch();
        this.backgroundImage = new Texture(theme);
        this.theme=theme;
    }

    @Override
    public void show() {
        backgroundMusicImage = new Texture("download2.png"); // 50*50
        homeButtonImage = new Texture("homebutton.png"); // 70*70
        prev_page = new Texture("prev.png");
        Level_Map = new Texture("levelm.png");
        triggerLevel1Image = new Texture("level1.png");
        triggerLevel2Image = new Texture("level2s.png");
        triggerLevel3Image = new Texture("level3.png");
        Pixmap triggerLevel1Pixmap = new Pixmap(Gdx.files.internal("level1.png"));
        Pixmap triggerLevel2Pixmap = new Pixmap(Gdx.files.internal("level2s.png"));
        Pixmap triggerLevel3Pixmap = new Pixmap(Gdx.files.internal("level3.png"));


        backgroundMusicRectangle = new Rectangle(115, 417, 50, 50);
        homeButtonRectangle = new Rectangle(23, 407, 70, 70);
        prev_page_rect = new Rectangle(28, 29, 44, 41);
        triggerLevel1Rect = new Rectangle(60-15, 133-10-10, 134, 91);
        triggerLevel2Rect = new Rectangle(386-10-5, 61-10-5, 134-10, 94);
        triggerLevel3Rect = new Rectangle(641-10-10, 250-10-10, 134-10, 104);

        triggerLevel1Sprite = new Sprite(triggerLevel1Image);
        triggerLevel1Sprite.setPosition(60-10-5, 133-10-10);
        triggerLevel2Sprite = new Sprite(triggerLevel2Image);
        triggerLevel2Sprite.setPosition(386-10-5, 61-10-5);
        triggerLevel3Sprite = new Sprite(triggerLevel3Image);
        triggerLevel3Sprite.setPosition(641-10-10, 250-10-10);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        backgroundMusic.setLooping(true);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float newY = 500 - screenY;

                if (backgroundMusicRectangle.contains(screenX, newY)) {
                    ((Main) game).getMusicManager().toggleMusic();
                    return true;
                }



                if (prev_page_rect.contains(screenX, newY)) {
                    game.setScreen(new ThemeChoose(game,false));
                    return true;
                }

                if (triggerLevel1Rect.contains(screenX, newY) && isTouchedInsideSprite(triggerLevel1Pixmap, screenX, newY, triggerLevel1Sprite)) {

                    game.setScreen(new Level1(game,theme));
                    return true;
                }

                if (triggerLevel2Rect.contains(screenX, newY) && isTouchedInsideSprite(triggerLevel2Pixmap, screenX, newY, triggerLevel2Sprite)) {
                    dispose();
                    triggerLevel1Sprite=null;
                    System.out.println("LEVEL 2 IS LOCKED");
                    return true;
                }

                if (triggerLevel3Rect.contains(screenX, newY) && isTouchedInsideSprite(triggerLevel3Pixmap, screenX, newY, triggerLevel3Sprite)) {
                    System.out.println("LEVEL 3 IS LOCKED");
                    dispose();
                    triggerLevel1Sprite=null;
                    return true;
                }

                if (homeButtonRectangle.contains(screenX, newY)) {
                    game.setScreen(new HomeScreen(game));
                    return true;
                }


                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, 800, 500);
        batch.draw(backgroundMusicImage, backgroundMusicRectangle.x, backgroundMusicRectangle.y, backgroundMusicRectangle.width, backgroundMusicRectangle.height);
        batch.draw(homeButtonImage, homeButtonRectangle.x, homeButtonRectangle.y, homeButtonRectangle.width, homeButtonRectangle.height);
        if (triggerLevel1Sprite!=null){
            batch.draw(triggerLevel1Sprite, triggerLevel1Rect.x, triggerLevel1Rect.y);
        }
        batch.draw(triggerLevel2Sprite, triggerLevel2Rect.x, triggerLevel2Rect.y);
        batch.draw(triggerLevel3Sprite, triggerLevel3Rect.x, triggerLevel3Rect.y);
        batch.draw(Level_Map,193.3F,327.4F+50,414,105);
        batch.draw(prev_page,prev_page_rect.x,prev_page_rect.y,prev_page_rect.width,prev_page_rect.height);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundImage.dispose();
        backgroundMusicImage.dispose();
        homeButtonImage.dispose();
        backgroundMusic.dispose();
        Level_Map.dispose();
        triggerLevel1Image.dispose();
        triggerLevel2Image.dispose();
        triggerLevel3Image.dispose();
    }

    private boolean isTouchedInsideSprite(Pixmap pixmap, int screenX, float adjustedY, Sprite sprite) {
        if (sprite!=null) {
            int localX = (int) (screenX - sprite.getX());
            int localY = (int) (adjustedY - sprite.getY());

            if (localX >= 0 && localX < pixmap.getWidth() && localY >= 0 && localY < pixmap.getHeight()) {
                return (pixmap.getPixel(localX, localY) & 0x000000FF) != 0;
            }
        }
        return false;
    }

}

