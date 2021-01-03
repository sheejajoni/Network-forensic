package com.github.username;
public class Main {

    public static void main(String[] args) {
        try {
            int InputNodes = 1;
            int HiddenNodes = 1;
            double Error = 3.20;
            int Itertaion = 100;
            String PatternsPath = "Pattern.csv";
            new Network(InputNodes,HiddenNodes,Error,Itertaion,PatternsPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}