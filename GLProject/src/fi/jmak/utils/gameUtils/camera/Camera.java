package fi.jmak.utils.gameUtils.camera;

import fi.jmak.math.quaternion.Quatf;
import fi.jmak.math.vector.Vec3f;

public class Camera
{
	private Vec3f translation;
	private Quatf orientation;
	
	public Camera(Vec3f translation, Quatf orientation)
	{
		this.translation = translation;
		this.orientation = orientation;
	}
	
	public Camera(Vec3f translation)
	{
		this(translation, new Quatf().toIdentity());
	}
	
	public Camera(float x, float y, float z)
	{
		this(new Vec3f(x, y, z));
	}
	
	public Camera()
	{
		this(new Vec3f(0, 0, 0));
	}
	
	public void translate(Vec3f trans)
	{
		translate(trans.getX(), trans.getY(), trans.getZ());
	}
	
	public void translate(float x, float y, float z)
	{
		getTranslation().set(getTranslation().add(x, y, z));
	}
	
	public void rotateGlobal(Vec3f ax, float rad)
	{
		Quatf rotAngle = new Quatf().rotate(ax, rad);
		orientation = (rotAngle.mul(orientation)).normalized();
	}
	
	public void rotateLocal(Vec3f ax, float rad)
	{
		Quatf rotAngle = new Quatf().rotate(ax, rad);
		orientation = (orientation.mul(rotAngle)).normalized();
	}
	
	public Vec3f getTranslation()
	{
		return translation;
	}
	
	public Quatf getOrientation()
	{
		return orientation;
	}
	
	
	
	
	
}
