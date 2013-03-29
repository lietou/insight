package com.dianping.cat.consumer.transaction.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

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
   public boolean onAllDuration(final TransactionType parent, final AllDuration allDuration) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addAllDuration(allDuration);
            }
         });
      } else {
         parent.addAllDuration(allDuration);
      }

      return true;
   }

   @Override
   public boolean onAllDuration(final TransactionName parent, final AllDuration allDuration) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addAllDuration(allDuration);
            }
         });
      } else {
         parent.addAllDuration(allDuration);
      }

      return true;
   }

   @Override
   public boolean onDuration(final TransactionName parent, final Duration duration) {
      parent.addDuration(duration);
      return true;
   }

   @Override
   public boolean onMachine(final TransactionReport parent, final Machine machine) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addMachine(machine);
            }
         });
      } else {
         parent.addMachine(machine);
      }

      return true;
   }

   @Override
   public boolean onName(final TransactionType parent, final TransactionName name) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addName(name);
            }
         });
      } else {
         parent.addName(name);
      }

      return true;
   }

   @Override
   public boolean onRange(final TransactionName parent, final Range range) {
      parent.addRange(range);
      return true;
   }

   @Override
   public boolean onRange2(final TransactionType parent, final Range2 range2) {
      parent.addRange2(range2);
      return true;
   }

   @Override
   public boolean onType(final Machine parent, final TransactionType type) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addType(type);
            }
         });
      } else {
         parent.addType(type);
      }

      return true;
   }
}
