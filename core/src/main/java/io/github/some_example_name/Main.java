package io.github.some_example_name;

import com.badlogic.gdx.Game;
import io.github.some_example_name.Classes.MusicManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game{

    private SpriteBatch batch;
    private Texture homeImage;
    private MusicManager musicManager;
    private boolean isMusicPlaying;

    @Override
    public void create() {
        batch = new SpriteBatch();
        homeImage = new Texture("homebutton.png");
        setScreen(new HomeScreen(this));
        musicManager = new MusicManager();
        isMusicPlaying = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        homeImage.dispose();
        musicManager.dispose();
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
