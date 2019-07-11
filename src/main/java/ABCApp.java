/**
 * Created by DIO
 */
public class ABCApp {
    public static void main(String[] args) {
        ABC abc = new ABC();
        Thread th1 = new Thread() {
            @Override
            public void run() {
                while (abc.getCounter()<5){
                    abc.printA();
                }
            }
        };
        Thread th2 = new Thread() {
            @Override
            public void run() {
                while (abc.getCounter()<5){
                    abc.printB();
                }
            }
        };
        Thread th3 = new Thread() {
            @Override
            public void run() {
                while (abc.getCounter()<5){
                    abc.printC();
                }
            }
        };
        th1.start();
        th2.start();
        th3.start();
    }
}

class ABC{
    private Object lock = new Object();
    private int counter=1;
    private enum Label{A,B,C}

    Label label = Label.A;

    public int getCounter() {
        return counter;
    }

    public synchronized void printA(){
            while(label==Label.A){
                System.out.print(label);
                label=Label.B;
            }
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAll();
            }


    public synchronized void printB(){

            while(label==Label.B){
                System.out.print(label);
                label=Label.C;
                notifyAll();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }
    public synchronized void printC(){

            while(label==Label.C){
                System.out.print(label);
                label=Label.A;
                counter++;
                notifyAll();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }

}