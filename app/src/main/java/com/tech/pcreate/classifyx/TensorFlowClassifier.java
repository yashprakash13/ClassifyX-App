package com.tech.pcreate.classifyx;

import android.content.res.AssetManager;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Costa on 3/3/2018.
 */

public class TensorFlowClassifier implements Classifier {


    private static final float THRESHOLD = 0.1f;

    private TensorFlowInferenceInterface tfHelper;
    private String inputName;
    private String outputName;
    private int inputSize;
    private List<String> labels;
    private float[] output;
    private String[] outputNames;


    private static List<String> readLabels(AssetManager am, String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(am.open(fileName)));

        String line;
        List<String> labels = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            labels.add(line);
        }

        br.close();
        return labels;
    }

    public static TensorFlowClassifier create(AssetManager assetManager,
                                              String modelPath, String labelFile, int inputSize, String inputName, String outputName) throws IOException {
        TensorFlowClassifier c = new TensorFlowClassifier();

        c.inputName = inputName;
        c.outputName = outputName;

        c.labels = readLabels(assetManager, labelFile);

        c.tfHelper = new TensorFlowInferenceInterface(assetManager, modelPath);
        int numClasses = 5;

        c.inputSize = inputSize;

        c.outputNames = new String[] { outputName };

        c.outputName = outputName;
        c.output = new float[numClasses];


        return c;
    }
    @Override
    public Classification recognize(final float[] pixels) {

        tfHelper.feed(inputName, pixels, 1, inputSize, inputSize, 3);


        tfHelper.run(outputNames);

        //get the output
        tfHelper.fetch(outputName, output);

        // Find the best classification
        Classification ans = new Classification();
        for (int i = 0; i < output.length; ++i) {
            System.out.println(output[i]);
            System.out.println(labels.get(i));
            if (output[i] > THRESHOLD && output[i] > ans.getConf()) {
                ans.update(output[i], labels.get(i));
            }
        }

        return ans;
    }

}
