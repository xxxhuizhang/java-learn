package thread.cas;

import java.util.concurrent.atomic.AtomicReference;

class User {
    String userName;
    int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
        //修改失败
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());

    }
}
