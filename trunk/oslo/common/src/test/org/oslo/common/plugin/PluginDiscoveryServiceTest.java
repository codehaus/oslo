package org.oslo.common.plugin;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 25, 2003
 * Time: 3:20:46 PM
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
