package com.dianping.cat.consumer.ip.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

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
   public boolean onIp(final Period parent, final Ip ip) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addIp(ip);
            }
         });
      } else {
         parent.addIp(ip);
      }

      return true;
   }

   @Override
   public boolean onPeriod(final IpReport parent, final Period period) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addPeriod(period);
            }
         });
      } else {
         parent.addPeriod(period);
      }

      return true;
   }
}
