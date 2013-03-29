package com.dianping.cat.consumer.matrix.model.transform;

import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public interface IParser<T> {
   public MatrixReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForMatrix(IMaker<T> maker, ILinker linker, Matrix parent, T node);

   public void parseForRatio(IMaker<T> maker, ILinker linker, Ratio parent, T node);
}
