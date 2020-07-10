package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import lombok.Getter;

@Getter
public abstract class SymRef implements Constant {      // 所有的引用都是从运行时常量池中得到的

    protected RuntimeConstantPool runtimeConstantPool;  // 该引用所在的运行时常量池，修饰符改为protected，下同
    protected String className;                         // 引用所在类的类名，format : java/lang/Object
    protected JClass classToResolve;                    // 引用所指的类，对于ClassRef来说就是解析完成的类，暂时没有赋值

    /**
     * 此方法用于解析类或接口，并将解析结果赋值给成员变量clazz，执行指令时会调用
     * caller是当前代码所处的类，callee是从未解析过（也是没有加载过）的要去解析的类
     */
    public JClass resolveClassRef() {
        if (classToResolve != null) {
            return classToResolve;
        }
        JClass caller = runtimeConstantPool.getClazz();
        JClass callee = ClassLoader.getInstance().loadClass(className, null, caller.getLoadEntryType());
        if (callee.isAccessibleTo(caller)) {
            classToResolve = callee;
        }
        return classToResolve;
    }

}
