package org.oslo.server;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: Jul 21, 2003
 * Time: 3:18:42 PM
 * To change this template use Options | File Templates.
 */
public class PluginDiscoveryService {

    /**
     * Finds and returns a list of all Plugins available in the classpath
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList discoverPlugins() throws IOException, ClassNotFoundException {
        // Find java home, adjust for MAC OSX
        /*String javaHome = System.getProperty("java.home");

        if (System.getProperty("os.name").toUpperCase().equals("MAC OS X")) {
            javaHome = javaHome.substring(0, javaHome.lastIndexOf("/"));
        }

        // Classloader
        ClassLoader classLoader = Class.class.getClassLoader();

        // Results
        ArrayList plugins = new ArrayList();

        // Get classpath
        String classpath = System.getProperty("java.class.path");

        System.out.println(classpath);

        // Split the path into its components
        String delimeter = File.pathSeparator;
        StringTokenizer tokenizer = new StringTokenizer(classpath, delimeter);

        while (tokenizer.hasMoreTokens()) {
            String classpathElement = tokenizer.nextToken();

            // Ok, do not include files in the java.home cataloge
            if (classpathElement.indexOf(javaHome) == -1) {

                // Ok check if this is a jar or a directory
                File currentElement = new File(classpathElement);

                if (currentElement.isFile()) {
                    String fileEnd = classpathElement.substring(classpathElement.lastIndexOf(".")).toUpperCase();

                    // Check if its a jar or zip
                    if (".JAR".equals(fileEnd) || ".ZIP".equals(fileEnd)) {
                        // Ok we got a JAR or ZIP file, read it in and walk through the file entries
                        JarInputStream jarInputStream = new JarInputStream(new FileInputStream(currentElement));

                        while (jarInputStream.available() == 1) {
                            JarEntry jarEntry = jarInputStream.getNextJarEntry();

                            if (jarEntry != null && !jarEntry.isDirectory()) {
                                // Its a file entry, check that it is a .class file
                                String elementName = jarEntry.getName();
                                plugins = validateEntry(elementName, plugins);
                            }
                        }
                    }
                } else {
                    // Ok we have a directory structure, we need to parse this and find all the Java entries
                    ArrayList directoryFiles = getDirectoryFiles(currentElement.getPath() + "/", new ArrayList(), currentElement);

                    for (Iterator iterator = directoryFiles.iterator(); iterator.hasNext();) {
                        String fileString = (String) iterator.next();
                        plugins = validateEntry(fileString, plugins);
                    }
                }
                System.out.println(classpathElement);
            }
        }  */
        ArrayList plugins = new ArrayList();
        plugins.add("org.oslo.plugins.performance.PerformancePlugin");
        plugins.add("org.oslo.plugins.sequence.SequencePlugin");

        return plugins;
    }

    /**
     * Figures out if the class uses the interface "org.oslo.server.plugin.Plugin" and adds it to the list
     * @param elementName
     * @param plugins
     * @return
     * @throws ClassNotFoundException
     */
    private static ArrayList validateEntry(String elementName, ArrayList plugins) throws ClassNotFoundException {
        // Replace seperator, so that it is correct according to the classloading standard
        /*elementName = elementName.replace(File.separatorChar, '.');

        // Check if this is in fact a class
        if (elementName.lastIndexOf(".class") != -1 && (elementName.indexOf("sun.") != 0 && elementName.indexOf("java.") != 0) && elementName.indexOf("javax.") != 0) {
            try {
                Class elementClass = PluginDiscoveryService.class.getClassLoader().loadClass(elementName.substring(0, elementName.lastIndexOf(".class")));

                // Get interfaces
                Class[] interfaces = elementClass.getInterfaces();

                // Traverse interfaces and check if it contains plugins
                for (int i = 0; i < interfaces.length; i++) {
                    Class anInterface = interfaces[i];

                    if ("org.oslo.server.plugin.Plugin".equals(anInterface.getName())) {
                        plugins.add(elementClass.getName());
                    }
                }
            } catch (Throwable e) {
                //e.printStackTrace();
                // Ignore faults...
                //e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        } */

        return plugins;
    }

    /**
     * Parses a directory in the classpath containing classes, etc. Adds them to a list as strings expressing
     * the relative path to the object
     * @param root
     * @param files
     * @param file
     * @return
     * @throws IOException
     */
    /*public static ArrayList getDirectoryFiles(String root, ArrayList files, File file) throws IOException {

        if (file.isDirectory()) {
            // Traverse the directory
            File[] dirFiles = file.listFiles();

            for (int i = 0; i < dirFiles.length; i++) {
                File newFile = dirFiles[i];
                files = getDirectoryFiles(root, files, newFile);
            }
        } else {
            // Remove the root info
            String fileName = file.getAbsolutePath();
            fileName = fileName.substring(root.length());

            // Add the file to the files ArrayList
            files.add(fileName.replace(File.separatorChar, '.'));
        }

        return files;
    } */
}
