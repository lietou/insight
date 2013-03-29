package com.dianping.cat.configuration.server.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

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
   public boolean onConsole(final ServerConfig parent, final ConsoleConfig console) {
      parent.setConsole(console);
      return true;
   }

   @Override
   public boolean onConsumer(final ServerConfig parent, final ConsumerConfig consumer) {
      parent.setConsumer(consumer);
      return true;
   }

   @Override
   public boolean onDomain(final LongConfig parent, final Domain domain) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addDomain(domain);
            }
         });
      } else {
         parent.addDomain(domain);
      }

      return true;
   }

   @Override
   public boolean onHdfs(final StorageConfig parent, final HdfsConfig hdfs) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addHdfs(hdfs);
            }
         });
      } else {
         parent.addHdfs(hdfs);
      }

      return true;
   }

   @Override
   public boolean onLongConfig(final ConsumerConfig parent, final LongConfig longConfig) {
      parent.setLongConfig(longConfig);
      return true;
   }

   @Override
   public boolean onProperty(final StorageConfig parent, final Property property) {
      if (m_deferrable) {
         m_deferedJobs.add(new Runnable() {
            @Override
            public void run() {
               parent.addProperty(property);
            }
         });
      } else {
         parent.addProperty(property);
      }

      return true;
   }

   @Override
   public boolean onStorage(final ServerConfig parent, final StorageConfig storage) {
      parent.setStorage(storage);
      return true;
   }
}
