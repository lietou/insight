package com.dianping.cat.consumer.state.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

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
   public boolean onMachine(final StateReport parent, final Machine machine) {
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
   public boolean onMessage(final Machine parent, final Message message) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addMessage(message);
            }
         });
      } else {
         parent.addMessage(message);
      }

      return true;
   }

   @Override
   public boolean onProcessDomain(final Machine parent, final ProcessDomain processDomain) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addProcessDomain(processDomain);
            }
         });
      } else {
         parent.addProcessDomain(processDomain);
      }

      return true;
   }
}
