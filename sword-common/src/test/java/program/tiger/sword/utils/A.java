package program.tiger.sword.utils;

public class A {

    private  String str;

    public A() {
    }

    public A(String str) {
        this.str = str;
    }

    public A print(){
        scan:{
            if (str.equals("abcde")){
                System.out.println(str);
            }else {
                break scan;
            }
            return this;
        }
        return new A("12313");
    }

    public static void main(String[] args) {
        A a = new A();
        a.str = "abcde";

        A b = a.print();
        System.out.println(b.str);
    }
}
