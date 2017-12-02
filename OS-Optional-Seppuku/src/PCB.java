public class PCB {

    private int id, arrivalOrder;
    private  int[] pages;
    private long arrivalTime, endTime, firstIO;
    private State state;
    private Pair pcVal;

    public PCB(int id, State state, Pair pcVal, int arrivalOrder, int[] pages){
        this.id = id;
        this.arrivalTime = System.currentTimeMillis();
        this.state = state;
        this.pcVal = pcVal;
        this.arrivalOrder = arrivalOrder;
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public Pair getPcVal() {
        return pcVal;
    }

    public void setPcVal(Pair pcVal) {
        this.pcVal = pcVal;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setFirstIO() {
        this.firstIO = System.currentTimeMillis();
    }

    public long getFirstIO() {
        return firstIO;
    }

    public State getState() {
        return state;
    }

    public void setState(State state){
        this.state = state;
    }

    public void setEndTime() {
        this.endTime = System.currentTimeMillis();
    }

    //To do: PCB data structure of a process
    //for example: Process_id, Arrive_time, state,
    //PositionOfNextInstructionToExecute(PC value)
    //and so on



}
