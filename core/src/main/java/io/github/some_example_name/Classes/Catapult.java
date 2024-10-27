package io.github.some_example_name.Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Catapult{
    public float angle;
    public Texture texture;
    public float sizex;
    public float sizey;
    public float posx;
    public float posy;
    public Sprite sprite;

    public Catapult(){
    }

    public void setPos(float posx,float posy){
        this.posx=posx;
        this.posy=posy;
        this.sprite.setPosition(this.posx,this.posy);
    }

    public void setSize(float sizex,float sizey){
        this.sizex=sizex;
        this.sizey=sizey;
        this.sprite.setSize(this.sizex,this.sizey);
    }

    public void setTexture(String s){
        this.texture= new Texture(s);
        this.sprite= new Sprite(this.texture);
    }
}
