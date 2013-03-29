package com.dianping.cat.consumer.cross.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

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
   public boolean onLocal(final CrossReport parent, final Local local) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addLocal(local);
            }
         });
      } else {
         parent.addLocal(local);
      }

      return true;
   }

   @Override
   public boolean onName(final Type parent, final Name name) {
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
   public boolean onRemote(final Local parent, final Remote remote) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addRemote(remote);
            }
         });
      } else {
         parent.addRemote(remote);
      }

      return true;
   }

   @Override
   public boolean onType(final Remote parent, final Type type) {
      parent.setType(type);
      return true;
   }
}
