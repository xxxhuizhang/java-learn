package jucdemo;


public class LambdaDemo {

    @FunctionalInterface
    interface Foo{
        public int add(int x,int y);
        default int div(int x,int y){
            return x/y;
        }
        public static int sub(int x,int y){
            return x-y;
        }
    }


    /**
     * Lambda Express-----> 函数式编程
     * @param args
     * @throws Exception
     *拷贝小括号（），写死右箭头->，落地大括号{...}
     *
     */
    public static void main(String[] args)throws Exception {
        /*Foo foo = new Foo() {
            @Override
            public int add(int x, int y) {
                System.out.println("hellojava190401!!");
                return x+y;
            }
        };*/
        Foo foo = (int x,int y)->{
            System.out.println("hellojava190401!!");
            return x+y;
        };

                System.out.println(foo.add(10, 5));
                System.out.println(foo.div(10, 5));
                System.out.println(Foo.sub(10, 5));
    }
}
