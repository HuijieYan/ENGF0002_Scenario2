import java.util.ArrayList;

public class MatrixCalculator {
    public Matrix addMatrices(Matrix matrix1, Matrix matrix2){
        if(matrix1.getRowNumber() != matrix2.getRowNumber() || matrix1.getColumnNumber() != matrix2.getColumnNumber()){
            return null;
        }else if(matrix1.getMatrix() == null || matrix2.getMatrix() == null){
            return null;
        }

        int rowNumber = matrix1.getRowNumber();
        int columnNumber = matrix1.getColumnNumber();
        ArrayList<ArrayList<Float>> result = new ArrayList<>();

        for(int rowIndex = 0; rowIndex < rowNumber; rowIndex++){
            ArrayList<Float> row1 = matrix1.getRow(rowIndex);
            ArrayList<Float> row2 = matrix2.getRow(rowIndex);
            ArrayList<Float> row = new ArrayList<>();
            for(int columnIndex = 0; columnIndex < columnNumber; columnIndex++){
                row.add(row1.get(columnIndex) + row2.get(columnIndex));
            }
            result.add(row);
        }

        return new Matrix(result, rowNumber, columnNumber);
    }

    public Matrix subtractMatrices(Matrix matrix1, Matrix matrix2){
        if(matrix1.getRowNumber() != matrix2.getRowNumber() || matrix1.getColumnNumber() != matrix2.getColumnNumber()){
            return null;
        }else if(matrix1.getMatrix() == null || matrix2.getMatrix() == null){
            return null;
        }

        int rowNumber = matrix1.getRowNumber();
        int columnNumber = matrix1.getColumnNumber();
        ArrayList<ArrayList<Float>> result = new ArrayList<>();

        for(int rowIndex = 0; rowIndex < rowNumber; rowIndex++){
            ArrayList<Float> row1 = matrix1.getRow(rowIndex);
            ArrayList<Float> row2 = matrix2.getRow(rowIndex);
            ArrayList<Float> row = new ArrayList<>();
            for(int columnIndex = 0; columnIndex < columnNumber; columnIndex++){
                row.add(row1.get(columnIndex) - row2.get(columnIndex));
            }
            result.add(row);
        }

        return new Matrix(result, rowNumber, columnNumber);
    }

    public Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2){
        if(matrix1.getColumnNumber() != matrix2.getRowNumber()){
            return null;
        }else if(matrix1.getMatrix() == null || matrix2.getMatrix() == null){
            return null;
        }

        int rowNumber = matrix1.getRowNumber();
        int columnNumber = matrix2.getColumnNumber();
        int multiplyNumber = matrix1.getColumnNumber();
        ArrayList<ArrayList<Float>> result = new ArrayList<>();

        for(int rowIndex = 0; rowIndex < rowNumber; rowIndex++){
            ArrayList<Float> resultRow = new ArrayList<>();
            ArrayList<Float> row = matrix1.getRow(rowIndex);
            for(int columnIndex = 0; columnIndex < columnNumber; columnIndex++){
                ArrayList<Float> column = matrix2.getColumn(columnIndex);
                float sum = 0f;
                for(int index = 0; index < multiplyNumber; index++){
                    sum += row.get(index) * column.get(index);
                }
                resultRow.add(sum);
            }
            result.add(resultRow);
        }

        return new Matrix(result, rowNumber, columnNumber);
    }

    public Matrix findDeterminant(Matrix matrix){
        if(matrix.getMatrix() == null || matrix.getRowNumber() != matrix.getColumnNumber()){
            return null;
        }

        float[][] data = new float[matrix.getRowNumber()][matrix.getColumnNumber()];
        for(int rowIndex = 0; rowIndex < matrix.getRowNumber(); rowIndex++){
            ArrayList<Float> row = matrix.getRow(rowIndex);
            for(int columnIndex = 0; columnIndex < matrix.getColumnNumber(); columnIndex++){
                data[rowIndex][columnIndex] = row.get(columnIndex);
            }
        }

        int k;
        // swapTemp is the temporary row swapping variable
        float[] swapTemp;

        for(int i = 0; i < data.length; i++) {
            k = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j][i] > data[k][i]) {
                    k = j;
                }
            }

            if (data[k][i] == 0) {
                return null;
                // Contain free column
            }

            // Swap the rows i and k
            swapTemp = data[i];
            data[i] = data[k];
            data[k] = swapTemp;

            // Iterate over all subsequent rows
            for (int j = 0; j < data.length; j++) {
                // Reduce row by row
                if (i != j && !reduceRow(data[i], data[j], i)) {
                    return null;
                }
            }
        }

        float result = 1;
        for(int index = 0; index < data.length; index++){
            result *= data[index][index];
        }

        ArrayList<ArrayList<Float>> resultMatrix = new ArrayList<>();
        ArrayList<Float> resultData = new ArrayList<>();
        resultData.add(result);
        resultMatrix.add(resultData);
        return new Matrix(resultMatrix, 1, 1);
    }

    private boolean reduceRow(float[] r1, float[] r2, int pos) {
        if(r1.length != r2.length) return false;

        double factor = r2[pos] / r1[pos];
        for(int i = pos; i < r1.length; i++) {
            r2[i] -= factor * r1[i];
        }
        return true;
    }

    public Matrix findTranspose(Matrix matrix){
        int rowNumber = matrix.getRowNumber();
        int columnNumber = matrix.getColumnNumber();
        ArrayList<ArrayList<Float>> data = new ArrayList<>();
        for(int columnIndex = 0; columnIndex < columnNumber; columnIndex++){
            data.add(matrix.getColumn(columnIndex));
        }
        return new Matrix(data, columnNumber, rowNumber);
    }

}