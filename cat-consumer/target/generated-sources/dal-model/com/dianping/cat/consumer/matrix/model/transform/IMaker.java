package com.dianping.cat.consumer.matrix.model.transform;

import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public interface IMaker<T> {

   public String buildDomain(T node);

   public Matrix buildMatrix(T node);

   public MatrixReport buildMatrixReport(T node);

   public Ratio buildRatio(T node);
}
