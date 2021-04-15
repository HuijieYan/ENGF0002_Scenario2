public class MatrixModel {
    private MatrixCalculator calculator = new MatrixCalculator();

    public Matrix addMatrices(Matrix matrix1, Matrix matrix2){
        return this.calculator.addMatrices(matrix1, matrix2);
    }

    public Matrix subtractMatrices(Matrix matrix1, Matrix matrix2){
        return this.calculator.subtractMatrices(matrix1, matrix2);
    }

    public Matrix multiplyMatrices(Matrix matrix1, Matrix matrix2){
        return this.calculator.multiplyMatrices(matrix1, matrix2);
    }

    public Matrix findTranspose(Matrix matrix){
        return this.calculator.findTranspose(matrix);
    }

    public Matrix findDeterminant(Matrix matrix){
        return this.calculator.findDeterminant(matrix);
    }

    public boolean hasDeterminant(Matrix matrix){
        return this.calculator.findDeterminant(matrix) != null;
    }
}