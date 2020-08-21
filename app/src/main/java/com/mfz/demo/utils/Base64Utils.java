package com.mfz.demo.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.mfz.prefsbuilder.StringCodec;

public class Base64Utils {

    @StringCodec.Encode(id = 1)
    public static String encodeToString(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        StringBuilder base64Builder = new StringBuilder((text.length() / 3 + 1) * 4);
        int bufferSize = 3 * 128;
        for (int i = 0; i < text.length(); i += bufferSize) {
            int endIndex = i + bufferSize;
            if (endIndex > text.length()) {
                endIndex = text.length();
            }
            byte[] encoded = new byte[0];
            try {
                encoded = Base64.encode(text.subSequence(i, endIndex).toString().getBytes(),
                        Base64.NO_WRAP | Base64.URL_SAFE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            base64Builder.append(new String(encoded));
        }

        // return fixToStringOOM(base64Builder,4);
        return base64Builder.toString();
    }

    @StringCodec.Decode(id = 1)
    public static String decodeToString(String base64Text) {
        if (TextUtils.isEmpty(base64Text)) {
            return null;
        }
        StringBuilder textBuilder = new StringBuilder(base64Text.length() / 4 * 3);
        int bufferSize = 4 * 128;
        for (int i = 0; i < base64Text.length(); i += bufferSize) {
            int endIndex = i + bufferSize;
            if (endIndex > base64Text.length()) {
                endIndex = base64Text.length();
            }
            byte[] decoded = new byte[0];
            try {
                decoded = Base64.decode(
                        base64Text.substring(i, endIndex), Base64.NO_WRAP | Base64.URL_SAFE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            textBuilder.append(new String(decoded));
        }
        // return fixToStringOOM(textBuilder,3);
        return textBuilder.toString();
    }
}
