/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mfz.prefsbuilder.annotationprocessor;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by lyy on 2017/6/6.
 */
class PrintLog {

    private volatile static PrintLog INSTANCE = null;
    private Messager mMessager;

    public static PrintLog init(Messager msg) {
        if (INSTANCE == null) {
            synchronized (PrintLog.class) {
                INSTANCE = new PrintLog(msg);
            }
        }
        return INSTANCE;
    }

    public static PrintLog getInstance() {
        return INSTANCE;
    }

    private PrintLog() {
    }

    private PrintLog(Messager msg) {
        mMessager = msg;
    }

    public static void error(String msg, Object... args) {
        getInstance().mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    public static void warning(String msg, Object... args) {
        getInstance().mMessager.printMessage(Diagnostic.Kind.WARNING, String.format(msg, args));
    }

    public static void info(String msg, Object... args) {
        getInstance().mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
