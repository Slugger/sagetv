package sage.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class PluginJarBuilder {

	public PluginJarBuilder(String id, List<String> jars) {
		if(id != null && id.length() > 0 && jars != null && jars.size() > 0) {
			File root = new File(new File(System.getProperty("user.dir")), String.format("jar_staging/%s", id));
			root.mkdirs();
			File bld = new File(root, "build.gradle");
			bld.delete();
			StringWriter sw = new StringWriter();
			sw.append("dependencies {\n");
			for(String jar : jars)
				sw.append(String.format("\tplugin '%s'\n", jar));
			sw.append("}\n");
			Writer w = null;
			try {
				w = new FileWriter(bld);
				w.append(sw.toString());
			} catch(IOException e) {
				bld.delete();
				System.out.println("ERROR: failed to create jar builder! [" + bld.getAbsolutePath() + "]");
			} finally {
				if(w != null)
					try { w.close(); } catch(Throwable t) {}
			}
		}		
	}
}
