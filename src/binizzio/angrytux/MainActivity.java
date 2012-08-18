package binizzio.angrytux;

/**
 *   @author Vinicius DSL
 *   email:dasilvavinic7@gmail.com
 */

import java.util.Stack;




import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;


import android.hardware.SensorManager;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.Shape;

public class MainActivity extends SimpleBaseGameActivity implements
IOnSceneTouchListener, IOnAreaTouchListener {

	private SmoothCamera camera;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas2;
	private BitmapTextureAtlas mPersonajes;
	private BitmapTextureAtlas mpunto;
	private BitmapTextureAtlas mMaderas1;
	private BitmapTextureAtlas mMaderas2;
	private boolean swF=false;
	private int b;
	
	
	private AnimatedSprite explosion;
	private TiledTextureRegion mCircleFaceTextureRegion;
	private Stack<Sprite> PilaP= new Stack<Sprite>();
	private int mFaceCount = 0;
	private float x = 0;
	private float y = 0;
	private PhysicsWorld mPhysicsWorld;

	private int cont=0;
	private Scene mScene;
	
	private AnimatedSprite tuxConGravedad;
	private boolean sw = false;
	private TextureRegion mBgTexture;
	
	private Body body;
    private boolean swE=false;
	private long lastShakeTime=0;
	private float posX;
	private TiledTextureRegion mResoltera;
	private AnimatedSprite resoltera;
	
	private TextureRegion texturaPunto;
	private TextureRegion mAuxRes;
	private Sprite resolteraAux;
	private boolean swCol=true;
	private Line line;
	private Line line2;
	private TextureRegion mPasto;
	 

	private boolean swP=false;
	private TextureRegion tMadera3;
	private TextureRegion tMadera4;
	private TiledTextureRegion tpajaro;
	private Sprite madera1;
	private Sprite madera2;
	private Sprite madera3;
	private Sprite madera4;
	private Sprite madera5;
	
	
	private TiledTextureRegion tExplosion2;
	private AnimatedSprite pajaro;
	private TextureRegion tfin;
	private Sprite fina;
	private boolean swEs=false;
	private boolean swT=false;
	private boolean swFinal =false;
	private int a=0;
	private BitmapTextureAtlas mBackgroundTexture;
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new SmoothCamera(1000, 360, 1000, 720, 1000,
				1000, 1.0f);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}  

	@Override
	protected void onCreateResources() {
		this.mBitmapTextureAtlas =  new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.mPersonajes = new BitmapTextureAtlas(this.getTextureManager(), 128, 64, TextureOptions.BILINEAR);
		this.mpunto = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.mMaderas1 = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		this.mMaderas2 = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.texturaPunto = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mpunto, this,"punto.png",0,0);
		
		this.tMadera3 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mMaderas2, this,"madera1.png",0,0);
		this.tMadera4 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mMaderas2, this,"madera3.png",0,16);
		
		
		this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 4096, 2048, TextureOptions.BILINEAR);
		this.mBgTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackgroundTexture, this,
						"fondo.png", 0, 0);
		this.mPasto = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackgroundTexture, this,
						"pasto.png", 2048, 0);
		this.mCircleFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, this,
						"tux.png", 0, 0, 4, 1);
		
		this.tExplosion2 = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas2, this,
						"explosion.png", 0, 0, 4, 1);
		this.tpajaro = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mPersonajes, this,
		  				"wndw.png", 0, 0, 2, 1);
		this.mResoltera = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, this,
						"resoltera.png", 0, 256, 2, 1);
		this.mAuxRes = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"aux.png",512,0);
		this.tfin = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						"fin.png",256,0);
	
		this.mPersonajes.load();this.mMaderas2.load();this.mMaderas1.load();this.mBitmapTextureAtlas.load();
		this.mBackgroundTexture.load();this.mpunto.load();this.mBitmapTextureAtlas2.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		camera.setCenter(1500, 360);
		mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);   
		this.mScene = new Scene();
		SpriteBackground bg = new SpriteBackground(new Sprite(0, 0, mBgTexture,this.getVertexBufferObjectManager()));
		mScene.setBackground(bg);
		Sprite pasto = new Sprite(-20,0,this.mPasto,this.getVertexBufferObjectManager());
		 madera1=new Sprite(1600,390,this.tMadera4,this.getVertexBufferObjectManager());
		madera2=new Sprite(1400,390,this.tMadera4,this.getVertexBufferObjectManager());
		 madera3=new Sprite(1430,370,this.tMadera3,this.getVertexBufferObjectManager());
		 madera4=new Sprite(1555,100,this.tMadera4,this.getVertexBufferObjectManager());
		 madera5=new Sprite(1429,100,this.tMadera4,this.getVertexBufferObjectManager());
		madera1.setScale(2);
		madera2.setScale(2);
		madera3.setScale(2);
		madera4.setScale(2);
		madera5.setScale(2);
		FixtureDef objectFixtureDef1 = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		Body  Bmadera1 = PhysicsFactory.createBoxBody(this.mPhysicsWorld, madera1,
				BodyType.DynamicBody, objectFixtureDef1);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(madera1,
				Bmadera1, true, true));
	
		FixtureDef objectFixtureDef2 = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		Body  Bmadera2 = PhysicsFactory.createBoxBody(this.mPhysicsWorld, madera2,
				BodyType.DynamicBody, objectFixtureDef2);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(madera2,
				Bmadera2, true, true));
		
		FixtureDef objectFixtureDef3 = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		Body  Bmadera3 = PhysicsFactory.createBoxBody(this.mPhysicsWorld, madera3,
				BodyType.DynamicBody, objectFixtureDef3);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(madera3,
				Bmadera3, true, true));
		
		FixtureDef objectFixtureDef4 = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		Body  Bmadera4 = PhysicsFactory.createBoxBody(this.mPhysicsWorld, madera4,
				BodyType.DynamicBody, objectFixtureDef4);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(madera4,
				Bmadera4, true, true));
		
		FixtureDef objectFixtureDef5 = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		Body  Bmadera5 = PhysicsFactory.createBoxBody(this.mPhysicsWorld, madera5,
				BodyType.DynamicBody, objectFixtureDef5);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(madera5,
				Bmadera5, true, true));
		
		madera1.setUserData(Bmadera1);
		madera2.setUserData(Bmadera2);
		madera3.setUserData(Bmadera3);
		madera4.setUserData(Bmadera4);
		madera5.setUserData(Bmadera5);
		mScene.attachChild(madera1);
		mScene.attachChild(madera2);
		mScene.attachChild(madera3);
		mScene.attachChild(madera4);
		mScene.attachChild(madera5);
		pajaro = new AnimatedSprite(1470,338,this.tpajaro,this.getVertexBufferObjectManager());
		pajaro.setScale(1.4f);
		FixtureDef objectFixtureDefp = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		Body  bwinbugs = PhysicsFactory.createCircleBody(this.mPhysicsWorld, pajaro,
				BodyType.DynamicBody, objectFixtureDefp);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(pajaro,
				bwinbugs, true, true));
		pajaro.setUserData(bwinbugs);
		pajaro.animate(new long[] { 1500, 700 }, 0, 1, true);
		mScene.attachChild(pajaro);
		mScene.attachChild(pasto);
		this.mScene.setOnSceneTouchListener(this);
		final Shape ground = new Rectangle(0, 690, 2000, 2,this.getVertexBufferObjectManager());
		final Shape left = new Rectangle(0, 0, 2, 720,this.getVertexBufferObjectManager());
		final Shape right = new Rectangle(2000 - 2, 0, 2, 720,this.getVertexBufferObjectManager());
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0,
				0.5f, 0.5f);
		
		
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) ground,
				BodyType.StaticBody, wallFixtureDef);  
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) left,
				BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) right,
				BodyType.StaticBody, wallFixtureDef);
		this.mScene.attachChild(ground);
		this.mScene.attachChild(left);
		
		left.setVisible(false);
		ground.setVisible(false);
		this.mScene.registerUpdateHandler(this.mPhysicsWorld);
		this.mScene.setOnAreaTouchListener(this);
		resolteraAux = new Sprite(230, 400,
				this.mAuxRes,this.getVertexBufferObjectManager()) ;
		resolteraAux.setScale(2); 
		
		resoltera = new AnimatedSprite(230, 400,
				this.mResoltera,this.getVertexBufferObjectManager()) ;
		resoltera.setScale(2);
		b = (int) System.currentTimeMillis(); 
		  b = b + 4500; 
		AnimatedSprite tux = new AnimatedSprite(200, 300,
				this.mCircleFaceTextureRegion,this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent evento,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (evento.isActionDown() && !evento.isActionMove()) {
					x = evento.getX();
					y = evento.getY();
					
				}
					if (evento.isActionMove()) {
						
						line.setVisible(true);
						line2.setVisible(true);
						line2.setPosition(240, 373, evento.getX()-47, evento.getY());

						
						this.setPosition(evento.getX() - this.getWidth() / 2,
								evento.getY() - this.getHeight() / 2);
						if(evento.getX()<100){
							resoltera.setCurrentTileIndex(1);
							line.setPosition(310, 370, evento.getX(), evento.getY());
							
							resolteraAux.setVisible(false);
						}else{
							resoltera.setCurrentTileIndex(0);
							line.setPosition(290, 373, evento.getX(), evento.getY());
							resolteraAux.setVisible(true);
						}
					}
				
				
				if (evento.isActionUp()) {
					while(!PilaP.isEmpty()){
						mScene.detachChild(PilaP.pop());
					}
					MainActivity.this.camera.setCenter(1500, 360);
					
					
					this.setVisible(false);
					swCol=false;
					resoltera.setCurrentTileIndex(0);
					if(!resolteraAux.isVisible()){
						resolteraAux.setVisible(true);
					}
					line.setVisible(false);
					line2.setVisible(false);
					if(mFaceCount!=0){
						removeFace(tuxConGravedad);
					}
					addFace(x, y, this.getX(), this.getY());
					
					this.setPosition(200, 300);
					  
					    
				}
				return true;
			}

		};
		tux.setScale(0.8f);
		tux.animate(new long[] { 1500, 700 ,0,0 }, 0, 3, true);
		
		
		line = new Line(230, 330, 230, 330, 0.2f,this.getVertexBufferObjectManager());
		line.setColor(0.30f,0.23f,0.07f);
		mScene.attachChild(line);
		mScene.attachChild(resoltera);
		this.mScene.attachChild(tux);
		line2 = new Line(200, 300, 200, 300, 0.2f,this.getVertexBufferObjectManager());
		line2.setColor(0.30f,0.23f,0.07f);
		mScene.attachChild(line2);
		this.mScene.registerTouchArea(tux);
		
		mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
				
			}

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				  
				  
				  if((int) System.currentTimeMillis() >= b && !swEs) { 
				MainActivity.this.camera.setCenter(500, 360);
				swEs=true;
				  }
				
				
				if(cont>=2 && !swF){
					   if(!swT){
					  a = (int) System.currentTimeMillis(); a = a + 1700; 
					   swT=true;
					   }
					  if((int) System.currentTimeMillis() >= a) { 
						  swF=true;
							 
						    
						  mScene.attachChild(fina);
							MainActivity.this.camera.setCenterDirect(500, 360);
							swFinal=true;
					  }
					
					
					
				}
				if((pajaro.collidesWith(madera1)||
						pajaro.collidesWith(madera2)||
						pajaro.collidesWith(right)||
						pajaro.collidesWith(ground)||
						pajaro.collidesWith(tuxConGravedad))&&!swP){
					explosion =new AnimatedSprite(pajaro.getX(),pajaro.getY(),tExplosion2.deepCopy(),MainActivity.this.getVertexBufferObjectManager());
					 
					removeFace(pajaro);
					
					cont++;
					explosion.setScale(2);
					swP=true;
					explosion.animate(100,false,new IAnimationListener() {
						
						

						@Override
						public void onAnimationStarted(
								AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite pAnimatedSprite,
								int pOldFrameIndex, int pNewFrameIndex) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite pAnimatedSprite,
								int pRemainingLoopCount, int pInitialLoopCount) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite pAnimatedSprite) {
							// TODO Auto-generated method stub
							mScene.detachChild(explosion);
							
						}
					});
					mScene.attachChild(explosion);
				}
				
				
				
				if(camera.getCenterX()>=1510  ){
					  
				       swE=true;	
				}else{
					if(camera.getCenterX()<=490  ){
						  
					       swE=true;	
					}	
				}
				if(mFaceCount!=0){
					
				if(
						tuxConGravedad.collidesWith(right )
						||tuxConGravedad.collidesWith(left)
						||tuxConGravedad.collidesWith(ground)
						){
					tuxConGravedad.animate(new long[] { 0, 0 ,0,700 }, 0, 3, true);
					if(mFaceCount!=0){
						
					    cont++;
				     	
				     	 mFaceCount=0;

					}
					
					
				}
					if(	tuxConGravedad.collidesWith(madera1)
						||tuxConGravedad.collidesWith(madera2)
						||tuxConGravedad.collidesWith(madera3)
						||tuxConGravedad.collidesWith(madera4)
						||tuxConGravedad.collidesWith(madera5))
						{
					swCol=true;
					
					
					
					tuxConGravedad.animate(new long[] { 0, 0 ,700,0 }, 0, 3, true);
					
						
					
					
				}
			}
				if(camera.getCenterX()>1500 && !sw ){
					  
				       camera.setCenter(1500, camera.getCenterY());	
				}else{
					if(camera.getCenterX()<500 && !sw ){
						  
					       camera.setCenter(500, camera.getCenterY());	
					}	
				}
				
				
			}
		});
		

		mScene.registerUpdateHandler(new TimerHandler(0.05f,
				new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				
				agregar();
				
				
				 
					
			}

		

		}
				
				
			
	));
		
		this.mScene.attachChild(resolteraAux);
		line.setVisible(false);
		line2.setVisible(false);
		fina = new Sprite(240,180,tfin,this.getVertexBufferObjectManager());
		fina.setScale(2.5f);
		
		
		return this.mScene;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		
		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent evento) {
if (this.mPhysicsWorld != null && !swFinal) {
			
			if (evento.isActionDown() &&!evento.isActionMove()) {
				
				posX=evento.getX();
				sw=true;
				swE=false;
				
			}else{
			if (evento.isActionMove()) {
				if(this.camera.getCenterX()<1550  
						&& this.camera.getCenterX()>450
						&& !swE){
				this.camera.setCenter(camera.getCenterX()+(posX-evento.getX()), camera.getCenterY());
				}
			}else{
				posX=0;
                sw=false;
                long currentTime = System.currentTimeMillis();         
	             
	             if((currentTime - lastShakeTime) < 500) {
	            
                    if(!swE){
                   	 swE=true;
                   	 MainActivity.this.camera.setCenter(1000,360);
   	            	 MainActivity.this.camera.setZoomFactor(0.5f);	 
                    }else{
                   	 swE=false;
                   	 MainActivity.this.camera.setZoomFactor(2.0f);
    					MainActivity.this.camera.setCenter(300, 600);
                    }
			}
			}
		}
		}
		return false;
	}
	public void agregar() {
		if(this.mFaceCount!=0 && !swCol){
		Sprite p = new Sprite(tuxConGravedad.getX()+tuxConGravedad.getWidth()/2,tuxConGravedad.getY()+tuxConGravedad.getHeight()/2,texturaPunto,this.getVertexBufferObjectManager());
		 int aux= (int) (Math.random() * 3);
		 if(aux==1){
	            p.setScale(1.4f);		 
		 }
if(aux==2){
p.setScale(0.7f);				 
		 }

		PilaP.add(p);
		mScene.attachChild(p);
		}
		
	}
	private void addFace(final float pX, final float pY, final float tX,
			final float tY) {
		this.mFaceCount++;
		final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		tuxConGravedad = new AnimatedSprite(200, 300, this.mCircleFaceTextureRegion,this.getVertexBufferObjectManager());
		tuxConGravedad.setScale(0.8f);
		body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, tuxConGravedad,
				BodyType.DynamicBody, objectFixtureDef);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(tuxConGravedad,
				body, true, true));
		tuxConGravedad.animate(new long[] { 1500, 700 ,0,0 }, 0, 3, true);
		tuxConGravedad.setUserData(body);
		this.mScene.registerTouchArea(tuxConGravedad);
		this.mScene.attachChild(tuxConGravedad);
		jumpFace(tuxConGravedad, pX, pY, tX, tY);
	}

	private void jumpFace(final AnimatedSprite face, final float pX,
			final float pY, final float tX, final float tY) {
		final Body faceBody = (Body) face.getUserData();
		final Vector2 velocity = Vector2Pool.obtain((pX - tX)/11 ,
				(pY - tY)/11 );
		faceBody.setLinearVelocity(velocity);
		Vector2Pool.recycle(velocity);
	}
	private void removeFace(final AnimatedSprite face) {
		final PhysicsConnector facePhysicsConnector = this.mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(face);
		this.mPhysicsWorld.unregisterPhysicsConnector(facePhysicsConnector);
		this.mPhysicsWorld.destroyBody(facePhysicsConnector.getBody());
		this.mScene.unregisterTouchArea(face);
		this.mScene.detachChild(face);
		System.gc();
	}

    
}
