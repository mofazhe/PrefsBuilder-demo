package com.mfz.prefsbuilder.annotationprocessor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import com.mfz.prefsbuilder.BasePrefsClass;
import com.mfz.prefsbuilder.DefaultValue;
import com.mfz.prefsbuilder.PrefsClass;
import com.mfz.prefsbuilder.PrefsVal;
import com.mfz.prefsbuilder.StringDecode;
import com.mfz.prefsbuilder.StringEncode;

/**
 * @author mz
 * @date 2020/05/14/Thu
 * @time 11:14
 */
public class ElementHandler {
    private final Filer mFiler;
    private final Elements mElementUtils;
    private Map<String, List<Element>> classMap = new HashMap<>();
    private Map<Integer, MethodInfo> mUserMethodParams = new HashMap<>();
    private Map<Integer, MethodInfo> mMethodInnerMap = new HashMap<>();
    private Map<String, PrefsClassInfo> mPrefsClassInfoMap = new HashMap<>();
    private Map<Integer, MethodInfo> mDecodeMethodMap = new HashMap<>();
    private Map<Integer, MethodInfo> mEncodeMethodMap = new HashMap<>();
    private TypeName mStringTypeName;
    private ClassName mBasePrefsClassname;

    public ElementHandler(Filer filer, Elements elementUtils) {
        mFiler = filer;
        mElementUtils = elementUtils;
        mStringTypeName = TypeName.get(String.class);
    }

    public void clean() {
        classMap.clear();
    }

    public void handleBasePrefs(RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BasePrefsClass.class);
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                String qualifiedName = typeElement.getQualifiedName().toString();
                mBasePrefsClassname = ClassName.bestGuess(qualifiedName);
                break;
            }
        }
    }

    public void handlePrefsClass(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        Map<String, String> options = processingEnv.getOptions();
        String defPrefsPkg = options.get(Const.OptionArg.PKG);
        if (StringUtils.isEmpty(defPrefsPkg)) {
            defPrefsPkg = Const.Default.PKG;
        }
        String classPrefix = options.get(Const.OptionArg.CLASS_PREFIX);
        if (StringUtils.isEmpty(classPrefix)) {
            classPrefix = Const.Default.CLASS_PREFIX;
        }
        String classSuffix = options.get(Const.OptionArg.CLASS_SUFFIX);
        if (StringUtils.isEmpty(classSuffix)) {
            classSuffix = Const.Default.CLASS_SUFFIX;
        }
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(PrefsClass.class);
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;

                PackageElement packageElement = mElementUtils.getPackageOf(element);
                String currentPkgName = packageElement.getQualifiedName().toString();

                PrefsClass annotation = typeElement.getAnnotation(PrefsClass.class);
                String pkgName;
                if (annotation.currentPkg()) {
                    pkgName = currentPkgName;
                } else {
                    pkgName = StringUtils.isEmpty(annotation.pkgName()
                            .replace(".", "")) ?
                            defPrefsPkg : annotation.pkgName();
                    if (pkgName.endsWith(".")) {
                        pkgName = pkgName.substring(0, pkgName.length() - 1);
                    }
                }
                String className = classPrefix + annotation.className() + classSuffix;
                String fileName = StringUtils.isEmpty(annotation.fileName()) ?
                        StringUtils.camel2SmallConst(annotation.className()) : annotation.fileName();
                PrefsClassInfo prefsClassInfo = PrefsClassInfo.newBuilder()
                        .className(ClassName.get(pkgName, className))
                        .fileName(fileName)
                        .build();
                mPrefsClassInfoMap.put(typeElement.getQualifiedName().toString(), prefsClassInfo);
            }
        }
    }

    public void handleDefObjectVal(RoundEnvironment roundEnv) {
        Set<? extends Element> aimElements = roundEnv.getElementsAnnotatedWith(DefaultValue.class);
        for (Element element : aimElements) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            String qualifiedName = enclosingElement.getQualifiedName().toString();

            ClassName className = ClassName.bestGuess(qualifiedName);
            DefaultValue annotation = element.getAnnotation(DefaultValue.class);
            int key = annotation.id();
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement executableElement = (ExecutableElement) element;
                int size = executableElement.getParameters().size();
                // if (size > 1) {
                //     return;
                // }
                MethodInfo params = MethodInfo.newBuilder()
                        .isMethod(true)
                        .name(executableElement.getSimpleName().toString())
                        .className(className)
                        .paramsNum(size)
                        .build();
                mUserMethodParams.put(key, params);
            } else if (element.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;
                MethodInfo params = MethodInfo.newBuilder()
                        .isMethod(false)
                        .name(variableElement.getSimpleName().toString())
                        .className(className)
                        .build();
                mUserMethodParams.put(key, params);
            }
        }
    }

    public void handleRuleMethod(RoundEnvironment roundEnv) {
        Map<Class<? extends Annotation>, Integer> map = AnnotationList.getRuleMethod();
        Set<Class<? extends Annotation>> classes = map.keySet();
        for (Class<? extends Annotation> cls : classes) {
            Set<? extends Element> aimElements = roundEnv.getElementsAnnotatedWith(cls);
            for (Element element : aimElements) {
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                String qualifiedName = enclosingElement.getQualifiedName().toString();

                ClassName className = ClassName.bestGuess(qualifiedName);
                if (element.getKind() == ElementKind.METHOD) {
                    ExecutableElement executableElement = (ExecutableElement) element;
                    int size = executableElement.getParameters().size();
                    // 序列化
                    // if (size > 2 || size < 1) {
                    //     continue;
                    // }
                    // 反序列化
                    // if (size > 3 || size < 2) {
                    //     return;
                    // }
                    MethodInfo params = MethodInfo.newBuilder()
                            .isMethod(true)
                            .name(executableElement.getSimpleName().toString())
                            .className(className)
                            .paramsNum(size)
                            .build();
                    mMethodInnerMap.put(map.get(cls), params);
                    break;
                }
            }
        }
    }

    public void handleCodecMethod(RoundEnvironment roundEnv) {
        List<Class<? extends Annotation>> classes = new ArrayList<>();
        classes.add(StringDecode.class);
        classes.add(StringEncode.class);
        for (Class<? extends Annotation> cls : classes) {
            Set<? extends Element> aimElements = roundEnv.getElementsAnnotatedWith(cls);
            for (Element element : aimElements) {
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                String qualifiedName = enclosingElement.getQualifiedName().toString();

                ClassName className = ClassName.bestGuess(qualifiedName);
                if (element.getKind() == ElementKind.METHOD) {
                    ExecutableElement executableElement = (ExecutableElement) element;
                    int size = executableElement.getParameters().size();
                    MethodInfo params = MethodInfo.newBuilder()
                            .isMethod(true)
                            .name(executableElement.getSimpleName().toString())
                            .className(className)
                            .paramsNum(size)
                            .build();
                    int id;
                    Annotation annotation = element.getAnnotation(cls);
                    if (annotation instanceof StringEncode) {
                        id = ((StringEncode) annotation).id();
                        mEncodeMethodMap.put(id, params);
                    } else {
                        id = ((StringDecode) annotation).id();
                        mDecodeMethodMap.put(id, params);
                    }
                    break;
                }
            }
        }
    }

    public void handlePrefsVal(RoundEnvironment roundEnv, Class<? extends Annotation> annotation) {
        Set<? extends Element> aimElements = roundEnv.getElementsAnnotatedWith(annotation);
        for (Element element : aimElements) {
            handleElement(element);
        }
    }

    private void handleElement(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            return;
        }
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = enclosingElement.getQualifiedName().toString();
        List<Element> elementList = classMap.get(fullClassName);
        if (elementList == null) {
            elementList = new ArrayList<>();
            classMap.put(fullClassName, elementList);
        }
        elementList.add(element);
    }

    public void createJavaFiles() {
        if (mBasePrefsClassname == null) {
            throw new NullPointerException("Base prefs class is null! Please implement BasePrefsInterface!");
        }
        Set<Map.Entry<String, List<Element>>> entrySet = classMap.entrySet();
        for (Map.Entry<String, List<Element>> entry : entrySet) {
            createPrefs(entry.getKey(), entry.getValue());
        }
    }

    private void createPrefs(String fullClassName, List<Element> elementList) {
        PrefsClassInfo classInfo = mPrefsClassInfoMap.get(fullClassName);
        if (classInfo == null) {
            throw new NullPointerException("The class not statement annotation:" + fullClassName);
        }
        ClassName className = classInfo.getClassName();
        String prefsFileName = classInfo.getFileName();
        String warningTxt = "Auto generated by apt,do not modify!!\n";

        TypeSpec.Builder builder = TypeSpec.classBuilder(className.simpleName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(mBasePrefsClassname);

        // 警告
        builder.addJavadoc(warningTxt);

        // 内部单例类
        builder.addType(TypeSpec.classBuilder("SingletonHolder")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addField(FieldSpec.builder(className, "INSTANCE")
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                        .initializer("new $T()", className)
                        .build())
                .build());

        // 构造方法
        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addCode(StringUtils.format("super(\"%s\");\n", prefsFileName))
                .build());

        // 获取实例方法
        builder.addMethod(MethodSpec.methodBuilder("getInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addCode("return SingletonHolder.INSTANCE;\n")
                .returns(className)
                .build());

        // get和set方法
        for (Element element : elementList) {
            addMethod(builder, element);
        }

        JavaFile javaFile = JavaFile.builder(className.packageName(), builder.build()).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMethod(TypeSpec.Builder classBuilder, Element element) {

        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = enclosingElement.getQualifiedName().toString();

        VariableElement variableElement = (VariableElement) element;
        // 注解的成员变量名
        String filedName = variableElement.getSimpleName().toString();
        // 注解的成员变量类型
        // String filedClassType = variableElement.asType().toString();
        // 注解的成员变量类型
        // String filedValue = (String) variableElement.getConstantValue();
        // filedValue = wrapperQuotes(filedValue);

        TypeName typeName = mStringTypeName;
        TypeName keyTypeName = null;
        TypeName valTypeName = null;
        Annotation annotation = null;
        Object defValue = null;
        String valueName = "String";
        ClassName currentClass = ClassName.bestGuess(qualifiedName);
        int defValFromId = -1;
        boolean defNull = false;
        String defString = null;
        int type = 0;
        TypeName prefixTypeName = null;
        TypeName suffixTypeName = null;
        int codecId = -1;
        for (Class<? extends Annotation> cls : AnnotationList.getPrefsVal()) {
            annotation = variableElement.getAnnotation(cls);
            prefixTypeName = MethodUtils.getPrefixType(element, cls, mElementUtils);
            suffixTypeName = MethodUtils.getSuffixType(element, cls, mElementUtils);
            if (annotation instanceof PrefsVal.Int) {
                typeName = TypeName.INT;
                PrefsVal.Int annotationObj = (PrefsVal.Int) annotation;
                defValue = annotationObj.defVal();
                defValFromId = annotationObj.defValFromId();
                valueName = "Int";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Float) {
                typeName = TypeName.FLOAT;
                PrefsVal.Float annotationObj = (PrefsVal.Float) annotation;
                defValue = annotationObj.defVal() + "f";
                defValFromId = annotationObj.defValFromId();
                valueName = "Float";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Bool) {
                typeName = TypeName.BOOLEAN;
                PrefsVal.Bool annotationObj = (PrefsVal.Bool) annotation;
                defValue = annotationObj.defVal();
                defValFromId = annotationObj.defValFromId();
                valueName = "Bool";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Byte) {
                typeName = TypeName.BYTE;
                PrefsVal.Byte annotationObj = (PrefsVal.Byte) annotation;
                defValue = StringUtils.format("(byte)%d", annotationObj.defVal());
                defValFromId = annotationObj.defValFromId();
                valueName = "Byte";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Double) {
                typeName = TypeName.DOUBLE;
                PrefsVal.Double annotationObj = (PrefsVal.Double) annotation;
                defValue = annotationObj.defVal();
                defValFromId = annotationObj.defValFromId();
                valueName = "Double";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Char) {
                typeName = TypeName.CHAR;
                PrefsVal.Char annotationObj = (PrefsVal.Char) annotation;
                char defChar = annotationObj.defVal();
                if (Character.isDefined(defChar)) {
                    defValue = StringUtils.format("'%c'", defChar);
                } else {
                    defValue = StringUtils.format("(char)%d", (int) defChar);
                }
                defValFromId = annotationObj.defValFromId();
                valueName = "Char";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Long) {
                typeName = TypeName.LONG;
                PrefsVal.Long annotationObj = (PrefsVal.Long) annotation;
                defValue = annotationObj.defVal();
                defValFromId = annotationObj.defValFromId();
                valueName = "Long";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Short) {
                typeName = TypeName.SHORT;
                PrefsVal.Short annotationObj = (PrefsVal.Short) annotation;
                defValue = StringUtils.format("(short)%d", annotationObj.defVal());
                defValFromId = annotationObj.defValFromId();
                valueName = "Short";
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.String) {
                typeName = mStringTypeName;
                PrefsVal.String annotationObj = (PrefsVal.String) annotation;
                if (annotationObj.defNull()) {
                    defValue = "null";
                } else {
                    defValue = MethodUtils.wrapperQuotes(annotationObj.defVal());
                }
                defValFromId = annotationObj.defValFromId();
                valueName = "String";
                codecId = annotationObj.codecId();
                type = 1;
                break;
            } else if (annotation instanceof PrefsVal.Object) {
                typeName = keyTypeName = MethodUtils.getType(element, cls, mElementUtils);
                PrefsVal.Object annotationObj = (PrefsVal.Object) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            } else if (annotation instanceof PrefsVal.List) {
                keyTypeName = MethodUtils.getType(element, cls, mElementUtils);
                if (keyTypeName != null) {
                    typeName = ParameterizedTypeName.get(ClassName.get(List.class), keyTypeName);
                }
                PrefsVal.List annotationObj = (PrefsVal.List) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            } else if (annotation instanceof PrefsVal.Set) {
                keyTypeName = MethodUtils.getType(element, cls, mElementUtils);
                if (keyTypeName != null) {
                    typeName = ParameterizedTypeName.get(ClassName.get(Set.class), keyTypeName);
                }
                PrefsVal.Set annotationObj = (PrefsVal.Set) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            } else if (annotation instanceof PrefsVal.Queue) {
                keyTypeName = MethodUtils.getType(element, cls, mElementUtils);
                if (keyTypeName != null) {
                    typeName = ParameterizedTypeName.get(ClassName.get(Queue.class), keyTypeName);
                }
                PrefsVal.Queue annotationObj = (PrefsVal.Queue) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            } else if (annotation instanceof PrefsVal.Deque) {
                keyTypeName = MethodUtils.getType(element, cls, mElementUtils);
                if (keyTypeName != null) {
                    typeName = ParameterizedTypeName.get(ClassName.get(Deque.class), keyTypeName);
                }
                PrefsVal.Deque annotationObj = (PrefsVal.Deque) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            } else if (annotation instanceof PrefsVal.SparseArray) {
                keyTypeName = MethodUtils.getType(element, cls, mElementUtils);
                if (keyTypeName != null) {
                    typeName = ParameterizedTypeName.get(
                            ClassName.bestGuess("android.util.SparseArray"), keyTypeName);
                }
                PrefsVal.SparseArray annotationObj = (PrefsVal.SparseArray) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            } else if (annotation instanceof PrefsVal.Map) {
                keyTypeName = MethodUtils.getKeyType(element, cls, mElementUtils);
                valTypeName = MethodUtils.getValType(element, cls, mElementUtils);
                if (keyTypeName != null && valTypeName != null) {
                    typeName = ParameterizedTypeName.get(
                            ClassName.get(Map.class), keyTypeName, valTypeName);
                }
                PrefsVal.Map annotationObj = (PrefsVal.Map) annotation;
                defValFromId = annotationObj.defValFromId();
                defNull = annotationObj.defNull();
                defString = annotationObj.defString();
                type = 2;
                break;
            }
        }

        switch (type) {
            case 1:
                buildBasicMethod(classBuilder, filedName, valueName, defValue, currentClass,
                        typeName, defValFromId, prefixTypeName, suffixTypeName, codecId);
                break;
            case 2:
                buildGenericMethod(classBuilder, filedName, currentClass, annotation.annotationType(),
                        typeName, keyTypeName, valTypeName, defValFromId, defNull, defString,
                        suffixTypeName, prefixTypeName);
                break;
            default:
                break;
        }
    }

    private void buildBasicMethod(TypeSpec.Builder classBuilder, String filedName, String valueName,
                                  Object defValue, ClassName currentClass, TypeName typeName,
                                  int defValFromId, TypeName prefixTypeName, TypeName suffixTypeName,
                                  int codecId) {
        MethodSpec.Builder methodBuilder;
        CodeBlock.Builder codeGetBuilder = CodeBlock.builder();
        MethodInfo methodInfo;
        boolean hasPrefix = prefixTypeName != null && prefixTypeName != TypeName.VOID;
        boolean hasSuffix = suffixTypeName != null && suffixTypeName != TypeName.VOID;
        String prefixTxt = "prefix";
        String suffixTxt = "suffix";

        String keyStatement;
        if (hasPrefix && hasSuffix) {
            keyStatement = prefixTxt + "+$T.%s+" + suffixTxt;
        } else if (hasPrefix) {
            keyStatement = prefixTxt + "+$T.%s";
        } else if (hasSuffix) {
            keyStatement = "$T.%s+" + suffixTxt;
        } else {
            keyStatement = "$T.%s";
        }
        keyStatement = StringUtils.format(keyStatement, filedName);

        // get方法
        methodInfo = mUserMethodParams.get(defValFromId);
        if (methodInfo == null) {
            codeGetBuilder.addStatement(
                    StringUtils.format("$T defVal=%s", String.valueOf(defValue)), typeName);
        } else if (methodInfo.isMethod()) {
            if (methodInfo.getParamsNum() == 0) {
                codeGetBuilder.addStatement(StringUtils.format(
                        "$T defVal=$T.%s()", methodInfo.getName()), typeName, methodInfo.getClassName());
            } else if (methodInfo.getParamsNum() == 1) {
                codeGetBuilder.addStatement(StringUtils.format(
                        "$T defVal=$T.%s(%s)", methodInfo.getName(), keyStatement),
                        typeName, methodInfo.getClassName(), currentClass);
            } else {
                notSupportMethod(methodInfo, 0, 1);
            }
        } else {
            codeGetBuilder.addStatement(StringUtils.format(
                    "$T defVal=$T.%s", methodInfo.getName()), typeName, methodInfo.getClassName());
        }
        methodInfo = mDecodeMethodMap.get(codecId);
        if (methodInfo == null) {
            codeGetBuilder.addStatement(
                    StringUtils.format("return getInstance().get%s(%s, defVal)",
                            valueName, keyStatement), currentClass);
        } else {
            codeGetBuilder.addStatement(
                    StringUtils.format("$T s=getInstance().get%s(%s, defVal)",
                            valueName, keyStatement), mStringTypeName, currentClass);
            if (methodInfo.getParamsNum() == 1) {
                codeGetBuilder.addStatement(StringUtils.format("return $T.%s(s)",
                        methodInfo.getName()), methodInfo.getClassName());
            } else if (methodInfo.getParamsNum() == 2) {
                codeGetBuilder.addStatement(StringUtils.format("return $T.%s(%s, s)",
                        methodInfo.getName(), keyStatement), methodInfo.getClassName());
            } else {
                notSupportMethod(methodInfo, 1, 2);
            }
        }

        String getMethodName;
        if (typeName == TypeName.BOOLEAN) {
            getMethodName = MethodUtils.getBoolMethodName(filedName);
        } else {
            getMethodName = MethodUtils.getGetMethodName(filedName);
        }
        methodBuilder = MethodSpec.methodBuilder(getMethodName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(typeName)
                .addCode(codeGetBuilder.build());
        if (hasPrefix) {
            methodBuilder.addParameter(prefixTypeName, prefixTxt, Modifier.FINAL);
        }
        if (hasSuffix) {
            methodBuilder.addParameter(suffixTypeName, suffixTxt, Modifier.FINAL);
        }
        classBuilder.addMethod(methodBuilder.build());

        // set方法
        CodeBlock.Builder codeSetBuilder = CodeBlock.builder();
        methodInfo = mEncodeMethodMap.get(codecId);
        if (methodInfo == null) {
            codeSetBuilder.addStatement(StringUtils.format(
                    "getInstance().set%s(%s, value)", valueName, keyStatement), currentClass);
        } else {
            if (methodInfo.getParamsNum() == 1) {
                codeSetBuilder.addStatement(StringUtils.format("$T encode=$T.%s(value)",
                        methodInfo.getName()), mStringTypeName, methodInfo.getClassName());
            } else if (methodInfo.getParamsNum() == 2) {
                codeSetBuilder.addStatement(StringUtils.format("$T encode=$T.%s(%s, value)",
                        methodInfo.getName(), keyStatement), mStringTypeName, methodInfo.getClassName());
            } else {
                notSupportMethod(methodInfo, 1, 2);
            }
            codeSetBuilder.addStatement(StringUtils.format(
                    "getInstance().set%s(%s, encode)", valueName, keyStatement), currentClass);
        }

        methodBuilder = MethodSpec.methodBuilder(MethodUtils.getSetMethodName(filedName))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addCode(codeSetBuilder.build());
        if (hasPrefix) {
            methodBuilder.addParameter(prefixTypeName, prefixTxt, Modifier.FINAL);
        }
        if (hasSuffix) {
            methodBuilder.addParameter(suffixTypeName, suffixTxt, Modifier.FINAL);
        }
        methodBuilder.addParameter(typeName, "value", Modifier.FINAL);
        classBuilder.addMethod(methodBuilder.build());
    }

    private void buildGenericMethod(TypeSpec.Builder classBuilder, String filedName,
                                    ClassName currentClass, Class<? extends Annotation> cls,
                                    TypeName typeName, TypeName keyTypeName, TypeName valTypeName,
                                    int defValFromId, boolean defNull, String defString,
                                    TypeName prefixTypeName, TypeName suffixTypeName) {
        CodeBlock.Builder codeGetBuilder = CodeBlock.builder();
        CodeBlock.Builder codeSetBuilder = CodeBlock.builder();
        boolean hasPrefix = prefixTypeName != null && prefixTypeName != TypeName.VOID;
        boolean hasSuffix = suffixTypeName != null && suffixTypeName != TypeName.VOID;
        String prefixTxt = "prefix";
        String suffixTxt = "suffix";
        MethodSpec.Builder methodBuilder;

        String keyStatement;
        if (hasPrefix && hasSuffix) {
            keyStatement = prefixTxt + "+$T.%s+" + suffixTxt;
        } else if (hasPrefix) {
            keyStatement = prefixTxt + "+$T.%s";
        } else if (hasSuffix) {
            keyStatement = "$T.%s+" + suffixTxt;
        } else {
            keyStatement = "$T.%s";
        }
        keyStatement = StringUtils.format(keyStatement, filedName);

        String valueName = "String";
        MethodInfo serializerMethod = null;
        ClassName serializerClass = null;
        String serializerName = null;
        for (Integer integer : AnnotationList.getSerializerByVal().get(cls)) {
            serializerMethod = mMethodInnerMap.get(integer);
            if (serializerMethod == null) {
                continue;
            }
            serializerClass = serializerMethod.getClassName();
            serializerName = serializerMethod.getName();
            break;
        }
        if (serializerMethod == null) {
            canNotFindMethod(cls, true);
        }

        MethodInfo deserializerMethod = null;
        ClassName deserializerClass = null;
        String deserializerName = null;
        for (Integer integer : AnnotationList.getDeserializerByVal().get(cls)) {
            deserializerMethod = mMethodInnerMap.get(integer);
            if (deserializerMethod == null) {
                continue;
            }
            deserializerClass = deserializerMethod.getClassName();
            deserializerName = deserializerMethod.getName();
            break;
        }
        if (deserializerMethod == null) {
            canNotFindMethod(cls, false);
        }

        // get方法
        codeGetBuilder.addStatement(StringUtils.format(
                "$T json = getInstance().get%s(%s, $S)",
                valueName, keyStatement), mStringTypeName, currentClass, defString);
        if (valTypeName == null) {
            if (deserializerMethod.getParamsNum() == 2) {
                codeGetBuilder.addStatement(StringUtils.format(
                        "$T value = $T.%s(json,$T.class)",
                        deserializerName), typeName, deserializerClass, keyTypeName);
            } else if (deserializerMethod.getParamsNum() == 3) {
                codeGetBuilder.addStatement(StringUtils.format(
                        "$T value = $T.%s(%s,json,$T.class)",
                        deserializerName, keyStatement), typeName, deserializerClass,
                        currentClass, keyTypeName);
            } else {
                notSupportMethod(serializerMethod, 2, 3);
            }
        } else {
            if (deserializerMethod.getParamsNum() == 3) {
                codeGetBuilder.addStatement(StringUtils.format(
                        "$T value = $T.%s(json,$T.class,$T.class)",
                        deserializerName), typeName, deserializerClass, keyTypeName, valTypeName);
            } else if (deserializerMethod.getParamsNum() == 4) {
                codeGetBuilder.addStatement(StringUtils.format(
                        "$T value = $T.%s(%s,json,$T.class,$T.class)",
                        deserializerName, keyStatement), typeName, deserializerClass,
                        currentClass, keyTypeName, valTypeName);
            } else {
                notSupportMethod(serializerMethod, 3, 4);
            }
        }
        if (defNull) {
            codeGetBuilder.addStatement("return value");
        } else {
            codeGetBuilder.beginControlFlow("if(value==null)");
            MethodInfo methodInfo = mUserMethodParams.get(defValFromId);
            if (methodInfo == null) {
                throw new NullPointerException(
                        "Can not set default value,because can not find a method or const with id="
                                + defValFromId + "!");
            } else if (methodInfo.isMethod()) {
                if (methodInfo.getParamsNum() == 0) {
                    codeGetBuilder.addStatement(StringUtils.format(
                            "return $T.%s()", methodInfo.getName()), methodInfo.getClassName());
                } else if (methodInfo.getParamsNum() == 1) {
                    codeGetBuilder.addStatement(StringUtils.format(
                            "return $T.%s(%s)", methodInfo.getName(), keyStatement),
                            methodInfo.getClassName(), currentClass);
                } else {
                    notSupportMethod(serializerMethod, 0, 1);
                }
            } else {
                codeGetBuilder.addStatement(StringUtils.format(
                        "return $T.%s", methodInfo.getName()), methodInfo.getClassName());
            }

            codeGetBuilder.nextControlFlow("else")
                    .addStatement("return value")
                    .endControlFlow();
        }

        methodBuilder = MethodSpec.methodBuilder(MethodUtils.getGetMethodName(filedName))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(typeName)
                .addCode(codeGetBuilder.build());
        if (hasPrefix) {
            methodBuilder.addParameter(prefixTypeName, prefixTxt, Modifier.FINAL);
        }
        if (hasSuffix) {
            methodBuilder.addParameter(suffixTypeName, suffixTxt, Modifier.FINAL);
        }
        classBuilder.addMethod(methodBuilder.build());

        // set方法
        if (serializerMethod.getParamsNum() == 1) {
            codeSetBuilder.addStatement(StringUtils.format("getInstance().set%s(%s, $T.%s(value))",
                    valueName, keyStatement, serializerName), currentClass, serializerClass);
        } else if (serializerMethod.getParamsNum() == 2) {
            codeSetBuilder.addStatement(StringUtils.format(
                    "getInstance().set%s(%s, $T.%s(%s,value))",
                    valueName, keyStatement, serializerName, keyStatement),
                    currentClass, serializerClass, currentClass);
        } else {
            notSupportMethod(serializerMethod, 1, 2);
        }

        methodBuilder = MethodSpec.methodBuilder(MethodUtils.getSetMethodName(filedName))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addCode(codeSetBuilder.build());
        if (hasPrefix) {
            methodBuilder.addParameter(prefixTypeName, prefixTxt, Modifier.FINAL);
        }
        if (hasSuffix) {
            methodBuilder.addParameter(suffixTypeName, suffixTxt, Modifier.FINAL);
        }
        methodBuilder.addParameter(typeName, "value", Modifier.FINAL);
        classBuilder.addMethod(methodBuilder.build());
    }

    private static void notSupportMethod(MethodInfo methodInfo, int num1, int num2) {
        throw new IllegalArgumentException("Only support param num " + num1 + " or " + num2 + ":"
                + methodInfo.getClassName() + "." + methodInfo.getClassName() + "!");
    }

    private static void canNotFindMethod(Class<? extends Annotation> cls, boolean isSerializer) {
        throw new IllegalArgumentException("Can not find " +
                (isSerializer ? "serializer" : "deserializer") + " method of " + cls + "!");
    }

}
