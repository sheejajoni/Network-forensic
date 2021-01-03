package me.xiaohan.decision_tree;

public class DecisionTreeMain {
	public static void main(String[] args) {
		DataFrame df = new DataFrame("iris.data.txt");
		DecisionTreeNode node = new DecisionTreeNode(df);
		node.train();
		node.test(df);
	}
}
