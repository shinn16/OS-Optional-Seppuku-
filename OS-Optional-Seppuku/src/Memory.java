import java.util.Arrays;

public class Memory {

    private final int PAGE_SIZE = 4;
    private int pageNumber = 0;

    private int[] memory1 = new int[128]; //if each page holds 4 items then one pages corresponds to 4 places in memory

    public Memory(){

    }

    public int getPage(){
        return pageNumber ++;
    }

    public void write(Pair addressVector, int data){ // takes logical address and writes data to physical address
        int pageNumber = (int)addressVector.getKey();
        int offset = (int)addressVector.getValue();
        int physicalAddress = pageNumber * PAGE_SIZE + offset;
        while (physicalAddress > memory1.length) memory1 = Arrays.copyOf(memory1, memory1.length * 2); //just in case
        memory1[physicalAddress] = data;
    }

    public int access(Pair addressVector){  // access the data in the physical memory at an address.
        int pageNumber = (int)addressVector.getKey();
        int offset = (int)addressVector.getValue();
        int physicalAddress = pageNumber * PAGE_SIZE + offset;
        return memory1[physicalAddress];
    }
}
