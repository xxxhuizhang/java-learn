package mst.floyd;

import java.util.Arrays;

/*
 * 最短路径算法:
 *  普里姆     PrimAlgorithm
 *  克鲁斯卡尔  KruskalCase
 *  迪杰斯特拉  DijkstraAlgorithm
 *  弗洛伊德    FloydAlgorithm
 */

/**
 * 1.和Dijkstra算法一样，弗洛伊德(Floyd)算法也是一种用于寻找给定的加权图中顶点间最短路径的算法。
 * 2.该算法名称以创始人之一、1978年图灵奖获得者、斯坦福大学计算机科学系教授罗伯特·弗洛伊德命名
 * 3.弗洛伊德算法(Floyd)计算图中各个顶点之间的最短路径
 * 4.迪杰斯特拉算法用于计算图中某一个顶点到其他顶点的最短路径。
 * 5.弗洛伊德算法 VS 迪杰斯特拉算法：迪杰斯特拉算法通过选定的被访问顶点，
 * 求出从出发访问顶点到其他顶点的最短路径；弗洛伊德算法中每一个顶点都是出发访问点，
 * 所以需要将每一个顶点看做被访问顶点，求出从每一个顶点到其他顶点的最短路径。
 */

/*

    0   1   2   3   4   5   6
    A   B   C   D   E   F   G
A  000 005 007 999 999 999 002
B  005 000 999 009 999 999 003
C  007 999 000 999 008 999 999
D  999 009 999 000 999 004 999
E  999 999 008 999 000 005 004
F  999 999 999 004 005 000 006
G  002 003 999 999 004 006 000

------------------------------------

    0   1   2   3   4   5   6
    A   B   C   D   E   F   G
A  000 005 007 012 006 008 002
B  005 000 012 009 007 009 003
C  007 012 000 017 008 013 009
D  012 009 017 000 009 004 010
E  006 007 008 009 000 005 004
F  008 009 013 004 005 000 006
G  002 003 009 010 004 006 000



 */

public class FloydAlgorithm {

    public static void main(String[] args) {
        // 测试看看图是否创建成功
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 999;//65535
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};


        // 创建 Graph 对象
        Graph graph = new Graph(vertex.length, matrix, vertex);
        graph.show();
        // 调用弗洛伊德算法
        graph.floyd();
        graph.show();
    }

}

// 创建图
class Graph {
    private char[] vertex; // 存放顶点的数组
    private int[][] dis; // 保存，从各个顶点出发到其它顶点的距离，最后的结果，也是保留在该数组
    private int[][] pre;// 保存到达目标顶点的前驱顶点

    // 构造器

    /**
     * @param length 大小
     * @param matrix 邻接矩阵
     * @param vertex 顶点数组
     */
    public Graph(int length, int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        // 对pre数组初始化, 注意存放的是前驱顶点的下标
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

    // 显示pre数组和dis数组
    public void show() {

        for (int i = 0; i < vertex.length; i++) {
            System.out.print(String.format("%s  %s ", i == 0 ? "  " : "", i));
        }
        System.out.println();
        for (int i = 0; i < vertex.length; i++) {
            System.out.print(String.format("%s  %s ", i == 0 ? "  " : "", vertex[i]));
        }
        System.out.println();
        for (int i = 0; i < dis.length; i++) {
            System.out.print(String.format("%s  ", vertex[i]));
            for (int j = 0; j < dis[i].length; j++) {
                System.out.printf("%03d ", dis[i][j]);
            }
            System.out.println();
        }

        // 为了显示便于阅读，我们优化一下输出
//        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        for (int k = 0; k < dis.length; k++) {
            // 先将pre数组输出的一行
            for (int i = 0; i < dis.length; i++) {
                System.out.print(vertex[pre[k][i]] + " ");
            }
            System.out.println();
            // 输出dis数组的一行数据
            for (int i = 0; i < dis.length; i++) {
                System.out.print("(" + vertex[k] + "->" + vertex[i] + ":" + dis[k][i] + ") ");
            }
            System.out.println();
            System.out.println();
        }
    }

    // 弗洛伊德算法, 比较容易理解，而且容易实现
    public void floyd() {
        int len = 0; // 变量保存距离
        // 对中间顶点遍历， k 就是中间顶点的下标 [A, B, C, D, E, F, G]
        for (int k = 0; k < dis.length; k++) { //
            // 从i顶点开始出发 [A, B, C, D, E, F, G]
            for (int i = 0; i < dis.length; i++) {
                // 到达j顶点 // [A, B, C, D, E, F, G]
                for (int j = 0; j < dis.length; j++) {
                    len = dis[i][k] + dis[k][j];// => 求出从i 顶点出发，经过 k中间顶点，到达 j 顶点距离
                    if (len < dis[i][j]) {// 如果len小于 dis[i][j]
                        dis[i][j] = len;// 更新距离
                        pre[i][j] = pre[k][j];// 更新前驱顶点
                    }
                }
            }
        }
    }
}
