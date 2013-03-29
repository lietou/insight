package com.dianping.cat.consumer.matrix.model.transform;

import com.dianping.cat.consumer.matrix.model.IVisitor;
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitMatrix(Matrix matrix) {
      for (Ratio ratio : matrix.getRatios().values()) {
         visitRatio(ratio);
      }
   }

   @Override
   public void visitMatrixReport(MatrixReport matrixReport) {
      for (Matrix matrix : matrixReport.getMatrixs().values()) {
         visitMatrix(matrix);
      }
   }

   @Override
   public void visitRatio(Ratio ratio) {
   }
}
