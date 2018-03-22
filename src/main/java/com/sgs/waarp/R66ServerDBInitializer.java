package com.sgs.waarp;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.waarp.openr66.server.ServerInitDatabase;

public class R66ServerDBInitializer {

	public void initdb() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("waarpdb.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String propcondition = properties.getProperty("com.sgs.waarpb.auto");
		String mycondition1 = new String("create");
		String mycondition2 = new String("update");
		File waarpconfigFile = new File(this.getClass().getClassLoader().getResource("config-serverA.xml").getFile());
		File waarpauthFile = new File(this.getClass().getClassLoader().getResource("OpenR66-authent-A.xml").getFile());
		File waarplimitFile = new File(this.getClass().getClassLoader().getResource("limitConfiga.xml").getFile());
		String waarpconfig = waarpconfigFile.toString();
		String directory = waarpconfig.substring(0, waarpconfig.lastIndexOf(File.separator));
		String[] update = { waarpconfig, "-upgradeDb" };
		if (propcondition.equals(mycondition1)) {
			String[] waarpdbinit = { waarpconfig, "-initdb" };
			String[] loadBusiness = { waarpconfig, "-loadBusiness", waarpconfig };
			String[] loadAlias = { waarpconfig, "-loadAlias", waarpconfig };
			String[] loadRoles = { waarpconfig, "-loadRoles", waarpconfig };
			String[] loadRules = { waarpconfig, "-dir", directory };
			String[] loadAuths = { waarpconfig, "-auth", waarpauthFile.toString() };
			String[] loadLimit = { waarpconfig, "-limit", waarplimitFile.toString() };
			ServerInitDatabase.initR66database(waarpdbinit);

			ServerInitDatabase.initR66database(loadBusiness);
			ServerInitDatabase.initR66database(loadAlias);
			ServerInitDatabase.initR66database(loadRoles);
			ServerInitDatabase.initR66database(loadRules);
			ServerInitDatabase.initR66database(loadAuths);
			ServerInitDatabase.initR66database(loadLimit);
			ServerInitDatabase.initR66database(update);
			
			new CreateTables().init();

		}
		if (propcondition.equals(mycondition2)) {
			ServerInitDatabase.initR66database(update);
		}
	}
}
