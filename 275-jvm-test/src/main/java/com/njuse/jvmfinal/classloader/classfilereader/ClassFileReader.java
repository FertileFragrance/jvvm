package com.njuse.jvmfinal.classloader.classfilereader;

import com.njuse.jvmfinal.classloader.classfilereader.classpath.*;
import com.njuse.jvmfinal.util.IOUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

/**
 * 此类用于读取class文件，以字节数组的形式返回
 * 类中方法的执行顺序是先设置类路径，然后由ClassLoader调用readClassFile方法
 */
public class ClassFileReader {

    /**
     * 构造方法私有，不允许创建该类的实例
     * 该类当做工具类来用
     */
    private ClassFileReader() {}

    private static Entry bootClassPath = null;  /**启动类路径*/
    private static Entry extClassPath = null;   /**扩展类路径*/
    private static Entry userClassPath = null;  /**用户类路径*/

    /**
     * 这三个方法是用来设置加载器读取方式的，不用再设置一遍，chooseEntryType也只会在这三个方法里被调用
     * 调用这些方法的任意一个意味着给某个类路径赋予搜索权限（就是赋值，让它不为空）
     * 启动类路径和扩展类路径是固定的，随着环境变量而确定，只有用户类路径与输入的classPath有关
     * 但是为了简单起见，用户类路径是唯一确定的，也只需要设置一次
     * @param className class文件的文件名
     * @param classPath 该文件的相对路径
     */
    public static void setBootClassPath(String className, String classPath) {
        String JAVA_HOME = System.getenv("JAVA_HOME");
        bootClassPath = chooseEntryType(className,
                IOUtil.transform(String.join(File.separator, JAVA_HOME, "jre", "lib", "*")));
    }

    public static void setExtClassPath(String className, String classPath) {
        String JAVA_HOME = System.getenv("JAVA_HOME");
        extClassPath = chooseEntryType(className,
                IOUtil.transform(String.join(File.separator, JAVA_HOME, "jre", "lib", "ext", "*")));
    }

    public static void setUserClassPath(String className, String classPath) {
        userClassPath = chooseEntryType(className, classPath);
    }

    public static Entry getBootClassPath() {
        return bootClassPath;
    }

    public static Entry getExtClassPath() {
        return extClassPath;
    }

    public static Entry getUserClassPath() {
        return userClassPath;
    }

    /**
     * 此方法用于选择读取class文件命令的格式类型
     * @param className class文件的文件名
     * @param classPath 该文件的相对路径
     * @return 某个Entry类型，该返回值就是三种类路径的一种
     */
    public static Entry chooseEntryType(String className, String classPath) {
        if (classPath.contains(File.pathSeparator)) {
            return new CompositeEntry(className, classPath);
        }
        if (classPath.contains("*")) {
            return new WildEntry(className, classPath);
        }
        if (classPath.endsWith("zip") || classPath.endsWith("ZIP") ||
                classPath.endsWith("jar") || classPath.endsWith("JAR")) {
            return new ArchivedEntry(className, classPath);
        }
        return new DirEntry(className, classPath);
    }

    /**
     * 此方法为真正地读取class文件的入口，即将class文件转换为byte[]
     * 此方法并非只适用于含有main方法的程序入口，所有class文件都需要经过此方法读取
     * @param className class文件的文件名
     * @param privilege 表示权限，值越大权限越高
     * @return class文件的字节数组形式和类路径的类型
     */
    public static Pair<byte[], Integer> readClassFile(String className, EntryType privilege) {
        int value;
        value = privilege == null ? EntryType.USER_ENTRY : privilege.getValue();
        String realClassName = IOUtil.transform(className + ".class");
        byte[] bytes;
        if (bootClassPath.readClassFile(realClassName,
                bootClassPath.getClassPath()) != null && value >= EntryType.BOOT_ENTRY) {
            bytes = bootClassPath.readClassFile(realClassName, bootClassPath.getClassPath());
            return Pair.of(bytes, EntryType.BOOT_ENTRY);
        } else if (extClassPath.readClassFile(realClassName,
                extClassPath.getClassPath()) != null && value >= EntryType.EXT_ENTRY) {
            bytes = extClassPath.readClassFile(realClassName, extClassPath.getClassPath());
            return Pair.of(bytes, EntryType.EXT_ENTRY);
        } else if (userClassPath.readClassFile(realClassName,
                userClassPath.getClassPath()) != null && value >= EntryType.USER_ENTRY) {
            bytes = userClassPath.readClassFile(realClassName, userClassPath.getClassPath());
            return Pair.of(bytes, EntryType.USER_ENTRY);
        }
        return null;
    }

}
