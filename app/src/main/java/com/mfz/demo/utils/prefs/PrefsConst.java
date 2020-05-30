package com.mfz.demo.utils.prefs;

import com.mfz.prefsbuilder.PrefsClass;
import com.mfz.prefsbuilder.PrefsVal;

/**
 * @author mz
 * @date 2020/05/30/星期六
 * @time 18:33:01
 */
public class PrefsConst {
    @PrefsClass(className = "Test", currentPkg = true)
    public static class Test {
        @PrefsVal.String()
        public static String SHOW_EDIT_TEXT_BUTTON = "a";
    }
}
