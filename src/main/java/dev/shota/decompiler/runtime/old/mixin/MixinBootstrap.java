package dev.shota.decompiler.runtime.old.mixin;

import dev.shota.decompiler.mixins.IMixinFernflower;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import lombok.SneakyThrows;
import org.jetbrains.java.decompiler.main.AssertProcessor;

public class MixinBootstrap {

    @SneakyThrows
    public static void init() {
        ClassPool classPool = ClassPool.getDefault();
        CtClass clazz = classPool.get(IMixinFernflower.class.getCanonicalName());
        CtClass target = classPool.get(((Mixin) clazz.getAnnotation(Mixin.class)).value());
        target.addInterface(clazz);

        for (CtMethod source : clazz.getDeclaredMethods()) {
            CtField field = target.getDeclaredField(((Field) source.getAnnotation(Field.class)).value());
            CtMethod method = new CtMethod(source.getReturnType(), source.getName(), new CtClass[]{}, target);
            method.setBody(String.format("return %s;", field.getName()));
            target.addMethod(method);
            target.toClass(AssertProcessor.class);
        }
    }

}