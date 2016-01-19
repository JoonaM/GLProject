package fi.jmak.camera;

import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.quaternion.Quatf;
import fi.jmak.math.vector.Vec3f;

public class Camera
{
	private static Camera main;
	
	private Mat4f projection;
	private Mat4f transformation;
	private Mat4f orienMat;
	
	private Vec3f translation;
	private Quatf orientation;

	private float fov;
	private float aspect;
	private float near;
	private float far;
	
	public Camera(Vec3f translation, Quatf orientation)
	{
		this.translation = translation;
		this.orientation = orientation;
		
		transformation = new Mat4f();
		orienMat = new Mat4f();
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
	
	public float getFOV()
	{
		return fov;
	}
	public float getAspect()
	{
		return aspect;
	}
	public float getNear()
	{
		return near;
	}
	public float getFar()
	{
		return far;
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

	public void setProjection(float fov, float aspect, float near, float far)
	{
		this.fov = fov;
		this.aspect = aspect;
		this.near = near;
		this.far = far;
		
		this.projection = new Mat4f().perspective(fov, aspect, near, far);
	}
	
	public Mat4f transform()
	{
		transformation = transformation.translation(translation.mul(-1));
		orienMat = orienMat.rotation(orientation.conjugate());
		
		transformation = orienMat.mul(transformation);
		
		return transformation;
	}
	
	public Mat4f getTransform()
	{
		return transformation;
	}

	public Mat4f getProjection()
	{
		return projection;
	}
	
	public void lookAt(Vec3f translation, Vec3f target, Vec3f up)
	{
		this.translation	= translation;
		this.orientation 	= Quatf.lookAt(translation, target, up);
	}

	public static void setMain(Camera mainCamera)
	{
		main = mainCamera;
	}	
	public static Camera getMain()
	{
		return main;
	}
}
