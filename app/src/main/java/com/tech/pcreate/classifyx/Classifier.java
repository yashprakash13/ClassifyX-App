package com.tech.pcreate.classifyx;

/**
 * Created by Costa on 3/3/2018.
 */

public interface Classifier {
    Classification recognize(final float[] pixels);
}
