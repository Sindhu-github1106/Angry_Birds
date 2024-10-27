package io.github.some_example_name.Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private Music backgroundMusic;
    private boolean isMusicPlaying;

    public MusicManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        backgroundMusic.setLooping(true);
        isMusicPlaying = false;
    }

    public void toggleMusic() {
        if (isMusicPlaying) {
            backgroundMusic.pause();
            isMusicPlaying = false;
        } else {
            backgroundMusic.play();
            isMusicPlaying = true;
        }
    }

    public boolean isMusicPlaying() {
        return isMusicPlaying;
    }

    public void dispose() {
        backgroundMusic.dispose();
    }
}
