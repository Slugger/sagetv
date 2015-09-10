package sage.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class PluginJarBuilder {

	public PluginJarBuilder(String id, List<String> jars, boolean ignoreTransitiveDeps) {
		if(id != null && id.length() > 0 && jars != null && jars.size() > 0) {
			File root = new File(CorePluginManager.JAR_STAGE_ROOT, id);
			root.mkdirs();
			File bld = new File(root, "build.gradle");
			bld.delete();
			StringBuilder sb = new StringBuilder();
			sb.append("dependencies {\n");
			for(String jar : jars) {
				sb.append(String.format("\tplugin('%s')", jar));
				if(ignoreTransitiveDeps)
					sb.append(" { transitive = false }");
				sb.append("\n");
			}
			sb.append("}\n");
			Writer w = null;
			try {
				w = new FileWriter(bld);
				w.append(sb.toString());
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
