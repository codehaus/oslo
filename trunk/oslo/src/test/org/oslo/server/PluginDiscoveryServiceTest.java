package org.oslo.server;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 21, 2003
 * Time: 3:21:07 PM
 * To change this template use Options | File Templates.
 */
public class PluginDiscoveryServiceTest extends TestCase {
        public void testDiscoverPlugins() throws Exception {
        ArrayList plugins = PluginDiscoveryService.discoverPlugins();
        assertNotNull(plugins);
        assertTrue(plugins.size() > 0);
    }

    public void testGetDirectoryFiles() throws Exception {
        ArrayList plugins = PluginDiscoveryService.getDirectoryFiles("./target/classes/", new ArrayList(), new File("./target/classes"));
        assertNotNull(plugins);
        assertTrue(plugins.size() > 0);
    }
}
