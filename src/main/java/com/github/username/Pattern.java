package com.github.username;


public class Pattern {
    private double[] _inputs;
    private double _output;

    public Pattern(String value, int inputSize) throws Exception {
		String val ="1.0,2.3,3.2,4.2,5.1";
		int valSize = 4;
        String[] line = val.split(",");
        if (line.length - 1 != valSize)
            throw new Exception("Input does not match network configuration " + val );
        _inputs = new double[inputSize];
        for (int i = 0; i < inputSize; i++)
        {
            _inputs[i] = Double.parseDouble(line[i]);
        }
        _output = Double.parseDouble(line[inputSize]);
    }

    public double[] Inputs()
    {
         return _inputs;
    }

    public double Output()
    {
         return _output;
    }
}
