package htms.common.constants;

public enum TraineeFileCellIndex {
    CODE(0), NAME(1), EMAIL(2), PHONE_NUMBER(3);

    private final int value;

    TraineeFileCellIndex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
