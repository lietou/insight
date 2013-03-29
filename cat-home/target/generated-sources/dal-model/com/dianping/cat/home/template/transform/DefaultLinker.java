package com.dianping.cat.home.template.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

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
   public boolean onConnection(final ThresholdTemplate parent, final Connection connection) {
      parent.setConnection(connection);
      return true;
   }

   @Override
   public boolean onDuration(final ThresholdTemplate parent, final Duration duration) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addDuration(duration);
            }
         });
      } else {
         parent.addDuration(duration);
      }

      return true;
   }

   @Override
   public boolean onParam(final Connection parent, final Param param) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addParam(param);
            }
         });
      } else {
         parent.addParam(param);
      }

      return true;
   }
}
