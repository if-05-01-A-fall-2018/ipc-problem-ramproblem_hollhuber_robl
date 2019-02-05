package sample;

public class ThreadController extends Thread {
    public static Boolean outputs = false;
    public final int n = 5;//amount of "dining philosophers"
    MyProcess[] processes = new MyProcess[n];
    Controller myController;

    @Override
    public void run() {
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        for (int i = 0; i < n; i++) {
            processes[i] = new MyProcess(this,i, new Ram(i));
            processes[i].setDaemon(true);
            myController.hideInitRam(i);

            myController.updateProcessView(processes[i]);
        }
        for (int i = 0; i < n; i++) {
            processes[i].start();
        }
    }


    public ThreadController(Controller con){
        this.myController = con;
    }

    public MyProcess getProcessById(int id){
        if(id < n){
            return processes[id];
        }
        return null;

    }

}
