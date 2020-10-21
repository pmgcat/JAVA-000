import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {
	
	protected static final String CLASS_FILE_SUFFIX = ".xlass";
	
	private final String CLASS_DIR;
	
	public HelloClassLoader() {
		CLASS_DIR = ".";
	}
	
	public HelloClassLoader(String path) {
		CLASS_DIR = path;
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b;
		try {
			b = loadClassData(name);
		} catch (IOException e) {
			throw new ClassNotFoundException(e.getMessage(), e);
		}
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) throws IOException {
		return decode(Files.readAllBytes(Paths.get(CLASS_DIR, name + CLASS_FILE_SUFFIX)));
    }
    
    private byte[] decode(byte[] b) {
    	for (int i = 0; i < b.length; i++) {
    		b[i] = (byte)(255 - (short)(b[i]));
    	}
    	return b;
    }
    
    public static void main(String[] args) throws Exception {
    	String dir = "/Users/spike/Downloads/Hello";
    	Class<?> clazz = new HelloClassLoader(dir).findClass("Hello");
    	Method method = clazz.getMethod("hello");
    	method.invoke(clazz.newInstance(), args);
	}
}
