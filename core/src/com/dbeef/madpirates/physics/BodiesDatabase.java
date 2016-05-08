package com.dbeef.madpirates.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class BodiesDatabase extends Array<Body>{

	double shipSailingSpeed = 120;
	double shipManouverSpeed;
	
	FixtureDef fixtureDef;
	PolygonShape shape;
	BodyDef bodyDef;
	Body ship;
	
	double destinatedX;
	double destinatedY;
	double destinatedAngle;

	double pointsX;
	double pointsY;
	
	boolean loaded = false;
	
	 World world;

	 public static final float FPSCAP = 1/60F;
	 private float accumulator = 0;
	 
	 
	 
	 
	 public int add (Body value, BodyDef bodyDef, FixtureDef fixtureDef) {
		Body b;
	    b = world.createBody(bodyDef);
        b.createFixture(fixtureDef);

        
			Body[] items = this.items;
			if (size == items.length) items = resize(Math.max(8, (int)(size * 1.75f)));
			items[size++] = b;
			
			if(loaded == false){


			}
			
			
			return this.size - 1;
	 }

	 public BodiesDatabase () {
			this(true, 16);
		}	 
	 public BodiesDatabase (boolean ordered, int capacity) {
			this.ordered = ordered;
			items = (Body[])new Body[capacity];
			 // Create a physics world, the heart of the simulation.  The Vector 
		     //passed in is gravity
		   world = new World(new Vector2(0, 0f), true);	 


	 }
	 
	 
	 
	 public void simulate(float deltaTime){
	//How does it work? 
	//http://saltares.com/blog/games/fixing-your-timestep-in-libgdx-and-box2d/
		 
		 accumulator+=deltaTime;
		 while(accumulator>FPSCAP){
		    world.step(FPSCAP, 6, 2);
		    accumulator-=FPSCAP;
		
			// End of world physics simulations

			// Now let's change ship's angle and linear speed if neccesary

			// From:
			// http://www.iforce2d.net/b2dtut/rotate-to-angle

			this.get(0).setAwake(true);
/*
			float bodyAngle = this.get(0).getAngle();

			float nextAngle = (float) (bodyAngle + this.get(0)
					.getAngularVelocity() / 60.0);
			float totalRotation = (float) (destinatedAngle - nextAngle);
			while (totalRotation < -180 * MathUtils.degreesToRadians)
				totalRotation += 360 * MathUtils.degreesToRadians;
			while (totalRotation > 180 * MathUtils.degreesToRadians)
				totalRotation -= 360 * MathUtils.degreesToRadians;
			float desiredAngularVelocity = totalRotation / 2;
			float torque = (float) (this.get(0).getInertia()
					* desiredAngularVelocity / (1 / 1000.0));
			this.get(0).applyTorque(torque, true);

			sideToRotate(this.get(0).getPosition().x + pointsX, this.get(0)
					.getPosition().y + pointsY);
			this.get(0)
					.setLinearVelocity(
							(float) ((shipSailingSpeed * Math.sin(bodyAngle)) + 5f * (shipSailingSpeed * Math
									.sin(-bodyAngle))),
							(float) ((shipSailingSpeed * Math.cos(bodyAngle) + 5f * (shipSailingSpeed * Math
									.cos(-bodyAngle)))));
*/
			
			float bodyAngle = this.get(0).getAngle();

float linearVelocityX =(float)Math.cos(bodyAngle + 1.45f) * 100 ;
float linearVelocityY =(float)Math.sin(bodyAngle + 1.45f) * 100 ;

			

			
			if(bodyAngle < destinatedAngle){
				this.get(0).setAngularVelocity(0.3f);
				this.get(0).setLinearVelocity(linearVelocityX,linearVelocityY);

			}
			
			if(bodyAngle > destinatedAngle - 0.1f && bodyAngle < destinatedAngle + 0.1f){
				this.get(0).setAngularVelocity(0f);
		//		this.get(0).setLinearVelocity(0,0);
				
			}
			
			if(bodyAngle > destinatedAngle){
				this.get(0).setAngularVelocity(-0.3f);
				this.get(0).setLinearVelocity(linearVelocityX,linearVelocityY);
				
			}
			if(bodyAngle < destinatedAngle - 0.1f && bodyAngle > destinatedAngle + 0.1f){
				this.get(0).setAngularVelocity(0f);
		//		this.get(0).setLinearVelocity(0,0);
				
			
			}
			
			
			
		}
	 
	 }

	public double sideToRotate(double mouseX, double mouseY){

		//Trigonometry, we've got mouse position and ship position
		//We want an angle between X axis and mouse click location
		 
		 Vector2 clickedPoint = new Vector2();
		 clickedPoint.set((int)mouseX, (int)mouseY);
		 Vector2 toTarget = new Vector2();
		 toTarget.x= clickedPoint.x - this.get(0).getPosition().x;
		 toTarget.y= clickedPoint.y - this.get(0).getPosition().y;
		 
destinatedAngle = MathUtils.atan2( -toTarget.x, toTarget.y );

		    destinatedX = mouseX;
		    destinatedY = mouseY;
		  

		    pointsX =  destinatedX - this.get(0).getPosition().x;
		    pointsY =  destinatedY - this.get(0).getPosition().y;
		    
//Now we've got destinatedAngle in radians

return ((float)destinatedAngle);
	 }
	 
		public void getShipCharacteristics(double sS, double mS){
		 shipSailingSpeed = sS;
		 shipManouverSpeed = mS;
	 }
	 
	 public void dispose(){
		 world.dispose();
	 }
	 public World giveWorld(){
		 return world;
	 }
}