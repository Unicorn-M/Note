import java.io.IOException;
import java.io.InputStram;
import java.util.Properties;

/*
 * ������ ר�Ű������Ǵ�������
 */
 public class Factory{
	 //��ȡproperties�����������Ϣ
 static Properties properties;
	//����һ����̬��map,�洢��Դ�ļ��ļ�ֵ��
 static Map<String, Object> beans;
 
	static{
		try{
			
			InputStram in = Factory.class.getClassLoader().getResourceAsStream("��Դ�ļ�����");
			properties = new properties;
			properties.load(in);
			beans = new HashMap<String, Object>();
			while(keys.hashMoreElements()){
				String key = keys.nextElement().toString();
				//ͨ��key,��ȡvalue
				String value = properties.getProperty(key);
				Object o = Class.forName(value).newInstance;
				beans.put(key, o);
			}
			
		}catch(Exception){
			e.printStackTrace();
		}
	}
 
	/*
	 *��ȡBean�ķ���
	 *name : ��Դ�ļ��е�k(��)
	 */
	public static Object getBean(String name){
		return beans.get(name);
	}
 
	 
 }
 
 