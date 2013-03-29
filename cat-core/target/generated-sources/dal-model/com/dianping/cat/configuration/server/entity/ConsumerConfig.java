package com.dianping.cat.configuration.server.entity;

import com.dianping.cat.configuration.server.BaseEntity;
import com.dianping.cat.configuration.server.IVisitor;

public class ConsumerConfig extends BaseEntity<ConsumerConfig> {
   private LongConfig m_longConfig;

   public ConsumerConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConsumer(this);
   }

   public LongConfig getLongConfig() {
      return m_longConfig;
   }

   @Override
   public void mergeAttributes(ConsumerConfig other) {
   }

   public ConsumerConfig setLongConfig(LongConfig longConfig) {
      m_longConfig = longConfig;
      return this;
   }

}
