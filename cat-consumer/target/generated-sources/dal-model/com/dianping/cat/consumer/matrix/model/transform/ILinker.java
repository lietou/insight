package com.dianping.cat.consumer.matrix.model.transform;

import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public interface ILinker {

   public boolean onMatrix(MatrixReport parent, Matrix matrix);

   public boolean onRatio(Matrix parent, Ratio ratio);
}
