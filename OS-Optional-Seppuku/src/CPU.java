public class CPU {

    public  static void main(String[] args){
        final int PAGE_SIZE = 4;
        OS os = new OS(PAGE_SIZE);

        PCB pcb = null;
        Pair pcVal = null;

        while (os.terminated()){

            if (pcb == null){ // if we have nothing to process, get a process.
                pcb = os.scheduler();
                pcVal = pcb.getPcVal();
            }


            //get data from memory
            int data = os.memory.access(pcVal);

            //check to see what the data is
            if (data == 1) new BubbleSort().run();

            else{
                os.deviceDriver(pcVal);
                os.savePCB(State.Waiting);
                pcb = null;
            }

            //check for interruption from DMA
            if (!os.memory.isDMAFlag()) {
                os.savePCB(State.Ready);
                pcb = null;
                //call os interrupt handler
                os.interruptHandler();
            }else{
                if (data == 1) os.incrementPcVal();
                else pcb = null;
            }


        }

        System.out.println("finish");

        os.memory.killDMA();

        System.exit(0);
    }
}
