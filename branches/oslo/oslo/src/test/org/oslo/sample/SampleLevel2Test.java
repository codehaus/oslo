
package org.oslo.sample;

import junit.framework.*;

import org.oslo.sample.*;

/**
 * SampleLevel2 tester.

 * <p>System-related variables are:
 * <table bgcolor=decaff><tr><td bgcolor=beaded></td><td><pre>
 * $AWT_TOOLKIT
 *   Class: java.lang.String
 *   Value: sun.awt.windows.WToolkit
 * $FILE_ENCODING
 *   Class: java.lang.String
 *   Value: Cp1252
 * $FILE_ENCODING_PKG
 *   Class: java.lang.String
 *   Value: sun.io
 * $FILE_SEPARATOR
 *   Class: java.lang.String
 *   Value: \
 * $IDEA_CONFIG_PATH
 *   Class: java.lang.String
 *   Value: ..\config
 * $IDEA_POPUP_WEIGHT
 *   Class: java.lang.String
 *   Value: heavy
 * $IDEA_SYSTEM_PATH
 *   Class: java.lang.String
 *   Value: ..\system
 * $JAVA_AWT_GRAPHICSENV
 *   Class: java.lang.String
 *   Value: sun.awt.Win32GraphicsEnvironment
 * $JAVA_AWT_PRINTERJOB
 *   Class: java.lang.String
 *   Value: sun.awt.windows.WPrinterJob
 * $JAVA_CLASS_PATH
 *   Class: java.lang.String
 *   Value: C:\IntelliJ-IDEA-3.0\lib\idea.jar;C:\IntelliJ-IDEA-3.0\lib\jdom.jar;C:\IntelliJ-IDEA-3.0\lib\log4j.jar;C:\IntelliJ-IDEA-3.0\bin\lax.jar;c:\intellij-idea-3.0\jre\lib\charsets.jar;c:\intellij-idea-3.0\jre\lib\jaws.jar;c:\intellij-idea-3.0\jre\lib\jce.jar;c:\intellij-idea-3.0\jre\lib\jsse.jar;c:\intellij-idea-3.0\jre\lib\rt.jar;c:\intellij-idea-3.0\jre\lib\sunrsasign.jar;c:\intellij-idea-3.0\jre\lib\tools.jar;
 * $JAVA_CLASS_VERSION
 *   Class: java.lang.String
 *   Value: 48.0
 * $JAVA_ENDORSED_DIRS
 *   Class: java.lang.String
 *   Value: c:\intellij-idea-3.0\jre\lib\endorsed
 * $JAVA_EXT_DIRS
 *   Class: java.lang.String
 *   Value: c:\intellij-idea-3.0\jre\lib\ext
 * $JAVA_HOME
 *   Class: java.lang.String
 *   Value: c:\intellij-idea-3.0\jre
 * $JAVA_IO_TMPDIR
 *   Class: java.lang.String
 *   Value: C:\DOCUME~1\ac08683\LOKALE~1\Temp\
 * $JAVA_LIBRARY_PATH
 *   Class: java.lang.String
 *   Value: C:\IntelliJ-IDEA-3.0\bin;.;C:\WINNT\System32;C:\WINNT;C:\WINNT\system32;C:\WINNT;C:\WINNT\System32\Wbem;C:\Java\jdk\jdk1.3.1_04\bin;C:\Java\apis\jakarta-ant\bin;D:\Nordea\DeploymentFramework\maven-1.0-beta-8\bin;C:\Program Files\Rational\ClearCase\bin;Z:\Common\OpenSource\jakarta-ant\bin;S:\DeploymentFramework\ssh\putty;S:\DeploymentFramework\maven-b9-SNAPSHOT-20030325\bin;C:\python22;S:\Common\Nordea\Infrastructure\DeploymentFramework\python\winpython2_2\;C:\PROGRA~1\CCCHAR~1\BIN;C:\PROGRA~1\CVSFOR~1;P:\Notes
 * $JAVA_RUNTIME_NAME
 *   Class: java.lang.String
 *   Value: Java(TM) 2 Runtime Environment, Standard Edition
 * $JAVA_RUNTIME_VERSION
 *   Class: java.lang.String
 *   Value: 1.4.1_01-b01
 * $JAVA_SPECIFICATION_NAME
 *   Class: java.lang.String
 *   Value: Java Platform API Specification
 * $JAVA_SPECIFICATION_VENDOR
 *   Class: java.lang.String
 *   Value: Sun Microsystems Inc.
 * $JAVA_SPECIFICATION_VERSION
 *   Class: java.lang.String
 *   Value: 1.4
 * $JAVA_UTIL_PREFS_PREFERENCESFACTORY
 *   Class: java.lang.String
 *   Value: java.util.prefs.WindowsPreferencesFactory
 * $JAVA_VENDOR
 *   Class: java.lang.String
 *   Value: Sun Microsystems Inc.
 * $JAVA_VENDOR_URL
 *   Class: java.lang.String
 *   Value: http://java.sun.com/
 * $JAVA_VENDOR_URL_BUG
 *   Class: java.lang.String
 *   Value: http://java.sun.com/cgi-bin/bugreport.cgi
 * $JAVA_VERSION
 *   Class: java.lang.String
 *   Value: 1.4.1_01
 * $JAVA_VM_INFO
 *   Class: java.lang.String
 *   Value: mixed mode
 * $JAVA_VM_NAME
 *   Class: java.lang.String
 *   Value: Java HotSpot(TM) Client VM
 * $JAVA_VM_SPECIFICATION_NAME
 *   Class: java.lang.String
 *   Value: Java Virtual Machine Specification
 * $JAVA_VM_SPECIFICATION_VENDOR
 *   Class: java.lang.String
 *   Value: Sun Microsystems Inc.
 * $JAVA_VM_SPECIFICATION_VERSION
 *   Class: java.lang.String
 *   Value: 1.0
 * $JAVA_VM_VENDOR
 *   Class: java.lang.String
 *   Value: Sun Microsystems Inc.
 * $JAVA_VM_VERSION
 *   Class: java.lang.String
 *   Value: 1.4.1_01-b01
 * $LAX_APPLICATION_NAME
 *   Class: java.lang.String
 *   Value: idea.exe
 * $LAX_CLASS_PATH
 *   Class: java.lang.String
 *   Value: ../lib/idea.jar;../lib/jdom.jar;../lib/log4j.jar;lax.jar
 * $LAX_COMMAND_LINE_ARGS
 *   Class: java.lang.String
 *   Value: $CMD_LINE_ARGUMENTS$
 * $LAX_DIR
 *   Class: java.lang.String
 *   Value: C:\IntelliJ-IDEA-3.0\bin\
 * $LAX_GENERATED_LAUNCHER_NAME
 *   Class: java.lang.String
 *   Value: C:\IntelliJ-IDEA-3.0\bin\idea.exe
 * $LAX_MAIN_CLASS
 *   Class: java.lang.String
 *   Value: com.intellij.idea.Main
 * $LAX_MAIN_METHOD
 *   Class: java.lang.String
 *   Value: main
 * $LAX_NL_CURRENT_VM
 *   Class: java.lang.String
 *   Value: ..\jre\bin\java.exe
 * $LAX_NL_ENV_ALLUSERSPROFILE
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\All Users
 * $LAX_NL_ENV_ANT_HOME
 *   Class: java.lang.String
 *   Value: C:\Java\apis\jakarta-ant
 * $LAX_NL_ENV_APPDATA
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\ac08683\Programdata
 * $LAX_NL_ENV_COMMONPROGRAMFILES
 *   Class: java.lang.String
 *   Value: C:\Program Files\Common Files
 * $LAX_NL_ENV_COMPUTERNAME
 *   Class: java.lang.String
 *   Value: NORDEA-KID5S4B3
 * $LAX_NL_ENV_COMSPEC
 *   Class: java.lang.String
 *   Value: C:\WINNT\system32\cmd.exe
 * $LAX_NL_ENV_EXACT_CASE_ALLUSERSPROFILE
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\All Users
 * $LAX_NL_ENV_EXACT_CASE_ANT_HOME
 *   Class: java.lang.String
 *   Value: C:\Java\apis\jakarta-ant
 * $LAX_NL_ENV_EXACT_CASE_APPDATA
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\ac08683\Programdata
 * $LAX_NL_ENV_EXACT_CASE_COMMONPROGRAMFILES
 *   Class: java.lang.String
 *   Value: C:\Program Files\Common Files
 * $LAX_NL_ENV_EXACT_CASE_COMPUTERNAME
 *   Class: java.lang.String
 *   Value: NORDEA-KID5S4B3
 * $LAX_NL_ENV_EXACT_CASE_COMSPEC
 *   Class: java.lang.String
 *   Value: C:\WINNT\system32\cmd.exe
 * $LAX_NL_ENV_EXACT_CASE_GALAXYHOME
 *   Class: java.lang.String
 *   Value: C:\PROGRA~1\CCCHAR~1
 * $LAX_NL_ENV_EXACT_CASE_HARREPHOME
 *   Class: java.lang.String
 *   Value: C:\TEMP
 * $LAX_NL_ENV_EXACT_CASE_HARVESTHOME
 *   Class: java.lang.String
 *   Value: C:\PROGRA~1\CCCHAR~1
 * $LAX_NL_ENV_EXACT_CASE_HOME
 *   Class: java.lang.String
 *   Value: C:\Programfiler\CCCHARVEST
 * $LAX_NL_ENV_EXACT_CASE_HOMEDRIVE
 *   Class: java.lang.String
 *   Value: P:
 * $LAX_NL_ENV_EXACT_CASE_HOMEPATH
 *   Class: java.lang.String
 *   Value: \
 * $LAX_NL_ENV_EXACT_CASE_HOMESHARE
 *   Class: java.lang.String
 *   Value: \\Mi17fil02\ac08683
 * $LAX_NL_ENV_EXACT_CASE_JAVA_HOME
 *   Class: java.lang.String
 *   Value: C:\Java\jdk\jdk1.3.1_04
 * $LAX_NL_ENV_EXACT_CASE_LOGONSERVER
 *   Class: java.lang.String
 *   Value: \\NOD1DC0001
 * $LAX_NL_ENV_EXACT_CASE_MAVEN_HOME
 *   Class: java.lang.String
 *   Value: S:\DeploymentFramework\maven-b9-SNAPSHOT-20030325
 * $LAX_NL_ENV_EXACT_CASE_MAVEN_LOCAL_HOME
 *   Class: java.lang.String
 *   Value: D:\maven_local
 * $LAX_NL_ENV_EXACT_CASE_NUMBER_OF_PROCESSORS
 *   Class: java.lang.String
 *   Value: 1
 * $LAX_NL_ENV_EXACT_CASE_OS
 *   Class: java.lang.String
 *   Value: Windows_NT
 * $LAX_NL_ENV_EXACT_CASE_OS2LIBPATH
 *   Class: java.lang.String
 *   Value: C:\WINNT\system32\os2\dll;
 * $LAX_NL_ENV_EXACT_CASE_PATH
 *   Class: java.lang.String
 *   Value: C:\WINNT\system32;C:\WINNT;C:\WINNT\System32\Wbem;C:\Java\jdk\jdk1.3.1_04\bin;C:\Java\apis\jakarta-ant\bin;D:\Nordea\DeploymentFramework\maven-1.0-beta-8\bin;C:\Program Files\Rational\ClearCase\bin;Z:\Common\OpenSource\jakarta-ant\bin;S:\DeploymentFramework\ssh\putty;S:\DeploymentFramework\maven-b9-SNAPSHOT-20030325\bin;C:\python22;S:\Common\Nordea\Infrastructure\DeploymentFramework\python\winpython2_2\;C:\PROGRA~1\CCCHAR~1\BIN;C:\PROGRA~1\CVSFOR~1;P:\Notes
 * $LAX_NL_ENV_EXACT_CASE_PATHEXT
 *   Class: java.lang.String
 *   Value: .COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH
 * $LAX_NL_ENV_EXACT_CASE_PROCESSOR_ARCHITECTURE
 *   Class: java.lang.String
 *   Value: x86
 * $LAX_NL_ENV_EXACT_CASE_PROCESSOR_IDENTIFIER
 *   Class: java.lang.String
 *   Value: x86 Family 6 Model 8 Stepping 6, GenuineIntel
 * $LAX_NL_ENV_EXACT_CASE_PROCESSOR_LEVEL
 *   Class: java.lang.String
 *   Value: 6
 * $LAX_NL_ENV_EXACT_CASE_PROCESSOR_REVISION
 *   Class: java.lang.String
 *   Value: 0806
 * $LAX_NL_ENV_EXACT_CASE_PROGRAMFILES
 *   Class: java.lang.String
 *   Value: C:\Program Files
 * $LAX_NL_ENV_EXACT_CASE_SYSTEMDRIVE
 *   Class: java.lang.String
 *   Value: C:
 * $LAX_NL_ENV_EXACT_CASE_SYSTEMROOT
 *   Class: java.lang.String
 *   Value: C:\WINNT
 * $LAX_NL_ENV_EXACT_CASE_TEMP
 *   Class: java.lang.String
 *   Value: C:\DOCUME~1\ac08683\LOKALE~1\Temp
 * $LAX_NL_ENV_EXACT_CASE_TMP
 *   Class: java.lang.String
 *   Value: C:\DOCUME~1\ac08683\LOKALE~1\Temp
 * $LAX_NL_ENV_EXACT_CASE_USERDNSDOMAIN
 *   Class: java.lang.String
 *   Value: nod1.root4.net
 * $LAX_NL_ENV_EXACT_CASE_USERDOMAIN
 *   Class: java.lang.String
 *   Value: NOD1
 * $LAX_NL_ENV_EXACT_CASE_USERNAME
 *   Class: java.lang.String
 *   Value: ac08683
 * $LAX_NL_ENV_EXACT_CASE_USERPROFILE
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\ac08683
 * $LAX_NL_ENV_EXACT_CASE_WINDIR
 *   Class: java.lang.String
 *   Value: C:\WINNT
 * $LAX_NL_ENV_GALAXYHOME
 *   Class: java.lang.String
 *   Value: C:\PROGRA~1\CCCHAR~1
 * $LAX_NL_ENV_HARREPHOME
 *   Class: java.lang.String
 *   Value: C:\TEMP
 * $LAX_NL_ENV_HARVESTHOME
 *   Class: java.lang.String
 *   Value: C:\PROGRA~1\CCCHAR~1
 * $LAX_NL_ENV_HOME
 *   Class: java.lang.String
 *   Value: C:\Programfiler\CCCHARVEST
 * $LAX_NL_ENV_HOMEDRIVE
 *   Class: java.lang.String
 *   Value: P:
 * $LAX_NL_ENV_HOMEPATH
 *   Class: java.lang.String
 *   Value: \
 * $LAX_NL_ENV_HOMESHARE
 *   Class: java.lang.String
 *   Value: \\Mi17fil02\ac08683
 * $LAX_NL_ENV_JAVA_HOME
 *   Class: java.lang.String
 *   Value: C:\Java\jdk\jdk1.3.1_04
 * $LAX_NL_ENV_LOGONSERVER
 *   Class: java.lang.String
 *   Value: \\NOD1DC0001
 * $LAX_NL_ENV_MAVEN_HOME
 *   Class: java.lang.String
 *   Value: S:\DeploymentFramework\maven-b9-SNAPSHOT-20030325
 * $LAX_NL_ENV_MAVEN_LOCAL_HOME
 *   Class: java.lang.String
 *   Value: D:\maven_local
 * $LAX_NL_ENV_NUMBER_OF_PROCESSORS
 *   Class: java.lang.String
 *   Value: 1
 * $LAX_NL_ENV_OS
 *   Class: java.lang.String
 *   Value: Windows_NT
 * $LAX_NL_ENV_OS2LIBPATH
 *   Class: java.lang.String
 *   Value: C:\WINNT\system32\os2\dll;
 * $LAX_NL_ENV_PATH
 *   Class: java.lang.String
 *   Value: C:\WINNT\system32;C:\WINNT;C:\WINNT\System32\Wbem;C:\Java\jdk\jdk1.3.1_04\bin;C:\Java\apis\jakarta-ant\bin;D:\Nordea\DeploymentFramework\maven-1.0-beta-8\bin;C:\Program Files\Rational\ClearCase\bin;Z:\Common\OpenSource\jakarta-ant\bin;S:\DeploymentFramework\ssh\putty;S:\DeploymentFramework\maven-b9-SNAPSHOT-20030325\bin;C:\python22;S:\Common\Nordea\Infrastructure\DeploymentFramework\python\winpython2_2\;C:\PROGRA~1\CCCHAR~1\BIN;C:\PROGRA~1\CVSFOR~1;P:\Notes
 * $LAX_NL_ENV_PATHEXT
 *   Class: java.lang.String
 *   Value: .COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH
 * $LAX_NL_ENV_PROCESSOR_ARCHITECTURE
 *   Class: java.lang.String
 *   Value: x86
 * $LAX_NL_ENV_PROCESSOR_IDENTIFIER
 *   Class: java.lang.String
 *   Value: x86 Family 6 Model 8 Stepping 6, GenuineIntel
 * $LAX_NL_ENV_PROCESSOR_LEVEL
 *   Class: java.lang.String
 *   Value: 6
 * $LAX_NL_ENV_PROCESSOR_REVISION
 *   Class: java.lang.String
 *   Value: 0806
 * $LAX_NL_ENV_PROGRAMFILES
 *   Class: java.lang.String
 *   Value: C:\Program Files
 * $LAX_NL_ENV_SYSTEMDRIVE
 *   Class: java.lang.String
 *   Value: C:
 * $LAX_NL_ENV_SYSTEMROOT
 *   Class: java.lang.String
 *   Value: C:\WINNT
 * $LAX_NL_ENV_TEMP
 *   Class: java.lang.String
 *   Value: C:\DOCUME~1\ac08683\LOKALE~1\Temp
 * $LAX_NL_ENV_TMP
 *   Class: java.lang.String
 *   Value: C:\DOCUME~1\ac08683\LOKALE~1\Temp
 * $LAX_NL_ENV_USERDNSDOMAIN
 *   Class: java.lang.String
 *   Value: nod1.root4.net
 * $LAX_NL_ENV_USERDOMAIN
 *   Class: java.lang.String
 *   Value: NOD1
 * $LAX_NL_ENV_USERNAME
 *   Class: java.lang.String
 *   Value: ac08683
 * $LAX_NL_ENV_USERPROFILE
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\ac08683
 * $LAX_NL_ENV_WINDIR
 *   Class: java.lang.String
 *   Value: C:\WINNT
 * $LAX_NL_JAVA_LAUNCHER_MAIN_CLASS
 *   Class: java.lang.String
 *   Value: com.zerog.lax.LAX
 * $LAX_NL_JAVA_LAUNCHER_MAIN_METHOD
 *   Class: java.lang.String
 *   Value: main
 * $LAX_NL_JAVA_OPTION_ADDITIONAL
 *   Class: java.lang.String
 *   Value: -Xms16m -Xmx128m -Dsun.java2d.noddraw=true -Didea.system.path="..\system" -Didea.config.path="..\config" -Didea.popup.weight=heavy
 * $LAX_NL_VALID_VM_LIST
 *   Class: java.lang.String
 *   Value: 
 * $LAX_NL_WIN32_MICROSOFTVM_MIN_VERSION
 *   Class: java.lang.String
 *   Value: 2750
 * $LAX_NL_WIN32_SERVICE
 *   Class: java.lang.String
 *   Value: false
 * $LAX_ROOT_INSTALL_DIR
 *   Class: java.lang.String
 *   Value: C:\IntelliJ-IDEA-3.0
 * $LAX_STDERR_REDIRECT
 *   Class: java.lang.String
 *   Value: 
 * $LAX_STDIN_REDIRECT
 *   Class: java.lang.String
 *   Value: 
 * $LAX_STDOUT_REDIRECT
 *   Class: java.lang.String
 *   Value: 
 * $LAX_USER_DIR
 *   Class: java.lang.String
 *   Value: .
 * $LAX_VERSION
 *   Class: java.lang.String
 *   Value: 4.5
 * $LINE_SEPARATOR
 *   Class: java.lang.String
 *   Value: 

 * $LOG4J_DEFAULTINITOVERRIDE
 *   Class: java.lang.String
 *   Value: true
 * $OS_ARCH
 *   Class: java.lang.String
 *   Value: x86
 * $OS_NAME
 *   Class: java.lang.String
 *   Value: Windows 2000
 * $OS_VERSION
 *   Class: java.lang.String
 *   Value: 5.0
 * $PATH_SEPARATOR
 *   Class: java.lang.String
 *   Value: ;
 * $SUN_ARCH_DATA_MODEL
 *   Class: java.lang.String
 *   Value: 32
 * $SUN_BOOT_CLASS_PATH
 *   Class: java.lang.String
 *   Value: c:\intellij-idea-3.0\jre\lib\rt.jar;c:\intellij-idea-3.0\jre\lib\i18n.jar;c:\intellij-idea-3.0\jre\lib\sunrsasign.jar;c:\intellij-idea-3.0\jre\lib\jsse.jar;c:\intellij-idea-3.0\jre\lib\jce.jar;c:\intellij-idea-3.0\jre\lib\charsets.jar;c:\intellij-idea-3.0\jre\classes
 * $SUN_BOOT_LIBRARY_PATH
 *   Class: java.lang.String
 *   Value: c:\intellij-idea-3.0\jre\bin
 * $SUN_CPU_ENDIAN
 *   Class: java.lang.String
 *   Value: little
 * $SUN_CPU_ISALIST
 *   Class: java.lang.String
 *   Value: pentium i486 i386
 * $SUN_IO_UNICODE_ENCODING
 *   Class: java.lang.String
 *   Value: UnicodeLittle
 * $SUN_JAVA2D_FONTPATH
 *   Class: java.lang.String
 *   Value: 
 * $SUN_JAVA2D_NODDRAW
 *   Class: java.lang.String
 *   Value: true
 * $SUN_OS_PATCH_LEVEL
 *   Class: java.lang.String
 *   Value: Service Pack 3
 * $USER_COUNTRY
 *   Class: java.lang.String
 *   Value: NO
 * $USER_DIR
 *   Class: java.lang.String
 *   Value: C:\IntelliJ-IDEA-3.0\bin
 * $USER_HOME
 *   Class: java.lang.String
 *   Value: C:\Documents and Settings\ac08683
 * $USER_LANGUAGE
 *   Class: java.lang.String
 *   Value: no
 * $USER_NAME
 *   Class: java.lang.String
 *   Value: ac08683
 * $USER_TIMEZONE
 *   Class: java.lang.String
 *   Value: Europe/Berlin
 * $USER_VARIANT
 *   Class: java.lang.String
 *   Value: 
 * </pre></td></tr></table>
 * <p>Project-related variables are:
 * <table bgcolor=deface><tr><td bgcolor=dead></td><td><pre>
 * $DATE_FORMAT
 *   Class: java.text.SimpleDateFormat
 *   Value: java.text.SimpleDateFormat@3dd497a
 * $MIRROR_AUTHOR
 *   Class: java.lang.String
 *   Value: Christian Amor Kvalheim
 * $MIRROR_EMAIL
 *   Class: java.lang.String
 *   Value: christian@inx-soft.com
 * $MIRROR_FILENAME_PREFIX
 *   Class: java.lang.String
 *   Value: 
 * $MIRROR_FILENAME_SUFFIX
 *   Class: java.lang.String
 *   Value: Test
 * $MIRROR_MASTER_EXTENSION
 *   Class: java.lang.String
 *   Value: .java
 * $MIRROR_MASTER_PREFIX
 *   Class: java.lang.String
 *   Value: Package_
 * $MIRROR_MASTER_SUFFIX
 *   Class: java.lang.String
 *   Value: _Tester
 * $MIRROR_OUTPUT_PATH
 *   Class: java.lang.String
 *   Value: C:/os/oslo/src/test
 * $MIRROR_PACKAGE_PREFIX
 *   Class: java.lang.String
 *   Value: 
 * $PROJECT
 *   Class: com.intellij.openapi.project.a.c
 *   Value: com.intellij.openapi.project.a.c@2eef15
 * $PROJECT_PATH
 *   Class: com.intellij.openapi.vfs.b.b.bg
 *   Value: VirtualFile: C:\os\oslo
 * $SORTED
 *   Class: idea.plugin.mirror.velocity.variables.Sorter
 *   Value: idea.plugin.mirror.velocity.variables.Sorter@318293
 * $SYSTEM_CONTEXT
 *   Class: org.apache.velocity.VelocityContext
 *   Value: org.apache.velocity.VelocityContext@26dfcc
 * $TEXT
 *   Class: idea.plugin.mirror.velocity.variables.RexxString
 *   Value: idea.plugin.mirror.velocity.variables.RexxString@739aa3
 * $yyyymmdd
 *   Class: java.text.SimpleDateFormat
 *   Value: java.text.SimpleDateFormat@129110e0
 * </pre></td></tr></table>
 * <p>File-related variables are:
 * <table bgcolor=efface><tr><td bgcolor=faded></td><td><pre>
 * $CLASSNAME
 *   Class: java.lang.String
 *   Value: SampleLevel2
 * $DATE
 *   Class: java.util.Date
 *   Value: Wed Apr 30 14:47:08 CEST 2003
 * $FILE_CONTEXT
 *   Class: org.apache.velocity.VelocityContext
 *   Value: org.apache.velocity.VelocityContext@6b26a8
 * $MIRROR_CLASSNAME
 *   Class: java.lang.String
 *   Value: SampleLevel2Test
 * $MIRROR_FILE
 *   Class: com.intellij.openapi.vfs.b.b.bg
 *   Value: VirtualFile: C:\os\oslo\src\test\org\oslo\sample\SampleLevel2Test.java
 * $MIRROR_FILES
 *   Class: java.util.ArrayList
 *   Value: [VirtualFile: C:\os\oslo\src\test\org\oslo\sample\Package_sample_Tester.java, VirtualFile: C:\os\oslo\src\test\org\oslo\sample\SampleLevel1Test.java, VirtualFile: C:\os\oslo\src\test\org\oslo\sample\SampleLevel2Test.java, VirtualFile: C:\os\oslo\src\test\org\oslo\sample\SampleMainTest.java]
 * $OPTIONAL_DOT
 *   Class: java.lang.String
 *   Value: .
 * $PACKAGE_NAME
 *   Class: java.lang.String
 *   Value: org.oslo.sample
 * $PROJECT_CONTEXT
 *   Class: org.apache.velocity.VelocityContext
 *   Value: org.apache.velocity.VelocityContext@1d0119e
 * $SOURCE_FILE
 *   Class: com.intellij.openapi.vfs.b.b.bg
 *   Value: VirtualFile: C:\os\oslo\src\java\org\oslo\sample\SampleLevel2.java
 * $SOURCE_FILES
 *   Class: java.util.ArrayList
 *   Value: [VirtualFile: C:\os\oslo\src\java\org\oslo\sample\SampleLevel1.java, VirtualFile: C:\os\oslo\src\java\org\oslo\sample\SampleLevel2.java, VirtualFile: C:\os\oslo\src\java\org\oslo\sample\SampleMain.java]
 * $TEMPLATE_NAME
 *   Class: java.lang.String
 *   Value: mirror_java.vm
 * </pre></td></tr></table>
 * <p>Source files:
 * <br>Ordered list of siblings of C:/os/oslo/src/java/org/oslo/sample/SampleLevel2.java:
 * <table bgcolor=decaff><tr><td bgcolor=feed></td><td><pre>
 *  SampleLevel1.java
 *  SampleLevel2.java
 *  SampleMain.java
 * </pre></td></tr></table>
 * <p>Mirror files:
 * <br>Ordered list of siblings of C:/os/oslo/src/test/org/oslo/sample/SampleLevel2Test.java:
 * <table bgcolor=decaff><tr><td bgcolor=feed></td><td><pre>
 *  Package_sample_Tester.java
 *  SampleLevel1Test.java
 *  SampleLevel2Test.java
 *  SampleMainTest.java
 * </pre></td></tr></table>
 * <p>Methods in source file: VirtualFile: C:\os\oslo\src\java\org\oslo\sample\SampleLevel2.java
 * <table bgcolor=decaff><tr bgcolor=feed><td/><td>Returns</td><td>Method</td><td>Signature</td>
 * </table>
 *
 * @author <a href="mailto:christian@inx-soft.com">Christian Amor Kvalheim</a>
 * <br>Generated using: mirror_java.vm
 * <br>Date: Wed Apr 30 14:47:08 CEST 2003
 * <br>By: ac08683
 */
public class SampleLevel2Test extends TestCase
{

    private org.oslo.sample.SampleLevel2 _objectUnderTest;

    /**
     * Code executed before EACH test.
     */
//  public void setUp()
//  {
//  }

    /**
     * Code executed after EACH test.
     */
//  public void tearDown()
//  {
//  }




}
