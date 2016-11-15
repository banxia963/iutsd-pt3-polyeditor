package Navigateur;
import Blueprint.Room;
import Blueprint.Wall;
public class NavigateurModel {
		
		private static float posX = 2;
		private float posZ = 2;
		private float headingY = 0; // heading of player; about y-axis
		private float lookUpAngle =0.0f;
		
		private float moveIncrement = 0.05f;
		private float turnIncrement = 1.5f; // each turn in degree
		private float lookUpIncrement = 1.0f;
		
		private float walkBias = 0;
		private float walkBiasAngle = 0;
		
		private static boolean isLightOn = false;

		public float getPosX(){
			return posX;
		}
		
		public float getPosZ(){
			return posZ;
		}
		
		public float getHeadingY(){
			return headingY;
		}
		
		public float getLookUpAngle(){
			return lookUpAngle;
		}
		
		public float getMoveIncrement() {
			return moveIncrement;
		}
		
		public float getTurnIncrement(){
			return turnIncrement;
		}
		
		public float getLookUpIncrement(){
			return lookUpIncrement;
		}
		
		public float getWalkBias(){
			return walkBias;
		}
		
		public float getWalkBiasAngle(){
			return walkBiasAngle;
		}
		
		public void turnLeft(){
			headingY += turnIncrement;
		}
		
		public void turnRight(){
			headingY -= turnIncrement;
		}
		
		public boolean getIsLigntOn(){
			return isLightOn;
		}
		
		public void turnLight(){
			isLightOn = !isLightOn;
		}
		public void moveIn(){
			posX -= (float)Math.sin(Math.toRadians(headingY)) * moveIncrement;
			posZ -= (float)Math.cos(Math.toRadians(headingY)) * moveIncrement;
			walkBiasAngle = (walkBiasAngle >= 359.0f) ? 0.0f : walkBiasAngle + 10.0f;
			walkBias = (float)Math.sin(Math.toRadians(walkBiasAngle)) / 20.0f;
		}
		
		public void moveOut() {
			posX += (float)Math.sin(Math.toRadians(headingY)) * moveIncrement;
			posZ += (float)Math.cos(Math.toRadians(headingY)) * moveIncrement;
			walkBiasAngle = (walkBiasAngle <=1.0f) ? 359.0f : walkBiasAngle - 10.0f;
			walkBias = (float)Math.sin(Math.toRadians(walkBiasAngle)) / 20.0f;
		}
		
		public void lookUp(){
			lookUpAngle -= lookUpIncrement;
		}
		
		public void lookDown() {
			lookUpAngle += lookUpIncrement;
		}
	}


