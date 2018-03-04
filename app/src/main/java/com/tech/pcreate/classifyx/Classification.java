package com.tech.pcreate.classifyx;

/**
 * Created by Costa on 3/3/2018.
 */

class Classification {
    //conf is the output confidence
    private float conf;
    //input label
    private String label;

    Classification() {
        this.conf = -1.0F;
        this.label = null;
    }

    void update(float conf, String label) {
        this.conf = conf;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public float getConf() {
        return conf;
    }
}
