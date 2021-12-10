package TYPES;

public class TYPE_INSTANCE extends TYPE
{
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TYPE_INSTANCE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TYPE_INSTANCE() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TYPE_INSTANCE getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_INSTANCE();
			instance.name = "instance";
		}
		return instance;
	}
}
