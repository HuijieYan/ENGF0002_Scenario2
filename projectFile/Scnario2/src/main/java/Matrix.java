import java.util.ArrayList;
import java.util.Random;

public class Matrix {
    private ArrayList<ArrayList<Float>> matrix;
    private int rowNumber;
    private int columnNumber;

    public Matrix(int rowNumber, int columnNumber){
        if(rowNumber <= 0 || columnNumber <= 0){
            setErrorMatrix();
            return;
        }

        this.matrix = new ArrayList<>();
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;

        Random random = new Random();
        for(int rowIndex = 0; rowIndex < rowNumber; rowIndex++){
            ArrayList<Float> column = new ArrayList<>();
            for(int columnIndex = 0; columnIndex < columnNumber; columnIndex++){
                column.add((float) random.nextInt(10));
            }
            this.matrix.add(column);
        }
    }

    public Matrix(ArrayList<ArrayList<Float>> matrix, int rowNumber, int columnNumber){
        if(rowNumber <= 0 || columnNumber <= 0 || matrix.size() != rowNumber){
            setErrorMatrix();
            return;
        }

        for(ArrayList<Float> row : matrix){
            if(row.size() != columnNumber){
                setErrorMatrix();
                return;
            }
        }

        this.matrix = matrix;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    private void setErrorMatrix(){
        this.rowNumber = -1;
        this.columnNumber = -1;
        this.matrix = null;
    }

    public int getRowNumber(){
        return this.rowNumber;
    }

    public int getColumnNumber(){
        return this.columnNumber;
    }

    public ArrayList<Float> getRow(int rowIndex){
        if(rowIndex >= this.rowNumber || rowIndex < 0){
            return null;
        }

        return new ArrayList<>(this.matrix.get(rowIndex));
    }

    public ArrayList<Float> getColumn(int columnIndex){
        if(columnIndex >= this.columnNumber || columnIndex < 0){
            return null;
        }

        ArrayList<Float> column = new ArrayList<>();
        for(ArrayList<Float> row : this.matrix){
            column.add(row.get(columnIndex));
        }
        return column;
    }

    public ArrayList<ArrayList<Float>> getMatrix(){
        if(this.matrix == null){
            return null;
        }
        return new ArrayList<>(this.matrix);
    }
}