package fi.jmak.transformation;

import fi.jmak.camera.Camera;
import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.quaternion.Quatf;
import fi.jmak.math.vector.Vec3f;

public class Transform
{
	private static Camera camera;

	private Vec3f translation;
	private Vec3f scale;
	private Quatf orientation;
	
	private Mat4f rotMat;
	private Mat4f transMat;
	private Mat4f scaleMat;

	private Mat4f modelMatrix;

	private Mat4f normalMatrix;
	
	public Transform()
	{
		translation = new Vec3f();
		scale 		= new Vec3f(1, 1, 1);
		orientation 		= new Quatf().toIdentity();
		
		transMat 	= new Mat4f();
		scaleMat 	= new Mat4f();
		rotMat	 	= new Mat4f();
		
		modelMatrix = new Mat4f();
		normalMatrix = new Mat4f();
	}
	
	public void setTranslation(Vec3f translation)
	{
		setTranslation(translation.getX(), translation.getY(), translation.getZ());
	}
	public void setTranslation(float x, float y, float z) { this.translation.set(x, y, z); }
	
	public void setScale(Vec3f scale)
	{
		setScale(scale.getX(), scale.getY(), scale.getZ());
	}
	public void setScale(float x, float y, float z) { this.scale.set(x, y, z); }
	
	public void rotate(Vec3f ax, float rad)
	{
		orientation.set( orientation.rotate(ax, rad) );
	}
	public void rotate(float x, float y, float z, float rad)
	{
		orientation.set( orientation.rotate(x, y, z, rad) );
	}

	public Vec3f getTranslation() 	{ return translation; }
	public Vec3f getScale() 		{ return scale; }
	public Quatf getOrientation()	{ return orientation; }
	
	public Mat4f transformModel()
	{
		transMat = transMat.translation(translation);
		scaleMat = scaleMat.scale(scale);
		rotMat 	 = rotMat.rotation(orientation);
		
		modelMatrix = transMat.mul(rotMat.mul(scaleMat));
		
		return modelMatrix;
	}

	public Mat4f getTranslationMatrix()
	{
		return transMat;
	}
	public Mat4f getScaleMatrix()
	{
		return scaleMat;
	}
	public Mat4f getRotationMatrix()
	{
		return rotMat;
	}
	public Mat4f getModelMatrix()
	{
		return modelMatrix;
	}
	
	public static void setCamera(Camera camera)
	{
		Transform.camera = camera;
	}
	
	public static Camera getCamera()
	{
		return camera;
	}

	public Mat4f normalTransformT()
	{
		Mat4f invTrans = Mat4f.invertTrans(transMat);
		Mat4f invRot = Mat4f.invertRot(rotMat);
		
		return invTrans .mul (invRot);
	}
	
	public Mat4f getNormalMatrix()
	{
		return normalMatrix;
	}
}
