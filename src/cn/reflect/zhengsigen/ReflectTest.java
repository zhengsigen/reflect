package cn.reflect.zhengsigen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class ReflectTest {

	public static void main(String[] args) throws ClassNotFoundException {
		// cn.reflect.zhengsigen.JSonDemo
		// cn.panqingshan.javase.demo.reflect.ExampleDemo
		Class<?> cls = Class.forName("cn.panqingshan.javase.demo.reflect.ExampleDemo");

		int modifiers = cls.getModifiers();
		//
		System.out.println(cls.getPackage());
		System.out.println();
		Type[] genericInterfaces = cls.getGenericInterfaces();
		
		
		for (Type t : genericInterfaces) {
			
			String typeName = t.getTypeName();
			
			int index = typeName.lastIndexOf("<");
			if(index>=0) {
				typeName= typeName.substring(0, index);
			}
			System.out.println("import " + typeName + ";");
		
		}
		
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append(Modifier.toString(modifiers) + " class " + cls.getSimpleName());

		String simpleName = cls.getSuperclass().getSimpleName();

		if (simpleName != null && !simpleName.equals("Object")) {

			stringBuffer.append(" extends " + simpleName);
		}

		Class[] interfaces = cls.getInterfaces();

		String inter = "";

		for (int i = 0; i < interfaces.length; i++) {

			TypeVariable[] typeParameters = interfaces[i].getTypeParameters();
			inter += interfaces[i].getSimpleName() + "<" + typeParameters[0].getName() + ">" + ",";

		}

		if (!inter.equals("")) {

			inter = inter.substring(0, inter.lastIndexOf(","));

			stringBuffer.append(" implements " + inter);

		}
		stringBuffer.append("{" + "\r\n" + "\r\n" + "    ");

		//

		String tempField = "";

		Field[] fields = cls.getDeclaredFields(); // 声明的属性
		for (Field field : fields) {

			tempField = Modifier.toString(field.getModifiers()) + " " + field.getGenericType() + " " + field.getName()
					+ " = " + field.getModifiers() + ";";

			stringBuffer.append(tempField + "\r\n" + "\r\n" + "    ");

		}

		// System.out.println(stringBuffer);

		Constructor[] cons = cls.getDeclaredConstructors();
		for (int i = 0; i < cons.length; i++) {
			String tempName = "";
			ArrayList<String> type = new ArrayList<>();
			ArrayList<String> value = new ArrayList<>();
			Constructor constructor = cons[i];
			tempName = Modifier.toString(constructor.getModifiers()) + " " + cls.getSimpleName() + "(";

			Class<?>[] parameterTypes = cons[i].getParameterTypes();
			for (Class<?> c : parameterTypes) {
				if (!c.getSimpleName().equals("Object")) {
					type.add(c.getSimpleName());
				}
			}
			Parameter[] parameters = cons[i].getParameters();
			for (Parameter parameter : parameters) {
				if (!parameter.getName().contains("arg")) {
					value.add(parameter.getName());
				}

			}
			if (type.size() == value.size()) {
				for (int k = 0; k < type.size(); k++) {
					tempName += type.get(k) + " ";
					tempName += value.get(k);
					if (k != type.size() - 1) {
						tempName += ",";
					}
				}
			}
			tempName += ")";
			stringBuffer.append(tempName + ";\r\n" + "\r\n" + "    ");
		}

		Method[] methods = cls.getDeclaredMethods();// 获取私有方法
		for (Method method : methods) {
			String tempName = "";
			ArrayList<String> type = new ArrayList<>();
			ArrayList<String> value = new ArrayList<>();
			tempName = Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " "
					+ method.getName() + "(";

			Class<?>[] parameterTypes = method.getParameterTypes();
			for (Class<?> c : parameterTypes) {
				if (!c.getSimpleName().equals("Object")) {
					type.add(c.getSimpleName());
				}
			}
			Parameter[] parameters = method.getParameters();
			for (Parameter parameter : parameters) {
				if (!parameter.getName().contains("arg")) {
					value.add(parameter.getName());
				}

			}
			if (type.size() == value.size()) {
				for (int i = 0; i < type.size(); i++) {
					tempName += type.get(i) + " ";
					tempName += value.get(i);
					if (i != type.size() - 1) {
						tempName += ",";
					}
				}
			}

			tempName += ")";
			stringBuffer.append(tempName + ";\r\n" + "\r\n" + "    ");
		}
		System.out.println(stringBuffer);
		System.out.println("}");
		
	}

}
