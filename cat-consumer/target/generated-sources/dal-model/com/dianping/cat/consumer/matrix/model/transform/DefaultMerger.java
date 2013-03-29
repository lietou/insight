package com.dianping.cat.consumer.matrix.model.transform;

import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_RATIO;
import java.util.Stack;

import com.dianping.cat.consumer.matrix.model.IVisitor;
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private MatrixReport m_matrixReport;

   public DefaultMerger(MatrixReport matrixReport) {
      m_matrixReport = matrixReport;
   }

   public MatrixReport getMatrixReport() {
      return m_matrixReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeMatrix(Matrix old, Matrix matrix) {
      old.mergeAttributes(matrix);
   }

   protected void mergeMatrixReport(MatrixReport old, MatrixReport matrixReport) {
      old.mergeAttributes(matrixReport);
   }

   protected void mergeRatio(Ratio old, Ratio ratio) {
      old.mergeAttributes(ratio);
   }

   @Override
   public void visitMatrix(Matrix matrix) {
      Object parent = m_objs.peek();
      Matrix old = null;

      if (parent instanceof MatrixReport) {
         MatrixReport matrixReport = (MatrixReport) parent;

         old = matrixReport.findMatrix(matrix.getName());

         if (old == null) {
            old = new Matrix(matrix.getName());
            matrixReport.addMatrix(old);
         }

         mergeMatrix(old, matrix);
      }

      visitMatrixChildren(old, matrix);
   }

   protected void visitMatrixChildren(Matrix old, Matrix matrix) {
      if (old != null) {
         m_objs.push(old);

         for (Ratio ratio : matrix.getRatios().values()) {
            m_tags.push(ENTITY_RATIO);
            visitRatio(ratio);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMatrixReport(MatrixReport matrixReport) {
      m_matrixReport.mergeAttributes(matrixReport);
      visitMatrixReportChildren(m_matrixReport, matrixReport);
   }

   protected void visitMatrixReportChildren(MatrixReport old, MatrixReport matrixReport) {
      if (old != null) {
         m_objs.push(old);

         for (Matrix matrix : matrixReport.getMatrixs().values()) {
            m_tags.push(ENTITY_MATRIX);
            visitMatrix(matrix);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitRatio(Ratio ratio) {
      Object parent = m_objs.peek();
      Ratio old = null;

      if (parent instanceof Matrix) {
         Matrix matrix = (Matrix) parent;

         old = matrix.findRatio(ratio.getType());

         if (old == null) {
            old = new Ratio(ratio.getType());
            matrix.addRatio(old);
         }

         mergeRatio(old, ratio);
      }

      visitRatioChildren(old, ratio);
   }

   protected void visitRatioChildren(Ratio old, Ratio ratio) {
   }
}
