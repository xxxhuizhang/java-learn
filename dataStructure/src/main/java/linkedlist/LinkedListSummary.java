package linkedlist;

import org.junit.Test;

import java.util.HashMap;
import java.util.Stack;

/**
 * https://blog.csdn.net/fightforyourdream/article/details/16353519(本文)
 * <p>
 * https://www.cnblogs.com/qianguyihao/p/4782595.html  (从这篇文章里发现的)
 * <p>
 * http://blog.csdn.net/luckyxiaoqiang/article/details/7393134 轻松搞定面试中的链表题目
 * http://www.cnblogs.com/jax/archive/2009/12/11/1621504.html 算法大全（1）单链表
 * <p>
 * 目录：
 * 1. 求单链表中结点的个数: getListLength
 * 2. 将单链表反转: reverseList（遍历），reverseListRec（递归） （剑指offer，题16）
 * 3. 查找单链表中的倒数第K个结点（k > 0）: reGetKthNode  （剑指offer，题15）
 * 4. 查找单链表的中间结点: getMiddleNode
 * 5. 从尾到头打印单链表: reversePrintListStack，reversePrintListRec（递归） （剑指offer，题5）
 * 6. 已知两个单链表pHead1 和pHead2 各自有序，把它们合并成一个链表依然有序: mergeSortedList, mergeSortedListRec （剑指offer，题17）
 * 7. 判断一个单链表中是否有环: hasCycle
 * 8. 判断两个单链表是否相交: isIntersect
 * 9. 求两个单链表相交的第一个节点: getFirstCommonNode
 * 10. 已知一个单链表中存在环，求进入环中的第一个节点: getFirstNodeInCycle, getFirstNodeInCycleHashMap （剑指offer，题56）
 * 11. 给出一单链表头指针pHead和一节点指针pToBeDeleted，O(1)时间复杂度删除节点pToBeDeleted: delete
 */
public class LinkedListSummary {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
//
//        printList(n1);
//		System.out.println(getListLength(n1));
//		Node head = reverseList(n1);
//		Node head = reverseListRec(n1);
//		printList(head);

//        Node x = reGetKthNode(n1, 1);
//        System.out.println(x.val);
//        Node x = reGetKthNodeRec(n1, 2); //不能返回倒数第k次调用返回的结果
//        System.out.println(x.val);

//        Node x = getMiddleNode(n1);
//        System.out.println(x.val);
//		System.out.println("reversePrintListStack:");
//		reversePrintListStack(head);
//		System.out.println("reversePrintListRec:");
//		reversePrintListRec(head);

    }

    @Test
    public void testergeMSortedList() {

        //commonNode
        Node c8 = new Node(8);
        Node c9 = new Node(9);

        Node n1 = new Node(1);
        Node n2 = new Node(3);
        Node n3 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = c8;
        c8.next = c9;

        Node m1 = new Node(1);
        Node m2 = new Node(4);
        Node m3 = new Node(6);
        m1.next = m2;
        m2.next = m3;
        m3.next = c8;
        c8.next = c9;

//        Node ret = mergeSortedList(n1, m1);
//        Node ret = mergeSortedListRec(n1, m1);
        Node ret = getFirstCommonNode(n1, m1);
        printList(ret);
    }


    @Test
    public void testHasCycle() {

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n1; //n1,n2

//        System.out.println("链表中" + (hasCycle(n1) ? "有" : "没有") + "环");

//        Node node = getFirstNodeInCycle(n1);
        Node node = getFirstNodeInCycleByLength(n1, 5);
        System.out.println("环链的头结点为:" + node.val);
    }

    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    //-----------------新加添加方法---------begin------------
    public Node head;
    public Node current;

    //方法：向链表中添加数据
    public void add(int data) {
        //判断链表为空的时候
        if (head == null) {//如果头结点为空，说明这个链表还没有创建，那就把新的结点赋给头结点
            head = new Node(data);
            current = head;
        } else {
            //创建新的结点，放在当前节点的后面（把新的结点合链表进行关联）
            current.next = new Node(data);
            //把链表的当前索引向后移动一位
            current = current.next;
        }
    }

    //方法重载：向链表中添加结点
    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (head == null) {
            head = node;
            current = head;
        } else {
            current.next = node;
            current = current.next;
        }
    }
    //-----------------新加添加方法---------end------------

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    // 求单链表中结点的个数
    // 注意检查链表是否为空。时间复杂度为O（n）
    public static int getListLength(Node head) {
        // 注意头结点为空情况
        if (head == null) {
            return 0;
        }

        int len = 0;
        Node cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        return len;
    }

    // 翻转链表（遍历）
    // 从头到尾遍历原链表，每遍历一个结点，
    // 将其摘下放在新链表的最前端。
    // 注意链表为空和只有一个结点的情况。时间复杂度为O（n）
    public static Node reverseList(Node head) {
        // 如果链表为空或只有一个节点，无需反转，直接返回原链表表头
        if (head == null || head.next == null) {
            return head;
        }

        Node reHead = null;        // 反转后新链表指针
        Node cur = head;

        while (cur != null) {
            Node preCur = cur;        // 用preCur保存住对要处理节点的引用
            cur = cur.next;            // cur更新到下一个节点
            preCur.next = reHead;    // 更新要处理节点的next引用
            reHead = preCur;            // reHead指向要处理节点的前一个节点
        }

        return reHead;
    }

    // 翻转递归（递归）
    // 递归的精髓在于你就默认reverseListRec已经成功帮你解决了子问题了！但别去想如何解决的
    // 现在只要处理当前node和子问题之间的关系。最后就能圆满解决整个问题。
	/*
		 head
			1 -> 2 -> 3 -> 4

		  head
			1--------------
			                    |
	               4 -> 3 -> 2							// Node reHead = reverseListRec(head.next);
	           reHead      head.next

	               4 -> 3 -> 2 -> 1					// head.next.next = head;
	           reHead

	                4 -> 3 -> 2 -> 1 -> null			// head.next = null;
	           reHead
	 */
    public static Node reverseListRec(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node reHead = reverseListRec(head.next);
        head.next.next = head;        // 把head接在reHead串的最后一个后面
        head.next = null;                // 防止循环链表
        return reHead;
    }

    /**
     * 从尾到头打印单链表
     * 对于这种颠倒顺序的问题，我们应该就会想到栈，后进先出。所以，这一题要么自己使用栈，要么让系统使用栈，也就是递归。注意链表为空的情况
     * 。时间复杂度为O（n）
     */
    public static void reversePrintListStack(Node head) {
        Stack<Node> s = new Stack<>();
        Node cur = head;
        while (cur != null) {
            s.push(cur);
            cur = cur.next;
        }

        while (!s.empty()) {
            cur = s.pop();
            System.out.print(cur.val + " ");
        }
        System.out.println();
    }

    /**
     * 从尾到头打印链表，使用递归（优雅！）
     */
    public static void reversePrintListRec(Node head) {
        if (head == null) {
            return;
        } else {
            reversePrintListRec(head.next);
            System.out.print(head.val + " ");
        }
    }


    /**
     * 查找单链表中的倒数第K个结点（k > 0）
     * 最普遍的方法是，先统计单链表中结点的个数，然后再找到第（n-k）个结点。注意链表为空，k为0，k为1，k大于链表中节点个数时的情况
     * 。时间复杂度为O（n）。代码略。 这里主要讲一下另一个思路，这种思路在其他题目中也会有应用。
     * 主要思路就是使用两个指针，先让前面的指针走到正向第k个结点
     * ，这样前后两个指针的距离差是k-1，之后前后两个指针一起向前走，前面的指针走到最后一个结点时，后面指针所指结点就是倒数第k个结点
     */
    public static Node reGetKthNode(Node head, int k) {
        // 这里k的计数是从1开始，若k为0或链表为空返回null
        if (k == 0 || head == null) {
            return null;
        }

        Node q = head; // q在p前面  p--q
        Node p = head; // p在q后面

        // 让q领先p距离k
        while (k > 1 && q != null) {
            q = q.next;
            k--;
        }

        // 当节点数小于k，返回null
        if (k > 1 || q == null) {
            return null;
        }

        // 前后两个指针一起走，直到前面的指针指向最后一个节点
        while (q.next != null) {
            p = p.next;
            q = q.next;
        }

        // 当前面的指针指向最后一个节点时，后面的指针指向倒数k个节点
        return p;
    }


    /**
     * 递归打印出倒数第k位的值
     *
     * @param head
     * @param dist
     */
    static int level = 0;

    public static Node reGetKthNodeRec(Node head, int k) {
        if (head == null || k == 0) {
            return null;
        }
        Node node = reGetKthNodeRec(head.next, k);
        level++;
        if (level == k) {
            node = head;
        }
        return node;
    }

    // 查找单链表的中间结点

    /**
     * 此题可应用于上一题类似的思想。也是设置两个指针，只不过这里是，两个指针同时向前走，前面的指针每次走两步，后面的指针每次走一步，
     * 前面的指针走到最后一个结点时，后面的指针所指结点就是中间结点，即第（n/2+1）个结点。注意链表为空，链表结点个数为1和2的情况。时间复杂度O（n
     */
    public static Node getMiddleNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node q = head;        // p---q
        Node p = head;

        // 前面指针每次走两步，直到指向最后一个结点，后面指针每次走一步
//        while (q.next != null) {
//            p = p.next;
//            q = q.next;
//            if (q.next != null) {
//                q = q.next;
//            }
//        }

        while (q != null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }

        return p;
    }

    /**
     * 已知两个单链表pHead1 和pHead2 各自有序，把它们合并成一个链表依然有序
     * 这个类似归并排序。尤其注意两个链表都为空，和其中一个为空时的情况。只需要O（1）的空间。时间复杂度为O（max(len1, len2)）
     */
    public static Node mergeSortedList(Node head1, Node head2) {
        // 其中一个链表为空的情况，直接返回另一个链表头，O(1)
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node mergeHead = null;
        // 先确定下来mergeHead是在哪里
        if (head1.val < head2.val) {
            mergeHead = head1;
            head1 = head1.next;        // 跳过已经合并了的元素
            mergeHead.next = null;    // 断开mergeHead和后面的联系
        } else {
            mergeHead = head2;
            head2 = head2.next;
            mergeHead.next = null;
        }

        Node mergeCur = mergeHead;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                mergeCur.next = head1;         // 把找到较小的元素合并到merge中
                head1 = head1.next;                 // 跳过已经合并了的元素
                mergeCur = mergeCur.next;     // 找到下一个准备合并的元素
                mergeCur.next = null;             // 断开mergeCur和后面的联系
            } else {
                mergeCur.next = head2;
                head2 = head2.next;
                mergeCur = mergeCur.next;
                mergeCur.next = null;
            }
        }

        // 合并剩余的元素
        if (head1 != null) {
            mergeCur.next = head1;
        } else if (head2 != null) {
            mergeCur.next = head2;
        }

        return mergeHead;
    }

    /**
     * 递归合并两链表（优雅！）
     * <p>
     * 1.h1=(1,3,5)    h2=(1,4,6)*   h2=(1,1,3,4,5,6)
     * 2.h1=(1,3,5)*   h2=(4,6)      h1=(1,3,4,5,6)
     * 3.h1=(3,5)*     h2=(4,6)      h1=(3,4,5,6)
     * 4.h1=(5)        h2=(4,6)*     h2=(4,5,6)
     * 5.h1=(5)*       h2=(6)        h1=(5,6)
     * 6.h1=(null)     h2=(6)        h2=(6)
     */
    public static Node mergeSortedListRec(Node head1, Node head2) {

        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node mergeHead = null;
        if (head1.val < head2.val) {
            mergeHead = head1;
            mergeHead.next = mergeSortedListRec(head1.next, head2);// 连接已解决的子问题
        } else {
            mergeHead = head2;
            mergeHead.next = mergeSortedListRec(head1, head2.next);
        }
        return mergeHead;
    }

    /**
     * 判断一个单链表中是否有环
     * 这里也是用到两个指针。如果一个链表中有环，也就是说用一个指针去遍历，是永远走不到头的。因此，我们可以用两个指针去遍历，一个指针一次走两步
     * ，一个指针一次走一步，如果有环，两个指针肯定会在环中相遇。时间复杂度为O（n）
     */
    public static boolean hasCycle(Node head) {
        Node fast = head; // 快指针每次前进两步
        Node slow = head; // 慢指针每次前进一步

        int length = 0;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            length++;
            if (fast == slow) { // 相遇，存在环
                System.out.println("环的长度为:" + length);
                return true;
                //return fast; //将相遇的那个结点进行返回, 为getCycleLength(Node node)的参数
            }
        }
        return false;
    }


    //方法：有环链表中，获取环的长度。参数node代表的是相遇的那个结点
    public int getCycleLength(Node node) {

        if (head == null) {
            return 0;
        }

        Node current = node;
        int length = 0;

        while (current != null) {
            current = current.next;
            length++;
            if (current == node) {  //当current结点走到原点的时候
                return length;
            }
        }
        return 0; //这里也得 return 0 ; 不然判断不出来是否是从环里跳出的
    }


    // 判断两个单链表是否相交

    /**
     * 如果两个链表相交于某一节点，那么在这个相交节点之后的所有节点都是两个链表所共有的。 也就是说，如果两个链表相交，那么最后一个节点肯定是共有的。
     * 先遍历第一个链表，记住最后一个节点，然后遍历第二个链表， 到最后一个节点时和第一个链表的最后一个节点做比较，如果相同，则相交，
     * 否则不相交。时间复杂度为O(len1+len2)，因为只需要一个额外指针保存最后一个节点地址， 空间复杂度为O(1)
     */
    public static boolean isIntersect(Node head1, Node head2) {

        if (head1 == null || head2 == null) {
            return false;
        }

        Node tail1 = head1;
        // 找到链表1的最后一个节点
        while (tail1.next != null) {
            tail1 = tail1.next;
        }

        Node tail2 = head2;
        // 找到链表2的最后一个节点
        while (tail2.next != null) {
            tail2 = tail2.next;
        }

        return tail1 == tail2;
    }

    /**
     * 求两个单链表相交的第一个节点 对第一个链表遍历，计算长度len1，同时保存最后一个节点的地址。
     * 对第二个链表遍历，计算长度len2，同时检查最后一个节点是否和第一个链表的最后一个节点相同，若不相同，不相交，结束。
     * 两个链表均从头节点开始，假设len1大于len2
     * ，那么将第一个链表先遍历len1-len2个节点，此时两个链表当前节点到第一个相交节点的距离就相等了，然后一起向后遍历，直到两个节点的地址相同。
     * 时间复杂度，O(len1+len2)
     * <p>
     * ----    len2
     * |__________
     * |
     * ---------   len1
     * |---|<- len1-len2
     */
    public static Node getFirstCommonNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        int len1 = 1;
        Node tail1 = head1;
        while (tail1.next != null) {
            tail1 = tail1.next;
            len1++;
        }

        int len2 = 1;
        Node tail2 = head2;
        while (tail2.next != null) {
            tail2 = tail2.next;
            len2++;
        }

        // 不相交直接返回NULL
        if (tail1 != tail2) {
            return null;
        }

        Node n1 = head1;
        Node n2 = head2;

        // 略过较长链表多余的部分
        if (len1 > len2) {
            int k = len1 - len2;
            while (k != 0) {
                n1 = n1.next;
                k--;
            }
        } else {
            int k = len2 - len1;
            while (k != 0) {
                n2 = n2.next;
                k--;
            }
        }

        // 一起向后遍历，直到找到交点
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }

        return n1;
    }

    /**
     * 求进入环中的第一个节点 用快慢指针做（本题用了Crack the Coding Interview的解法，因为更简洁易懂！）
     */
    public static Node getFirstNodeInCycle(Node head) {
        Node slow = head;
        Node fast = head;
        // 1） 找到快慢指针相遇点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // Collision
                break;
            }
        }
        // 错误检查，这是没有环的情况
        if (fast == null || fast.next == null) {//避免上边 null == null 的情况
            return null;
        }
        // 2）现在，相遇点离环的开始处的距离等于链表头到环开始处的距离，
        // 这样，我们把慢指针放在链表头，快指针保持在相遇点，然后
        // 同速度前进，再次相遇点就是环的开始处！
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        // 再次相遇点就是环的开始处
        return fast;
    }

    /**
     * 求进入环中的第一个节点 用HashMap做 一个无环的链表，它每个结点的地址都是不一样的。
     * 但如果有环，指针沿着链表移动，那这个指针最终会指向一个已经出现过的地址 以地址为哈希表的键值，每出现一个地址，就将该键值对应的实值置为true。
     * 那么当某个键值对应的实值已经为true时，说明这个地址之前已经出现过了， 直接返回它就OK了
     */
    public static Node getFirstNodeInCycleHashMap(Node head) {
        HashMap<Node, Boolean> map = new HashMap<Node, Boolean>();
        while (head != null) {
            if (map.get(head) == true) {
                return head; // 这个地址之前已经出现过了，就是环的开始处
            } else {
                map.put(head, true);
                head = head.next;
            }
        }
        return head;
    }

    /**
     * 经测试算的不对不对  略过
     * 让second指针走length步；然后让first指针和second指针同时各走一步，当两个指针相遇时，相遇时的结点就是环的起始点。
     * 方法：获取环的起始点。参数length表示环的长度
     *
     * @param head
     * @param cycleLength 表示环的长度
     * @return
     */
    public Node getFirstNodeInCycleByLength(Node head, int cycleLength) {//经测试算的不对不对  略过
        if (head == null) return null;
        Node first = head;
        Node second = head;
        //先让second指针走length步
        for (int i = 0; i < cycleLength; i++) {
            second = second.next;
        }
        //然后让first指针和second指针同时各走一步
        while (first != null && second != null) {
            first = first.next;
            second = second.next;
            if (first == second) { //如果两个指针相遇了，说明这个结点就是环的起始点
                return first;
            }
        }
        return null;
    }

    /**
     * 给出一单链表头指针head和一节点指针toBeDeleted，O(1)时间复杂度删除节点tBeDeleted
     * 对于删除节点，我们普通的思路就是让该节点的前一个节点指向该节点的下一个节点
     * ，这种情况需要遍历找到该节点的前一个节点，时间复杂度为O(n)。对于链表，
     * 链表中的每个节点结构都是一样的，所以我们可以把该节点的下一个节点的数据复制到该节点
     * ，然后删除下一个节点即可。要注意最后一个节点的情况，这个时候只能用常见的方法来操作，先找到前一个节点，但总体的平均时间复杂度还是O(1)
     */
    public void delete(Node head, Node toDelete) {
        if (toDelete == null) {
            return;
        }
        if (toDelete.next != null) {            // 要删除的是一个中间节点
            toDelete.val = toDelete.next.val;        // 将下一个节点的数据复制到本节点!
            toDelete.next = toDelete.next.next;
        } else {        // 要删除的是最后一个节点！
            if (head == toDelete) {        // 链表中只有一个节点的情况
                head = null;
            } else {
                Node node = head;
                while (node.next != toDelete) {    // 找到倒数第二个节点
                    node = node.next;
                }
                node.next = null;
            }
        }
    }

}
