package sage.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class JarStager {

	static private final File JARS_DIR = new File(new File(System.getProperty("user.dir")), "JARs");
	static private final File STAGING_DIR = new File(CorePluginManager.JAR_STAGE_ROOT, ".sagejars");
	
	public static void main(String[] args) {
		for(File f : JARS_DIR.listFiles())
			f.delete();
		for(File f : STAGING_DIR.listFiles()) {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(f);
				os = new FileOutputStream(new File(JARS_DIR, f.getName()));
				byte[] buf = new byte[65536];
				int len;
				while((len = is.read(buf)) > 0) {
					os.write(buf, 0, len);
				}
			} catch(Throwable t) {
				t.printStackTrace();
			} finally {
				if(os != null)
					try { os.close(); } catch(Throwable t1) { t1.printStackTrace(); }				
				if(is != null)
					try { is.close(); } catch(Throwable t1) { t1.printStackTrace(); }
			}
		}
	}
}
