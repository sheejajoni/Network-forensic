package com.github.username;
import java.util.ArrayList;
import java.util.Random;

public class Layer extends ArrayList<Neuron> {

    public Layer(int size) {
        for (int i = 0; (i < size); i++) {
            super.add(new Neuron());
        }

    }

    public Layer(int size, Layer layer, Random rnd, double plambda, double plearnRate) {
        for (int i = 0; (i < size); i++) {
            super.add(new Neuron(layer, rnd, plambda, plearnRate));
        }

    }
}
