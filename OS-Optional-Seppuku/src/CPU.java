public class CPU {

    public  static void main(String[] args){
        final int PAGE_SIZE = 4;
        OS os = new OS(PAGE_SIZE);

        while (true){
            Pair pcVal;

            pcVal = os.scheduler();

            //get data from memory
            int data = os.memory.access(pcVal);

            //remember old pc val
            Pair pcValOld = pcVal;

            //check offset and increment
            if (pcVal.offset() >= PAGE_SIZE){

                pcVal.setOffset(0);
            }
            else pcVal.setOffset(pcVal.offset() + 1);

            //check to see what the data is
            if (data == 1){
                new BubbleSort().run();
            }
            else{
                os.deviceDriver(pcValOld);
                os.savePCB(pcVal, State.Waiting);
            }

            //check for interruption from DMA
            if (os.memory.isDMAFlag()){
                os.savePCB(pcVal, State.Ready);
                //call os interrupt handler
                os.interruptHandler();
            }


        }
    }
}
