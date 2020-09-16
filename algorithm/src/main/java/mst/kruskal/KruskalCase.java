package mst.kruskal;

import java.util.Arrays;


/*
 * 最短路径算法:
 *  普里姆     PrimAlgorithm
 *  克鲁斯卡尔  KruskalCase
 *
 *  迪杰斯特拉  DijkstraAlgorithm
 *  弗洛伊德    FloydAlgorithm
 */

/**
 * 某城市新增7个站点(A, B, C, D, E, F, G) ，现在需要修路把7个站点连通
 * 各个站点的距离用边线表示(权) ，比如 A – B 距离 12公里
 * 问：如何修路保证各个站点都能连通，并且总的修建公路总里程最短
 * <p>
 * 克鲁斯卡尔(Kruskal)算法，是用来求加权连通图的最小生成树的算法。
 * 基本思想：按照权值从小到大的顺序选择n-1条边，并保证这n-1条边不构成回路
 * 具体做法：首先构造一个只含n个顶点的森林，然后依权值从小到大从连通网中选择边加入到森林中，
 * 并使森林中不产生回路，直至森林变成一棵树为止
 */

public class KruskalCase {

    private int edgeNum; // 边的个数
    private char[] vertexs; // 顶点数组
    private int[][] matrix; // 邻接矩阵
    // 使用 INF 表示两个顶点不能连通
//    private static final int INF = Integer.MAX_VALUE;
    private static final int INF = 999;

    public static void main(String[] args) {
/*
            0   1   2   3   4   5   6
            A   B   C   D   E   F   G
        A  000 012 999 999 999 016 014
        B  012 000 010 999 999 007 999
        C  999 010 000 003 005 006 999
        D  999 999 003 000 004 999 999
        E  999 999 005 004 000 002 008
        F  016 007 006 999 002 000 009
        G  014 999 999 999 008 009 000

        排序后的集合=[
        EData [<E, F>= 2],
        EData [<C, D>= 3],
        EData [<D, E>= 4],
        EData [<C, E>= 5],
        EData [<C, F>= 6],
        EData [<B, F>= 7],
        EData [<E, G>= 8],
        EData [<F, G>= 9],
        EData [<B, C>= 10],
        EData [<A, B>= 12],
        EData [<A, G>= 14],
        EData [<A, F>= 16]
        ]

        C-E构成回路
        C-F构成回路
        F-G构成回路
        B-C构成回路
        A-G构成回路
        A-F构成回路


        EData [<C, D>= 3]
        EData [<D, E>= 4]
        EData [<E, F>= 2]
        EData [<E, G>= 8]
        EData [<A, B>= 12]
        EData [<B, F>= 7]
*/
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 克鲁斯卡尔算法的邻接矩阵
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};

        // 大家可以在去测试其它的邻接矩阵，结果都可以得到最小生成树.

        // 创建KruskalCase 对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        // 输出构建的
        kruskalCase.print();
        kruskalCase.kruskal();
    }

    // 构造器
    public KruskalCase(char[] vertexs, int[][] matrix) {
        // 初始化顶点数和边的个数
        int vlen = vertexs.length;
        // 初始化顶点, 复制拷贝的方式
        this.vertexs = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }
        // 初始化边, 使用的是复制拷贝的方式
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        // 统计边的条数
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal() {
        int index = 0; // 表示最后结果数组的索引
        int[] ends = new int[edgeNum]; // 用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点
        // 创建结果数组, 保存最后的最小生成树
        EData[] rets = new EData[edgeNum];

        // 获取图中 所有的边的集合 ， 一共有12边
        EData[] edges = getEdges();
        System.out.println("图的边的集合=" + Arrays.toString(edges) + " 共" + edges.length); // 12

        // 按照边的权值大小进行排序(从小到大)
        sortEdges(edges);
        System.out.println("排序后的集合=" + Arrays.toString(edges));

        // 遍历edges 数组，将边添加到最小生成树中时，判断是准备加入的边否形成了回路，如果没有，就加入 rets, 否则不能加入
        for (int i = 0; i < edgeNum; i++) {
            // 获取到第i条边的第一个顶点(起点)
            int p1 = getPosition(edges[i].start); //p1=4
            // 获取到第i条边的第2个顶点
            int p2 = getPosition(edges[i].end); //p2=5

            // 获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(ends, p1); // m = 4
            // 获取p2这个顶点在已有最小生成树中的终点
            int n = getEnd(ends, p2); // n = 5
            // 是否构成回路
            if (m != n) { // 没有构成回路
                ends[m] = n; // 设置m 在"已有最小生成树"中的终点 <E,F> [0,0,0,0,5,0,0,0,0,0,0,0]
                rets[index++] = edges[i]; // 有一条边加入到rets数组
            } else {
                System.out.println(edges[i].start + "-" + edges[i].end + "构成回路");
            }
        }
        // <E,F> <C,D> <D,E> <B,F> <E,G> <A,B>。
        // 统计并打印 "最小生成树", 输出 rets
        System.out.println("最小生成树为");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }

    }

    // 打印邻接矩阵
    public void print() {
        for (int i = 0; i < vertexs.length; i++) {
            System.out.print(String.format("%s  %s ", i == 0 ? "  " : "", i));
        }
        System.out.println();
        for (int i = 0; i < vertexs.length; i++) {
            System.out.print(String.format("%s  %s ", i == 0 ? "  " : "", vertexs[i]));
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(String.format("%s  ", vertexs[i]));
            int[] link = matrix[i];
            for (int j = 0; j < link.length; j++) {
                System.out.print(String.format("%03d ", link[j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 功能：对边进行排序处理, 冒泡排序
     *
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {// 交换
                    EData tmp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * @param ch 顶点的值，比如'A','B'
     * @return 返回ch顶点对应的下标，如果找不到，返回-1
     */
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {// 找到
                return i;
            }
        }
        // 找不到,返回-1
        return -1;
    }

    /**
     * 功能: 获取图中边，放到EData[] 数组中，后面我们需要遍历该数组 是通过matrix 邻接矩阵来获取 EData[] 形式 [['A','B',
     * 12], ['B','F',7], .....]
     *
     * @return
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 功能: 获取下标为i的顶点的终点(), 用于后面判断两个顶点的终点是否相同
     *
     * @param ends ： 数组就是记录了各个顶点对应的终点是哪个,ends 数组是在遍历过程中，逐步形成
     * @param i    : 表示传入的顶点对应的下标
     * @return 返回的就是 下标为i的这个顶点对应的终点的下标, 一会回头还有来理解
     */
    private int getEnd(int[] ends, int i) { // i = 4 [0,0,0,0,5,0,0,0,0,0,0,0]
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

}

//创建一个类EData ，它的对象实例就表示一条边
class EData {
    char start; // 边的一个点
    char end; // 边的另外一个点
    int weight; // 边的权值
    // 构造器

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    // 重写toString, 便于输出边信息
    @Override
    public String toString() {
        return "EData [<" + start + ", " + end + ">= " + weight + "]";
    }

}
