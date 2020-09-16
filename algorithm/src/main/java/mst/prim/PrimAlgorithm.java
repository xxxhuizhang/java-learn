package mst.prim;

import java.util.Arrays;

/**
 * 求最小生成树:
 * 普里姆     PrimAlgorithm
 * 克鲁斯卡尔  KruskalCase
 *
 * 迪杰斯特拉  DijkstraAlgorithm
 * 弗洛伊德    FloydAlgorithm
 */

/**
 * 胜利乡有7个村庄(A, B, C, D, E, F, G) ，现在需要修路把7个村庄连通
 * 各个村庄的距离用边线表示(权) ，比如 A – B 距离 5公里
 * 问：如何修路保证各个村庄都能连通，并且总的修建公路总里程最短?
 * 思路: 将10条边，连接即可，但是总的里程数不是最小.
 * 正确的思路，就是尽可能的选择少的路线，并且每条路线最小，保证总里程数最少.
 *
 * 修路问题本质就是就是最小生成树问题， 先介绍一下最小生成树(Minimum Cost Spanning Tree)，简称MST。
 * 给定一个带权的无向连通图,如何选取一棵生成树,使树上所有边上权的总和为最小,这叫最小生成树
 * 求最小生成树的算法主要是普里姆算法和克鲁斯卡尔算法
 */

/*
     A     B     C     D     E     F     G
A  10000 00005 00007 10000 10000 10000 00002
B  00005 10000 10000 00009 10000 10000 00003
C  00007 10000 10000 10000 00008 10000 10000
D  10000 00009 10000 10000 10000 00004 10000
E  10000 10000 00008 10000 10000 00005 00004
F  10000 10000 10000 00004 00005 10000 00006
G  00002 00003 10000 10000 00004 00006 10000

 */

public class PrimAlgorithm {

    public static void main(String[] args) {
        // 测试看看图是否创建ok
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        // 邻接矩阵的关系使用二维数组表示,10000这个大数，表示两个点不联通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},};

        // 创建MGraph对象
        MGraph graph = new MGraph(verxs);
        // 创建一个MinTree对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, verxs, data, weight);
        // 输出
        minTree.showGraph(graph);
        // 测试普利姆算法
        minTree.prim(graph, 0);// 从哪个顶点开始,A->0,B->1
    }

}

//创建最小生成树->村庄的图
class MinTree {
    // 创建图的邻接矩阵

    /**
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, int verxs, char data[], int[][] weight) {
        int i, j;
        for (i = 0; i < verxs; i++) {// 顶点
            graph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    // 显示图的邻接矩阵
    public void showGraph2(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    // 显示图的邻接矩阵
    public void showGraph(MGraph graph) {
        for (int i = 0; i < graph.data.length; i++) {
            System.out.print(String.format("%s  %s   ", i == 0 ? "   " : "", i));
        }
        System.out.println();
        for (int i = 0; i < graph.data.length; i++) {
            System.out.print(String.format("%s  %s   ", i == 0 ? "   " : "", graph.data[i]));
        }
        System.out.println();
        for (int i = 0; i < graph.weight.length; i++) {
            System.out.print(String.format("%s  ", graph.data[i]));
            int[] link = graph.weight[i];
            for (int j = 0; j < link.length; j++) {
                System.out.print(String.format("%05d ", link[j]));
            }
            System.out.println();
        }
    }

    // 编写prim算法，得到最小生成树

    /**
     * @param graph 图
     * @param v     表示从图的第几个顶点开始生成'A'->0 'B'->1...
     */
    public void prim(MGraph graph, int v) {
        // visited[] 标记结点(顶点)是否被访问过,默认元素的值都是0, 表示没有访问过
        int visited[] = new int[graph.verxs];
        // 把当前这个结点标记为已访问
        visited[v] = 1;
        // h1和h2记录两个顶点的下标,为将要连接的两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; // 将 minWeight 初始成一个大数，后面在遍历过程中，会被替换
        for (int k = 1; k < graph.verxs; k++) {// 因为有 graph.verxs顶点，普利姆算法结束后，有 graph.verxs-1边
            // 这个是确定每一次生成的子图 ，和哪个结点的距离最近
            for (int i = 0; i < graph.verxs; i++) {// i结点表示被访问过的结点
                for (int j = 0; j < graph.verxs; j++) {// j结点表示还没有访问过的结点
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        // 替换minWeight(寻找已经访问过的结点和未访问过的结点间的权值最小的边)
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            // 找到一条边是最小
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值:" + minWeight);
            // 将当前这个结点标记为已经访问
            visited[h2] = 1;
            // minWeight 重新设置为最大值 10000
            minWeight = 10000;
        }

    }
}

class MGraph {
    int verxs; // 表示图的节点个数
    char[] data;// 存放结点数据
    int[][] weight; // 存放边，就是我们的邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}
