package org.oslo.common.plugin;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.io.File;

import org.oslo.common.cli.CommandLineInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 25, 2003
 * Time: 3:20:46 PM
 * To change this template use Options | File Templates.
 */
public class ClassDiscoveryServiceTest extends TestCase {
    public void testDiscoverPlugins() throws Exception {
        /*ArrayList plugins = ClassDiscoveryService.discoverPlugins("org.oslo.common.plugin.PluginImpl");
        assertNotNull(plugins);
        assertTrue(plugins.size() > 0);

        plugins = ClassDiscoveryService.discoverPlugins(CommandLineInterpreter.class.getName());
        assertNotNull(plugins);
        assertTrue(plugins.size() > 0); */
    }

    public void testGetDirectoryFiles() throws Exception {
        ArrayList plugins = ClassDiscoveryService.getDirectoryFiles("./target/classes/", new ArrayList(), new File("./target/classes"));
        assertNotNull(plugins);
        assertTrue(plugins.size() > 0);
    }
}
