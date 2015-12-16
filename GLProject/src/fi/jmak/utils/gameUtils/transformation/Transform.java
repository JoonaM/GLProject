package fi.jmak.utils.gameUtils.transformation;

import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.quaternion.Quatf;
import fi.jmak.math.vector.Vec3f;
import fi.jmak.utils.gameUtils.camera.Camera;

public class Transform
{
	private static Camera camera;

	private Vec3f translation;
	private Vec3f scale;
	private Quatf rot;
	
	private Mat4f rotMat;
	private Mat4f transMat;
	private Mat4f scaleMat;

	private Mat4f transform;
	
	public Transform()
	{
		translation = new Vec3f();
		scale 		= new Vec3f(1, 1, 1);
		rot 		= new Quatf().toIdentity();
		
		transMat 	= new Mat4f();
		scaleMat 	= new Mat4f();
		rotMat	 	= new Mat4f();
	}
	
	public Mat4f doTransform()
	{
		transMat = transMat.translation(translation);
		scaleMat = scaleMat.scale(scale);
		rotMat 	 = rotMat.rotation(rot);
		
		transform = camera.getProjection().mul(camera.getTransform().mul(transMat.mul(rotMat.mul(scaleMat))));
		
		return transform;
	}
	
	public Mat4f getTransform()
	{
		return transform;
	}
	
	public static void setCamera(Camera camera)
	{
		Transform.camera = camera;
	}
	
	public static Camera getCamera()
	{
		return camera;
	}
	
	public Vec3f getTranslation() { return translation; }
	public Vec3f getScale() { return scale; }
	public Quatf getRotation() { return rot; }
	
}
