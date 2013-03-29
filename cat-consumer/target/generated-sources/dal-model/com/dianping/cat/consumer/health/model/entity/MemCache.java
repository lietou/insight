package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class MemCache extends BaseEntity<MemCache> {
   private BaseCacheInfo m_baseCacheInfo;

   public MemCache() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMemCache(this);
   }

   public BaseCacheInfo getBaseCacheInfo() {
      return m_baseCacheInfo;
   }

   @Override
   public void mergeAttributes(MemCache other) {
   }

   public MemCache setBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      m_baseCacheInfo = baseCacheInfo;
      return this;
   }

}
