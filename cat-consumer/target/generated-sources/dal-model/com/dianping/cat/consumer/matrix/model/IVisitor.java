package com.dianping.cat.consumer.matrix.model;

import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public interface IVisitor {

   public void visitMatrix(Matrix matrix);

   public void visitMatrixReport(MatrixReport matrixReport);

   public void visitRatio(Ratio ratio);
}
