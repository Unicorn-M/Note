import java.io.IOException;
import java.io.InputStram;
import java.util.Properties;

/*
 * 工厂类 专门帮助我们创建对象
 */
 public class Factory{
	 //读取properties里面的配置信息
 static Properties properties;
	//创建一个静态的map,存储资源文件的键值对
 static Map<String, Object> beans;
 
	static{
		try{
			
			InputStram in = Factory.class.getClassLoader().getResourceAsStream("资源文件名称");
			properties = new properties;
			properties.load(in);
			beans = new HashMap<String, Object>();
			while(keys.hashMoreElements()){
				String key = keys.nextElement().toString();
				//通过key,获取value
				String value = properties.getProperty(key);
				Object o = Class.forName(value).newInstance;
				beans.put(key, o);
			}
			
		}catch(Exception){
			e.printStackTrace();
		}
	}
 
	/*
	 *获取Bean的方法
	 *name : 资源文件中的k(键)
	 */
	public static Object getBean(String name){
		return beans.get(name);
	}
 
	 
 }
 
 