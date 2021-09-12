package mst.dijkstra;

import java.util.Arrays;

/*
 * 最短路径算法:
 *  普里姆     PrimAlgorithm
 *  克鲁斯卡尔  KruskalCase
 *  迪杰斯特拉  DijkstraAlgorithm
 *  弗洛伊德    FloydAlgorithm
 */


/**
 * 迪杰斯特拉(Dijkstra)算法是典型最短路径算法，用于计算一个结点到其他结点的最短路径。
 * 它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想)，直到扩展到终点为止。
 *
 * 战争时期，胜利乡有7个村庄(A, B, C, D, E, F, G) ，
 * 现在有六个邮差，从G点出发，需要分别把邮件分别送到 A, B, C , D, E, F 六个村庄
 * 各个村庄的距离用边线表示(权) ，比如 A – B 距离 5公里
 * 问：如何计算出G村庄到 其它各个村庄的最短距离?
 * 如果从其它点出发到各个点的最短距离又是多少?
 *
 *
 */

/*

     0     1     2     3     4     5     6
     A     B     C     D     E     F     G
A  65535 00005 00007 65535 65535 65535 00002
B  00005 65535 65535 00009 65535 65535 00003
C  00007 65535 65535 65535 00008 65535 65535
D  65535 00009 65535 65535 65535 00004 65535
E  65535 65535 00008 65535 65535 00005 00004
F  65535 65535 65535 00004 00005 65535 00006
G  00002 00003 65535 65535 00004 00006 65535


 */


public class DijkstraAlgorithm {

    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        //创建 Graph对象
        Graph graph = new Graph(vertex, matrix);
        //测试, 看看图的邻接矩阵是否ok
        graph.showGraph();
        //测试迪杰斯特拉算法
        graph.dsj(0);//输入出发顶点,A->0,B->1...G->6
        graph.showDijkstra();
    }

}

class Graph {
    private char[] vertex; // 顶点数组
    private int[][] matrix; // 邻接矩阵
    private VisitedVertex vv; //已经访问的顶点的集合

    // 构造器
    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    //显示结果
    public void showDijkstra() {
        System.out.println("=============begin=============");
        for (char c : vertex) {
            System.out.print(c + " ");
        }
        vv.show();
    }

    // 显示图
    public void showGraph2() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    // 显示图的邻接矩阵
    public void showGraph() {
        for (int i = 0; i < vertex.length; i++) {
            System.out.print(String.format("%s  %s   ", i == 0 ? "   " : "", i));
        }
        System.out.println();
        for (int i = 0; i < vertex.length; i++) {
            System.out.print(String.format("%s  %s   ", i == 0 ? "   " : "", vertex[i]));
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(String.format("%s  ", vertex[i]));
            int[] link = matrix[i];
            for (int j = 0; j < link.length; j++) {
                System.out.print(String.format("%05d ", link[j]));
            }
            System.out.println();
        }
    }


    //迪杰斯特拉算法实现

    /**
     * @param index 表示出发顶点对应的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(vertex.length, index);
        update(index);//更新index顶点到周围顶点的距离和前驱顶点
        for (int j = 1; j < vertex.length; j++) {
            index = vv.updateAlreadyArr();// 选择并返回新的访问顶点
            update(index); // 更新index顶点到周围顶点的距离和前驱顶点
        }
    }


    //更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点,
    private void update(int index) {
        int len = 0;
        //根据遍历我们的邻接矩阵的  matrix[index]行
        for (int j = 0; j < matrix[index].length; j++) {
            // len 含义是 : 出发顶点到index顶点的距离 + 从index顶点到j顶点的距离的和
            len = vv.getDis(index) + matrix[index][j];
            // 如果j顶点没有被访问过，并且 len 小于出发顶点到j顶点的距离，就需要更新
            if (!vv.inAlreadyArr(j) && len < vv.getDis(j)) {
                vv.updatePre(j, index); //更新j顶点的前驱为index顶点
                vv.updateDis(j, len); //更新出发顶点到j顶点的距离
            }
        }
    }
}

// 已访问顶点集合
class VisitedVertex {
    // 记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新
    public int[] already_arr;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] pre_visited;
    // 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    //构造器

    /**
     * @param length :表示顶点的个数
     * @param index: 出发顶点对应的下标, 比如G顶点，下标就是6
     */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];

        this.already_arr[index] = 1; //设置出发顶点被访问过
        Arrays.fill(dis, 65535);//初始化 dis数组
        this.dis[index] = 0;//设置出发顶点的访问距离为0
    }

    /**
     * 功能: 判断index顶点是否被访问过
     *
     * @param index
     * @return 如果访问过，就返回true, 否则访问false
     */
    public boolean inAlreadyArr(int index) {
        return already_arr[index] == 1;
    }

    /**
     * 功能: 更新出发顶点到index顶点的距离
     *
     * @param index
     * @param len
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /**
     * 功能: 更新pre这个顶点的前驱顶点为index顶点
     *
     * @param pre
     * @param index
     */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /**
     * 功能:返回出发顶点到index顶点的距离
     *
     * @param index
     */
    public int getDis(int index) {
        return dis[index];
    }


    /**
     * 继续选择并返回新的访问顶点， 比如这里的G 完后，就是 A点作为新的访问顶点(注意不是出发顶点)
     *
     * @return
     */
    public int updateAlreadyArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        //更新 index 顶点被访问过
        already_arr[index] = 1;
        return index;
    }

    //显示最后的结果
    //即将三个数组的情况输出
    public void show() {
        System.out.println();
        //输出already_arr
        for (int i : already_arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        //输出pre_visited
        for (int i : pre_visited) {
            System.out.print(i + " ");
        }
        System.out.println();
        //输出dis
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println();
        //为了好看最后的最短距离，我们处理
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        for (int i = 0; i < dis.length; i++) {
            System.out.print(vertex[i] + "(" + dis[i] + ") ");
        }
        System.out.println();
        System.out.println("============end=============");
    }

}

