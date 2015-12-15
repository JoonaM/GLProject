package fi.jmak.window;

public interface GLRender
{
	public void init() throws Exception;
	public void tick(float delta) throws Exception;
	public void render() throws Exception;
	public void destroy();
}
