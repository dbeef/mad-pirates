package com.dbeef.madpirates.screens;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Antialiasing;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.CameraMotion;
import com.bitfire.postprocessing.effects.CrtMonitor;
import com.bitfire.postprocessing.effects.Curvature;
import com.bitfire.postprocessing.effects.Fxaa;
import com.bitfire.postprocessing.effects.LensFlare;
import com.bitfire.postprocessing.effects.LensFlare2;
import com.bitfire.postprocessing.effects.MotionBlur;
import com.bitfire.postprocessing.effects.Nfaa;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.postprocessing.effects.Zoomer;
import com.bitfire.postprocessing.filters.Blur;
import com.bitfire.postprocessing.filters.CrtScreen.RgbMode;
import com.bitfire.utils.ShaderLoader;
import com.dbeef.madpirates.Main;
import com.dbeef.madpirates.camera.ImprovedCamera;
import com.dbeef.madpirates.input.InputInterpreter;
import com.dbeef.madpirates.physics.BodiesDatabase;
import com.dbeef.madpirates.player.Ship;

public class MenuScreen implements Screen {

	//Good advice: Use CTRL + SHIFT + F
	
	PostProcessor postProcessor;
    
    ParticleEffect pe;
    
	TiledMap tiledMap;

	OrthogonalTiledMapRenderer tiledMapOrthogonal;
	
	BitmapFont font;
	SpriteBatch batch;
	Texture pirateShip;
	Viewport viewport;
	Box2DDebugRenderer dr;

	Ship ship;
	InputInterpreter iI;
	BodiesDatabase bodiesDatabase;
	ImprovedCamera camera;

	final Main game;

	public MenuScreen(final Main gam) {

		this.game = gam;

		font = new BitmapFont();
		font.setColor(Color.RED);

		pirateShip = new Texture("pirateShips/pirateShip.png");
		pirateShip.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		batch = new SpriteBatch();
		camera = new ImprovedCamera(800, 480);
		iI = new InputInterpreter();
		viewport = new FillViewport(800, 480, camera);

		bodiesDatabase = new BodiesDatabase();
		dr = new Box2DDebugRenderer();
		dr.setDrawVelocities(true);
		pe = new ParticleEffect();
	    pe.load(Gdx.files.internal("particleEffects/piana"), 
		            Gdx.files.internal("particleEffects"));
	
		 ship = new Ship(pirateShip, pe, 700, 800);
		ship.getSimulationIndex(bodiesDatabase.add(ship.getBody(),
				ship.getBodyDef(), ship.getFixtureDef()));

		camera.position.set(camera.viewportWidth / 2,
				camera.viewportHeight / 2, 0);

		tiledMap = new TmxMapLoader().load("tileMap/worldTilemap.tmx");

		tiledMapOrthogonal = new OrthogonalTiledMapRenderer(tiledMap);

		
		
		
		
		
		ShaderLoader.Pedantic = false;

		ShaderLoader.BasePath = "shaders/";	
	

		postProcessor = new PostProcessor( false, false, true );


		Curvature bloom = new Curvature( );
		CrtMonitor crtMonitor = new CrtMonitor(800, 480, false, false, RgbMode.RgbShift, 0);
		crtMonitor.setTime(5);


       Zoomer zoomer = new Zoomer(800, 480);
       zoomer.setOrigin(800/2, 480/2);
       zoomer.setZoom(1.42f);

       bloom.setDistortion(2);
   

       postProcessor.addEffect(zoomer);
       zoomer.setBlurStrength(0);    


   
	}

	@Override
	public void render(float delta) {
	      
		camera.takeZoomDelta(iI.getZoomDelta());
		camera.update();
	

		
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		postProcessor.capture();
		batch.begin();

			tiledMapOrthogonal.setView(camera);
	        tiledMapOrthogonal.render();

			//Better flush the buffer before drawing box2d's debug mode
			batch.flush();
		// For debugging:
//
			 batch.end();
			 batch.begin();
			 batch.draw(pirateShip, (float) 700, 800);
			 ship.render(batch,delta);

			 batch.end();

			 postProcessor.render();
	
		bodiesDatabase.simulate(delta);

		ship.takeSimulationReflection(bodiesDatabase.get(0));

		camera.position.set((float) ship.giveX() + 100, (float) ship.giveY() + 220,
				(float) 0);

		if (iI.touched == true) {
			Vector3 temp = new Vector3();
			temp.x = (float) iI.getXTap();
			temp.y = (float) iI.getYTap();
			camera.unproject(temp);

			bodiesDatabase.sideToRotate(temp.x, temp.y);
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}