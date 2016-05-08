package com.dbeef.madpirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dbeef.madpirates.input.InputInterpreter;
import com.dbeef.madpirates.screens.MenuScreen;



public class Main extends Game {
	SpriteBatch batch;
	Texture img;
	InputInterpreter iI;

	@Override
	public void create() {
		batch = new SpriteBatch();
		iI = new InputInterpreter();

		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render(); // important!
	}
}
