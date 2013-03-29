package com.dianping.cat.consumer.sql.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

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
   public boolean onDatabase(final SqlReport parent, final Database database) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addDatabase(database);
            }
         });
      } else {
         parent.addDatabase(database);
      }

      return true;
   }

   @Override
   public boolean onMethod(final Table parent, final Method method) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addMethod(method);
            }
         });
      } else {
         parent.addMethod(method);
      }

      return true;
   }

   @Override
   public boolean onTable(final Database parent, final Table table) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addTable(table);
            }
         });
      } else {
         parent.addTable(table);
      }

      return true;
   }
}
