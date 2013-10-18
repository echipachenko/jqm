
package com.enioka.jqm.jndi;

import javax.naming.spi.NamingManager;

import com.enioka.jqm.tools.Main;

public class JndiContextFactory {

	private JndiContextFactory() {

	}

	public static JndiContext createJndiContext(com.enioka.jqm.api.DatabaseProp db) throws Exception {

		com.enioka.jqm.jpamodel.DatabaseProp tmp = Main.em
		        .createQuery("SELECT d FROM DatabaseProp d WHERE d.url = :url AND d.user = :user", com.enioka.jqm.jpamodel.DatabaseProp.class)
		        .setParameter("url", db.getUrl()).setParameter("usr", db.getUser()).getSingleResult();

		try {
			JndiContext ctx = new JndiContext(tmp);
			Class.forName(db.getDriver());
			NamingManager.setInitialContextFactoryBuilder(ctx);
			return ctx;
		} catch (Exception e) {
			throw new Exception("could not init Jndi COntext", e);
		}

	}
}