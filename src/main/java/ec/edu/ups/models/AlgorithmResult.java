package ec.edu.ups.models;

public class AlgorithmResult {
    private String algorithmName;
    private int pathSize;
    private long timeNs;

    public AlgorithmResult(String algorithmName, int pathSize, long timeNs) {
        this.algorithmName = algorithmName;
        this.pathSize = pathSize;
        this.timeNs = timeNs;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getPathSize() {
        return pathSize;
    }

    public long getTimeNs() {
        return timeNs;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setPathSize(int pathSize) {
        this.pathSize = pathSize;
    }

    public void setTimeNs(long timeNs) {
        this.timeNs = timeNs;
    }

    @Override
    public String toString() {
        return algorithmName + "," + pathSize + "," + timeNs;
    }
}