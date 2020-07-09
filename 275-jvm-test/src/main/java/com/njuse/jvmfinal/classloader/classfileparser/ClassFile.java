package com.njuse.jvmfinal.classloader.classfileparser;

import com.njuse.jvmfinal.classloader.classfileparser.attribute.AttributeBuilder;
import com.njuse.jvmfinal.classloader.classfileparser.attribute.AttributeInfo;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.ClassInfo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

@Getter
@Setter
public class ClassFile {                // 一个此类的实例即表示一个Class字节码文件，一般代表一个类（或接口）
    private int magic;                  // 魔数
    private short minorVersion;         // 次版本号
    private short majorVersion;         // 主版本号
    private short constantPoolCount;    // 常量池容量计数值
    private ConstantPool constantPool;  // 常量池，其中每一个常量都是一个表
    private short accessFlags;          // 访问标志
    private short thisClass;            // 类索引，用于确定这个类和全限定名
    private short superClass;           // 父类索引，用于确定这个类的父类的全限定名
    private short interfacesCount;      // 这个类实现接口的数量
    private short[] interfaces;         // 接口索引集合，描述这个类实现了哪些接口
    private short fieldsCount;          // 这个类字段（成员变量，包括实例变量和静态变量）的数量
    private FieldInfo[] fields;         // 字段表集合，描述这个类声明的成员变量
    private short methodsCount;         // 这个类中方法的数量
    private MethodInfo[] methods;       // 方法表集合，描述这个类有哪些和什么样的方法
    private short attributeCount;       // 属性表的数量，Class文件、字段表、方法表都可以携带自己的属性表集合
    private AttributeInfo[] attributes; // 属性表集合，描述某些场景专有的信息

    private ByteBuffer in;              // 一个字节缓冲区，主要用于读取字节等类型数据，即Class文件
    Supplier<AttributeInfo> attrBuilder = this::getAttribute;

    /*
        构造方法，参数为byte类型数组
        利用in逐次获取byte[] classfile中的字节并赋值给各实例变量
        这意味着只要创建了ClassFile对象的实例，它所表示的类已经解析完成了（主要指字段表和方法表产生好了，不是类加载的“解析”）
     */
    public ClassFile(byte[] classfile) {
        in = ByteBuffer.wrap(classfile);
        this.magic = in.getInt();
        if (this.magic != 0xCAFEBABE) {
            throw new UnsupportedOperationException(
                    "Wrong magic number! Expect 0xCAFEBABE but actual is " + Integer.toHexString(this.magic));
        }
        this.minorVersion = in.getShort();
        this.majorVersion = in.getShort();
        parseConstantPool(classfile);
        this.accessFlags = in.getShort();
        this.thisClass = in.getShort();
        this.superClass = in.getShort();
        parseInterfaces();
        parseFields();
        parseMethods();
        parseAttributes();

    }

    /*
        解析属性，产生属性表集合
     */
    private void parseAttributes() {
        this.attributeCount = in.getShort();
        this.attributes = new AttributeInfo[0xFFFF & this.attributeCount];
        for (int i = 0; i < attributes.length; i++) {
            this.attributes[i] = attrBuilder.get();
        }
    }

    /*
        解析方法，产生方法表集合
     */
    private void parseMethods() {
        this.methodsCount = in.getShort();
        this.methods = new MethodInfo[0xFFFF & this.methodsCount];
        for (int i = 0; i < this.methods.length; i++) {
            this.methods[i] = new MethodInfo(this.constantPool, this.attrBuilder, in);
        }
    }

    /*
        解析字段，产生字段表集合
     */
    private void parseFields() {
        this.fieldsCount = in.getShort();
        this.fields = new FieldInfo[0xFFFF & this.fieldsCount];
        for (int i = 0; i < this.fields.length; i++) {
            this.fields[i] = new FieldInfo(this.constantPool, this.attrBuilder, in);
        }
    }

    /*
        解析接口
     */
    private void parseInterfaces() {
        this.interfacesCount = in.getShort();
        interfaces = new short[0xFFFF & this.interfacesCount];
        for (int i = 0; i < this.interfaces.length; i++) {
            this.interfaces[i] = in.getShort();
        }
    }

    /*
        解析常量池，完成了：
            1. 对常量池容量计数值的读取
            2. 通过键值对的方式读取了常量池
     */
    private void parseConstantPool(byte[] classfile) {
        this.constantPoolCount = in.getShort();
        int currentPos = in.position();         // 获得此缓冲区的位置
        Pair<ConstantPool, Integer> cpInt = ConstantPool.getInstance(constantPoolCount, classfile, currentPos);
        constantPool = cpInt.getKey();
        currentPos += cpInt.getValue();
        in.position(currentPos);                // 重新获取缓冲区的位置
    }

    public AttributeInfo getAttribute() {
        return AttributeBuilder.createAttribute(new BuildUtil(this.constantPool, in));
    }

    /*
        获得该ClassFile对象对应的类的类名
     */
    public String getClassName() {
        return ((ClassInfo) constantPool.get(thisClass)).getClassName();
    }

    /*
        获得该ClassFile对象对应的类的父类的类名
     */
    public String getSuperClassName() {
        return ((ClassInfo) constantPool.get(superClass)).getClassName();
    }

    /*
        获得该ClassFile对象对应的类实现的所有接口的名字
     */
    public String[] getInterfaceNames() {
        String[] ret = new String[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            ret[i] = ((ClassInfo) constantPool.get(interfaces[i])).getClassName();
        }
        return ret;
    }
}
