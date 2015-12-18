package fi.jmak.shapes;

public class CubeData
{
	private static CubeData instance;
	
	private float[] data;

	private int vertices;

	private CubeData()
	{
		float size 	= 0.5f;
		float left  = -size;
		float right =  size;
		float up	=  size;
		float down	= -size;
		float near	= -size;
		float far	=  size;
		
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
