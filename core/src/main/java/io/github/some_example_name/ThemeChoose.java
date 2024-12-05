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

public class HomeScreen implements Screen {
    private final Game game;
    private final SpriteBatch batch;
    private Texture backgroundImage;
    private Texture musicIcon;
    private Texture startButtonImage;
    private Texture resumeButtonImage;
    private Circle musicIconCircle;
    private Rectangle startNewGameRectangle;
    private Rectangle resumeGameRectangle;
    private Music backgroundMusic;
    private Texture[] storySlides;  // array of textures containing story slide images
    private int currentSlide = -1;// current slide index
    public boolean resume;

    public HomeScreen(Game game) {
        this.game = game;
        this.batch = ((Main) game).getBatch();
    }

    @Override
    public void show() {

        backgroundImage = new Texture("bgm.png");
        musicIcon = new Texture("download2.png");
        startButtonImage = new Texture("startt.png");
        resumeButtonImage = new Texture("resumee.png");

        storySlides = new Texture[]{new Texture("story_slide_1.png"), new Texture("story_slide_2.png"), new Texture("story_slide_3.png"), new Texture("story_slide_4.png")};
        musicIconCircle = new Circle(55, 452, 22.5f);
        startNewGameRectangle = new Rectangle(185, 65, 180, 50);
        resumeGameRectangle = new Rectangle(435, 65, 180, 50);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        backgroundMusic.setLooping(true); // music loop

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float newy = 500 - screenY;

                if (musicIconCircle.contains(screenX, newy)) {
                    ((Main) game).getMusicManager().toggleMusic();
                    return true;
                }
                if (currentSlide == -1) {  // home screen
                    if (startNewGameRectangle.contains(screenX, newy)) {
                        currentSlide = 0;// start the story slides
                        resume=false;
                    }
                    if (resumeGameRectangle.contains(screenX, newy)) {
                        resume=true;
                        game.setScreen(new ThemeChoose(game,resume));
                    }
                } else {  //in the story slides
                    currentSlide++;//next slide
                    if (currentSlide >= storySlides.length) {
                        game.setScreen(new ThemeChoose(game, resume));
                        return true;
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        if (currentSlide == -1) {

            batch.draw(backgroundImage, 0, 0, 800, 500);
            batch.draw(musicIcon, 30, 426, 50, 50);
            batch.draw(startButtonImage, startNewGameRectangle.x, startNewGameRectangle.y, startNewGameRectangle.width, startNewGameRectangle.height);
            batch.draw(resumeButtonImage, resumeGameRectangle.x, resumeGameRectangle.y, resumeGameRectangle.width, resumeGameRectangle.height);
        } else {
            batch.draw(storySlides[currentSlide], 0, 0, 800, 500);
        }
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
        musicIcon.dispose();
        startButtonImage.dispose();
        resumeButtonImage.dispose();
        backgroundMusic.dispose();
        for (Texture slide : storySlides) {
            slide.dispose();
        }
    }
}
