package io.github.some_example_name.Classes;

import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    public String name;
    public int speed;
    public String ability;
    public int damage;
    public float sizex;
    public float sizey;
    public float posx;
    public float posy;
    public Texture texture;
    public Sprite sprite;

    public boolean isDragged;
    public boolean isShot; // Indicates if the bird is being dragged
    public Vector2 velocity; // Stores the velocity for projectile motion

    public Bird(String name) {
        if (Objects.equals(name, "red")) {
            this.name = name;
            this.speed = 15;
            this.damage = 15;
            this.ability = "Extra Damage";
        } else if (Objects.equals(name, "bomb")) {
            this.name = name;
            this.speed = 10;
            this.damage = 20;
            this.ability = "Explode";
        } else if (Objects.equals(name, "chuck")) {
            this.name = name;
            this.speed = 25;
            this.damage = 10;
            this.ability = "Speed Boost";
        }
        this.velocity = new Vector2();
        this.isDragged = false;
        this.isShot = false;
    }

    public void activate_ability() {
        if (Objects.equals(this.name, "red")) {
            this.damage += 5;
        } else if (Objects.equals(this.name, "bomb")) {
            this.damage += 10;
        } else if (Objects.equals(this.name, "chuck")) {
            this.speed += 10;
        }
    }

    public void setSize(float sizex, float sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.sprite.setSize(this.sizex, this.sizey);
    }

    public void setPos(float posx, float posy) {
        this.posx = posx;
        this.posy = posy;
        this.sprite.setPosition(this.posx, this.posy);
    }

    public void setTexture(String s) {
        this.texture = new Texture(s);
        this.sprite = new Sprite(this.texture);
    }
}
