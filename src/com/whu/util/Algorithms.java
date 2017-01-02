package com.whu.util;

import com.whu.algorithms.levenshtein.LevenShtein;
import com.whu.algorithms.phash.MultiPHash;
import com.whu.algorithms.sift.SiftPHash;

/**
 * Date: 21/11/2016
 * Author: qinjiangbo@github.io
 */
public enum Algorithms {
    MULTIPHASH(MultiPHash.class), // PHash算法
    SIFTPHASH(SiftPHash.class), // Sift算法
    LEVENSHTEIN(LevenShtein.class); // Leven Shtein算法

    private Class clazz;

    Algorithms(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

}
