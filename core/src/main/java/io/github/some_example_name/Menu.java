package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class Menu implements Screen {
    private final Game game;
    private final SpriteBatch batch;
    private Texture backgroundImage;
    private Texture menuboxImage;
    private Texture resumeGameImage;
    private Texture homeButtonImage;
    private Texture saveAndExitImage;
    private Rectangle resumeButtonRectangle;
    private Rectangle homeButtonRectangle;
    private Rectangle saveAndExitRectangle;
    int currentlevel;
    String theme;

    public Menu(Game game,int n,String theme) {
        this.game = game;
        this.batch = ((Main) game).getBatch();
        this.currentlevel=n;
        this.theme=theme;
    }

    @Override
    public void show() {

        backgroundImage = new Texture("bgm_for_menu.png"); //800*500
        menuboxImage = new Texture("menu_dialog.png"); //668.4*399.2
        resumeGameImage = new Texture("resume_game.png"); //280*50
        homeButtonImage = new Texture("homeButtonImage.png"); //280*50
        saveAndExitImage = new Texture("saveAndExitImage.png"); //280*50

        resumeButtonRectangle= new Rectangle(261, 244, 280, 50);
        homeButtonRectangle= new Rectangle(261, 167, 280, 50);
        saveAndExitRectangle= new Rectangle(261, 91, 280, 50);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float newY = 500 - screenY;

                if (resumeButtonRectangle.contains(screenX, newY)) {

                    if (currentlevel==1){
                        game.setScreen(new Level1(game,theme));
                    } else if (currentlevel==2) {
                        game.setScreen(new Level2(game,theme));
                    }
                }


                if (homeButtonRectangle.contains(screenX, newY)) {
                    game.setScreen(new HomeScreen(game));
                }

                if (saveAndExitRectangle.contains(screenX, newY)) {
                    //game.dispose();
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
        batch.draw(menuboxImage, 66.9F, 50F, 668, 400);
        batch.draw(resumeGameImage, resumeButtonRectangle.x, resumeButtonRectangle.y,resumeButtonRectangle.width, resumeButtonRectangle.height);
        batch.draw(homeButtonImage, homeButtonRectangle.x, homeButtonRectangle.y, homeButtonRectangle.width, homeButtonRectangle.height);
        batch.draw(saveAndExitImage, saveAndExitRectangle.x, saveAndExitRectangle.y, saveAndExitRectangle.width, saveAndExitRectangle.height);
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
        menuboxImage.dispose();
        resumeGameImage.dispose();
        homeButtonImage.dispose();
        saveAndExitImage.dispose();
        backgroundImage.dispose();

    }
}
