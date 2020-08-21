package com.mfz.prefsbuilder.annotationprocessor;

import com.squareup.javapoet.ClassName;

/**
 * @author mz
 * @date 2020/05/15/Fri
 * @time 17:42
 */
public class MethodInfo {
    private boolean mIsMethod;
    private String mName;
    private ClassName mClassName;
    private int mParamsNum;

    private MethodInfo(Builder builder) {
        setMethod(builder.mIsMethod);
        setName(builder.mName);
        setClassName(builder.mClassName);
        setParamsNum(builder.mParamsNum);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isMethod() {
        return mIsMethod;
    }

    public void setMethod(boolean method) {
        mIsMethod = method;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ClassName getClassName() {
        return mClassName;
    }

    public void setClassName(ClassName className) {
        mClassName = className;
    }

    public int getParamsNum() {
        return mParamsNum;
    }

    public void setParamsNum(int paramsNum) {
        mParamsNum = paramsNum;
    }

    public static final class Builder {
        private boolean mIsMethod;
        private String mName;
        private ClassName mClassName;
        private int mParamsNum;

        private Builder() {
        }

        public Builder isMethod(boolean isMethod) {
            mIsMethod = isMethod;
            return this;
        }

        public Builder name(String name) {
            mName = name;
            return this;
        }

        public Builder className(ClassName className) {
            mClassName = className;
            return this;
        }

        public Builder paramsNum(int paramsNum) {
            mParamsNum = paramsNum;
            return this;
        }

        public MethodInfo build() {
            return new MethodInfo(this);
        }
    }
}
