package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class ThemeChoose implements Screen {
    private final Game game;
    private final SpriteBatch batch;
    private Texture backgroundImage;
    private Texture night_themeImage;
    private Texture day_themeImage;
    private Texture halloween_themeImage;
    private Rectangle themenightRectangle;
    private Rectangle themedayRectangle;
    private Rectangle themehalloweenRectangle;
    private String selectedTheme;
    private Texture homeButtonImage;
    private Circle speakerCircle;
    private Rectangle homeButtonRectangle;
    private Texture backgroundMusicImage;
    private Music backgroundMusic;
    private boolean resumeMode;

    public ThemeChoose(Game game, boolean resumeMode) {
        this.game = game;
        this.batch = ((Main) game).getBatch();
        this.resumeMode = resumeMode;
    }

    @Override
    public void show() {
        backgroundImage = new Texture("theme_background.png");
        night_themeImage = new Texture("night_theme.png");
        day_themeImage = new Texture("day_theme.png");
        halloween_themeImage =new Texture("spooky_theme.png");
        backgroundMusicImage = new Texture("download2.png"); // 50*50
        homeButtonImage = new Texture("homebutton.png");

        speakerCircle = new Circle(120, 455, 22.5f);
        homeButtonRectangle = new Rectangle(18.2F, 418.8F, 70, 70);
        themenightRectangle = new Rectangle(50, 15.6F, 216, 383);
        themedayRectangle = new Rectangle(292, 15.6F, 216, 383);
        themehalloweenRectangle=new Rectangle(533.6F, 15.6F, 216, 383);
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        backgroundMusic.setLooping(true);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float adjustedY = 500 - screenY;
                if (speakerCircle.contains(screenX, adjustedY)) {
                    ((Main) game).getMusicManager().toggleMusic();
                    return true;
                }
                if (homeButtonRectangle.contains(screenX, adjustedY)) {
                    game.setScreen(new HomeScreen(game));

                    return true;
                }

                if (themenightRectangle.contains(screenX, adjustedY)) {
                    selectedTheme="night.png";
                    if (resumeMode) {

                        game.setScreen(new LevelMap_Resume(game, "night_resume.png"));
                    } else {
                        game.setScreen(new LevelMap_Start(game, selectedTheme));
                    }
                    return true;
                } else if (themedayRectangle.contains(screenX, adjustedY)) {
                    selectedTheme = "day.png";
                    if (resumeMode) {
                        game.setScreen(new LevelMap_Resume(game, "day_resume.png"));
                    } else {
                        game.setScreen(new LevelMap_Start(game, selectedTheme));
                    }
                    return true;
                } else if (themehalloweenRectangle.contains(screenX, adjustedY)) {
                    selectedTheme = "spooky.png";
                    if (resumeMode) {
                        game.setScreen(new LevelMap_Resume(game,"spooky_resume.png"));
                    } else {
                        game.setScreen(new LevelMap_Start(game, selectedTheme));
                    }
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
        batch.draw(night_themeImage, themenightRectangle.x,themenightRectangle.y,themenightRectangle.width,themenightRectangle.height);
        batch.draw(day_themeImage, themedayRectangle.x,themedayRectangle.y,themedayRectangle.width,themedayRectangle.height);
        batch.draw(halloween_themeImage, themehalloweenRectangle.x,themehalloweenRectangle.y,themehalloweenRectangle.width,themehalloweenRectangle.height);
        batch.draw(homeButtonImage,homeButtonRectangle.x, homeButtonRectangle.y, homeButtonRectangle.width, homeButtonRectangle.height);
        batch.draw(backgroundMusicImage, speakerCircle.x - 25, speakerCircle.y - 25, 50, 50);

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
        night_themeImage.dispose();
        day_themeImage.dispose();
        halloween_themeImage.dispose();
        backgroundMusic.dispose();
        backgroundMusicImage.dispose();
        homeButtonImage.dispose();
    }
}
