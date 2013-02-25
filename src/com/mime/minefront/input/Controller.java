package com.mime.minefront.input;

public class Controller {
	public double x, z, rotation, xA, zA, rotationA;
	
	public void tick(boolean forward, boolean back,boolean left,boolean right,boolean turnLeft,boolean turnRight){
		double rotationSpeed = 1;
		double walkSpeed = 1;
		double xMove = 0;
		double zMove = 0;
		
		if(forward){
			zMove++; 
			System.out.println("Forward!");
			}
		
		if(back){
			zMove--;
			System.out.println("Backwards");
			}
		
		if(left){
			xMove++;
			System.out.println("Left!");
			}
		if(right){
			xMove--;
			System.out.println("Right!");
			}
		if(turnLeft){
			rotationA += rotationSpeed;
			}
		
		if(turnRight){
			rotationA -= rotationSpeed;
			}
		
		xA += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		zA += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		
		x += xA;
		z += zA;
		
		xA += 0.1;
		zA += 0.1;
		
		rotation += rotationA;
		rotationA *= 0.8;
		
		
		
		
		
		
	}
	
	
}


