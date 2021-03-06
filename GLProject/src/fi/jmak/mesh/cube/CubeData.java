package fi.jmak.mesh.cube;

import java.util.ArrayList;
import java.util.List;

import fi.jmak.math.vector.Vec3f;

public class CubeData
{
	private static CubeData instance;
	
	private float[] data;
	
	private int vertices;

	private static final float size = 0.5f;
	private static float left   = -size;
	private static float right  =  size;
	private static float up		=  size;
	private static float down	= -size;
	private static float near	= -size;
	private static float far	=  size;
	
	private CubeData()
	{
		
		data = new float[] {
			//position				//texture	//normal
			left,	up,		near,	0, 0,		0, 0, -1,		//front face
			left,	down,	near,	0, 1,		0, 0, -1,
			right,	down,	near,	1, 1,		0, 0, -1,
			
			left,	up,		near,	0, 0,		0, 0, -1,
			right,	down,	near,	1, 1,		0, 0, -1,
			right,	up,		near,	1, 0,		0, 0, -1,
			
			left,	up,		far,	1, 0,		0, 0, 1,		//back face
			right,	down,	far,    0, 1,		0, 0, 1,
			left,	down,	far,    1, 1,		0, 0, 1,
			                             
			left,	up,		far,    1, 0,		0, 0, 1,
			right,	up,		far,    0, 0,		0, 0, 1,
			right,	down,	far,    0, 1,		0, 0, 1,
			
			
			left,	up,		far,	0, 0,		0, 1, 0,		//top face
			left,	up,		near,   0, 1,		0, 1, 0,
			right,	up,		near,   1, 1,		0, 1, 0,
			                             
			left,	up,		far,    0, 0,		0, 1, 0,
			right,	up,		near,   1, 1,		0, 1, 0,
			right,	up,		far,    1, 0,		0, 1, 0,
			
			left,	down,	far,	0, 1,		0, -1, 0,		//bottom face
			right,	down,	near,   1, 0,		0, -1, 0,
			left,	down,	near,   0, 0,		0, -1, 0,
			                             
			left,	down,	far,    0, 1,		0, -1, 0,
			right,	down,	far,    1, 1,		0, -1, 0,
			right,	down,	near,   1, 0,		0, -1, 0,
			
			left,	up,		far,	0, 0,		-1, 0, 0,		//left face
			left,	down,	far,    0, 1,		-1, 0, 0,
			left,	down,	near,   1, 1,		-1, 0, 0,
			                             
			left,	up,		far,    0, 0,		-1, 0, 0,
			left,	down,	near,   1, 1,		-1, 0, 0,
			left,	up,		near,   1, 0,		-1, 0, 0,
			
			right,	up,		near,	0, 0,		1, 0, 0,		//right face
			right,	down,	near,   0, 1,		1, 0, 0,
			right,	down,	far,    1, 1,		1, 0, 0,
			                             
			right,	up,		near,   0, 0,		1, 0, 0,
			right,	down,	far,    1, 1,		1, 0, 0,
			right,	up,		far,    1, 0,		1, 0, 0
		};

		vertices = data.length / (3 + 2 + 3);
	}
	
	public static List<Vec3f> getCollPoints()
	{
		List<Vec3f> points = new ArrayList<Vec3f>();
		
		points.add(new Vec3f(left,	up,		far));
		points.add(new Vec3f(left,	down,	far));
		points.add(new Vec3f(right, down,	far));
		points.add(new Vec3f(right,	up,		far));
		
		points.add(new Vec3f(left,	up,		near));
		points.add(new Vec3f(left,	down,	near));
		points.add(new Vec3f(right, down,	near));
		points.add(new Vec3f(right,	up,		near));
		
		return points;
	}
	
	public float getSize()
	{
		return size;
	}
	
	public static float[] get()
	{
		if (instance == null)
			instance = new CubeData();
		
		return instance.data;
	}

	public static int vertices()
	{
		return instance.vertices;
	}
	
	
}
