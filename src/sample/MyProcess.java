package sample;

public class MyProcess extends Thread {

    private Ram firstRam = null;
    private Ram secondRam = null;
    private controllerWithThread controller = null;
    private int id;
    private Boolean finished = false;

    public int getMyId(){return this.id;}
    public Boolean isFinished(){return finished;}


    final long SHORT = (500 * 2);
    final long STD = (1000 * 2);

    MyProcess leftNeighbour;
    MyProcess rightNeighbour;

    public MyProcess(controllerWithThread c, int id, Ram ra){
        controller = c;
        this.id = id;
        recieveRam(ra);

        controller.myController.updateProcessView(this);
    }

    @Override
    public void run() {

        int leftone = ((id-1) + controller.n) % controller.n;
        int rightone = ((id+1) + controller.n) % controller.n;

        leftNeighbour = controller.getProcessById(leftone);
        rightNeighbour = controller.getProcessById(rightone);

        while(true){

            while(!finished){
                controller.myController.updateProcessView(this);


                sendRamTo(leftNeighbour.getMyId());
                sendRamTo(rightNeighbour.getMyId());

                try {
                    Thread.sleep(STD);
                } catch (Exception e) {
                }
            }
            finishAction();
        }
    }




    public synchronized Boolean recieveRam(Ram r){
        if(!finished){
            if(controllerWithThread.outputs)
                System.out.println("\treciever=" + this);
            if(firstRam == null){
                if(controllerWithThread.outputs)
                    System.out.println("\t\tfirstRam");


                firstRam = r;
            }
            else{
                secondRam = r;
                finished = true;
                if(controllerWithThread.outputs)
                    System.out.println("\t\tsecondRam");
                //System.out.println(">> fertig : " + this );
            }
            controller.myController.updateProcessView(this);
            return true;
        }
        return false;
    }
    public synchronized void finishAction(){


        if(firstRam != null)
            firstRam.makeDirty();
        if(secondRam != null)
            secondRam.makeDirty();
        finished = false;
        controller.myController.updateProcessView(this);

        try {
            Thread.sleep(SHORT);
        } catch (Exception e) {
        }

        if(rightNeighbour.isFinished() == false)
        {
            sendRamTo(rightNeighbour.getMyId());
            controller.myController.updateProcessView(this);
        }

        else if(leftNeighbour.isFinished() == false)
        {
            sendRamTo(leftNeighbour.getMyId());
        }
        controller.myController.updateProcessView(this);

        try {
            Thread.sleep(SHORT);
        } catch (Exception e) {
        }

    }
    public synchronized void sendRamTo(Integer reciever){

        controller.myController.updateProcessView(this);
        MyProcess p = controller.getProcessById(reciever);
        //System.out.println("sender=" + this);
        if(secondRam != null && secondRam.isDirty()){
            if(controllerWithThread.outputs)
                System.out.println("Ram From: " + this + " to: " + reciever);

            secondRam.clean();
            if(p.recieveRam(secondRam))
                secondRam = null;

        }
        else if(firstRam != null && firstRam.isDirty()){
            if(controllerWithThread.outputs)
                System.out.println("Ram From: " + this + " to: " + reciever);

            firstRam.clean();
            if(p.recieveRam(firstRam))
                firstRam = null;

        }

        controller.myController.updateProcessView(this);

    }
    @Override
    public String toString() {
        return "MyProcess{id=" + this.id + " firstRam=" + firstRam + " secondRam=" + secondRam + " finished=" + finished+"}";
    }
    public String infos(){


        String ret = "";
        if(firstRam == null)
            ret += '0';
        else if(firstRam.isDirty())
            ret+= '1';
        else
            ret+='2';
        ret+= ';';
        if(secondRam == null)
            ret += '0';
        else if(secondRam.isDirty())
            ret+= '1';
        else
            ret+='2';

        ret += ";";
        if(finished)
            ret += '1';
        else
            ret+='0';

        return ret;
    }


}
