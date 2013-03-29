package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class KvdbCache extends BaseEntity<KvdbCache> {
   private BaseCacheInfo m_baseCacheInfo;

   public KvdbCache() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitKvdbCache(this);
   }

   public BaseCacheInfo getBaseCacheInfo() {
      return m_baseCacheInfo;
   }

   @Override
   public void mergeAttributes(KvdbCache other) {
   }

   public KvdbCache setBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      m_baseCacheInfo = baseCacheInfo;
      return this;
   }

}
