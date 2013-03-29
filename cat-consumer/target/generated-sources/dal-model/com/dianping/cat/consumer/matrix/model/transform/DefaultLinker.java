package com.dianping.cat.consumer.matrix.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultLinker implements ILinker {
   private boolean m_deferrable;

   private List<Runnable> m_deferedJobs = new ArrayList<Runnable>();

   public DefaultLinker(boolean deferrable) {
      m_deferrable = deferrable;
   }

   public void finish() {
      for (Runnable job : m_deferedJobs) {
         job.run();
      }
   }

   @Override
   public boolean onMatrix(final MatrixReport parent, final Matrix matrix) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addMatrix(matrix);
            }
         });
      } else {
         parent.addMatrix(matrix);
      }

      return true;
   }

   @Override
   public boolean onRatio(final Matrix parent, final Ratio ratio) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addRatio(ratio);
            }
         });
      } else {
         parent.addRatio(ratio);
      }

      return true;
   }
}
