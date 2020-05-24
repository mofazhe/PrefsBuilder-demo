package com.mfz.prefsbuilder;

/**
 * 基类需要实现的接口
 *
 * @author mz
 * @date 2020 /05/19/Tue
 * @time 16 :29
 */
public interface BasePrefsInterface {
    /**
     * Gets bool.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the bool
     */
    boolean getBool(String key, boolean defVal);

    /**
     * Sets bool.
     *
     * @param key the key
     * @param val the val
     */
    void setBool(String key, boolean val);

    /**
     * Gets int.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the int
     */
    int getInt(String key, int defVal);

    /**
     * Sets int.
     *
     * @param key the key
     * @param val the val
     */
    void setInt(String key, int val);

    /**
     * Gets byte.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the byte
     */
    byte getByte(String key, byte defVal);

    /**
     * Sets byte.
     *
     * @param key the key
     * @param val the val
     */
    void setByte(String key, byte val);

    /**
     * Gets char.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the char
     */
    char getChar(String key, char defVal);

    /**
     * Sets char.
     *
     * @param key the key
     * @param val the val
     */
    void setChar(String key, char val);

    /**
     * Gets short.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the short
     */
    short getShort(String key, short defVal);

    /**
     * Sets short.
     *
     * @param key the key
     * @param val the val
     */
    void setShort(String key, short val);

    /**
     * Gets long.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the long
     */
    long getLong(String key, long defVal);

    /**
     * Sets long.
     *
     * @param key the key
     * @param val the val
     */
    void setLong(String key, long val);

    /**
     * Gets float.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the float
     */
    float getFloat(String key, float defVal);

    /**
     * Sets float.
     *
     * @param key the key
     * @param val the val
     */
    void setFloat(String key, float val);

    /**
     * Gets double.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the double
     */
    double getDouble(String key, double defVal);

    /**
     * Sets double.
     *
     * @param key the key
     * @param val the val
     */
    void setDouble(String key, double val);

    /**
     * Gets string.
     *
     * @param key    the key
     * @param defVal the def val
     * @return the string
     */
    String getString(String key, String defVal);

    /**
     * Sets string.
     *
     * @param key the key
     * @param val the val
     */
    void setString(String key, String val);
}
