package fi.jmak.mesh.sphere.icoshaedron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.jmak.math.vector.Vec3f;

public class IcoData
{
	private class TriangleIndices
	{
		public int v0;
		public int v1;
		public int v2;
		
		public TriangleIndices(int v0, int v1, int v2)
		{
			this.v0 = v0;
			this.v1 = v1;
			this.v2 = v2;
		}
	}
	
	private List<Vec3f> vertices;
	private List<Integer> indices;
	
	private int index;
	private Map<Long, Integer> middlePointIndexCache;
	private List<TriangleIndices> faces;
	private float[] vertArray;
	private int[] indexArray;
	private float radius;
	private float[] normArray;
	
	public void create()
	{
		create(0, 0.5f);
	}
	
	public void create(int recursionLevel)
	{
		create(recursionLevel, 0.5f);
	}
	
	public void create(int recursionLevel, float radius)
	{
		this.radius = radius;
		vertices = new ArrayList<Vec3f>();
		indices = new ArrayList<Integer>();
		
		middlePointIndexCache = new HashMap<Long, Integer>();
		index = 0;
		
		//create 12 vertices
		
		float t = (float) ( (1.0 + Math.sqrt(5.0)) / 2.0 );
		
		addVertex(new Vec3f(-1,  t, 0));
		addVertex(new Vec3f( 1,  t, 0));
		addVertex(new Vec3f(-1, -t, 0));
		addVertex(new Vec3f( 1, -t, 0));
		
		addVertex(new Vec3f(0, -1,  t));
		addVertex(new Vec3f(0,  1,  t));
		addVertex(new Vec3f(0, -1, -t));
		addVertex(new Vec3f(0,  1, -t));
		
		addVertex(new Vec3f( t, 0, -1));
		addVertex(new Vec3f( t, 0,  1));
		addVertex(new Vec3f(-t, 0, -1));
		addVertex(new Vec3f(-t, 0,  1));
	
		//create 20 faces
		faces = new ArrayList<TriangleIndices>();
		
		//5 faces around point 0
		faces.add(new TriangleIndices(0, 	11, 	5));
        faces.add(new TriangleIndices(0, 	5, 		1));
        faces.add(new TriangleIndices(0, 	1, 		7));
        faces.add(new TriangleIndices(0, 	7, 		10));
        faces.add(new TriangleIndices(0, 	10, 	11));
        
        // 5 adjacent faces 
        faces.add(new TriangleIndices(1,	5, 		9));
        faces.add(new TriangleIndices(5, 	11, 	4));
        faces.add(new TriangleIndices(11, 	10, 	2));
        faces.add(new TriangleIndices(10, 	7, 		6));
        faces.add(new TriangleIndices(7, 	1, 		8));

        // 5 faces around point 3
        faces.add(new TriangleIndices(3,	9, 		4));
        faces.add(new TriangleIndices(3,	4,		2));
        faces.add(new TriangleIndices(3, 	2, 		6));
        faces.add(new TriangleIndices(3, 	6, 		8));
        faces.add(new TriangleIndices(3, 	8, 		9));

        // 5 adjacent faces 
        faces.add(new TriangleIndices(4, 	9, 		5));
        faces.add(new TriangleIndices(2, 	4, 		11));
        faces.add(new TriangleIndices(6, 	2, 		10));
        faces.add(new TriangleIndices(8, 	6, 		7));
        faces.add(new TriangleIndices(9, 	8, 		1));

        //refine triangles
        for (int i = 0; i < recursionLevel; i++)
        {
        	ArrayList<TriangleIndices> faces2 = new ArrayList<TriangleIndices>();
        	
        	for (TriangleIndices tri : faces)
        	{
        		//replace triangle by 4 triangles
        		int a = getMiddlePoint(tri.v0, tri.v1);
        		int b = getMiddlePoint(tri.v1, tri.v2);
        		int c = getMiddlePoint(tri.v2, tri.v0);
        		
        		faces2.add(new TriangleIndices(tri.v0, 	a, 	c));
        		faces2.add(new TriangleIndices(tri.v1, 	b, 	a));
        		faces2.add(new TriangleIndices(tri.v2, 	c, 	b));
        		faces2.add(new TriangleIndices(		a, 	b, 	c));
        		
        	}
        	
        	faces = faces2;
        }
        
        //add to mesh,
        for (TriangleIndices tri : faces)
        {
        	//swap render order to clockwise (v0, v1, v2) = ccw
        	indices.add(tri.v2);
        	indices.add(tri.v1);
        	indices.add(tri.v0);
        }
        
        vertArray = new float[vertices.size() * 3];
        normArray = new float[vertices.size() * 3];

        for (int i = 0; i < vertices.size(); i ++)
        {
        	vertArray[i * 3	   ] = vertices.get(i).getX();
        	vertArray[i * 3 + 1] = vertices.get(i).getY();
        	vertArray[i * 3 + 2] = vertices.get(i).getZ();
        	
        	normArray[i * 3	   ] = vertices.get(i).getX();
        	normArray[i * 3 + 1] = vertices.get(i).getY();
        	normArray[i * 3 + 2] = vertices.get(i).getZ();
        }
        
        indexArray = new int[indices.size()];
        for (int i = 0; i < indices.size(); i ++)
        {
        	indexArray[i] = indices.get(i);
        }
        
	}
	
	public float[] getVertArray()
	{
		return vertArray;
	}
	public float[] getNormArray()
	{
		return normArray;
	}
	
	public int[] getIndexArray()
	{
		return indexArray;
	}
	
	private int addVertex(Vec3f p)
	{
		vertices.add(p.length(radius));
		return index ++;
	}
	
	private int getMiddlePoint(int p0, int p1)
	{
		boolean firstIsSmaller = p0 < p1;
		
		long smallerIndex = firstIsSmaller ? p0 : p1;
		long greaterIndex = firstIsSmaller ? p1 : p0;
		long key = (smallerIndex << 32) + greaterIndex;
				
		//if key exists, return it
		if (middlePointIndexCache.containsKey(key))
		{
			return middlePointIndexCache.get(key);
		}
		
		Vec3f point0 = vertices.get(p0);
		Vec3f point1 = vertices.get(p1);
		Vec3f middle = new Vec3f(
			(point0.getX() + point1.getX()) / 2.0f,
			(point0.getY() + point1.getY()) / 2.0f,
			(point0.getZ() + point1.getZ()) / 2.0f);
		
		int i = addVertex(middle);
		
		this.middlePointIndexCache.put(key, i);
		return i;
	}
}
