package io.github.some_example_name.Classes;

import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pig{
    public String size;
    public int health;
    public float posx;
    public float posy;
    public float sizex;
    public float sizey;
    public Texture texture;
    public Sprite sprite;


    public Pig(String name){
        if (Objects.equals(name, "small")){
            this.health=10;
        } else if (Objects.equals(name, "big")){
            this.health=20;
        } else if (Objects.equals(name, "king")){
            this.health=30;
        }
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

    public void setSprite(){
        this.sprite= new Sprite(this.texture);
    }
}
