package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class WebCache extends BaseEntity<WebCache> {
   private BaseCacheInfo m_baseCacheInfo;

   public WebCache() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitWebCache(this);
   }

   public BaseCacheInfo getBaseCacheInfo() {
      return m_baseCacheInfo;
   }

   @Override
   public void mergeAttributes(WebCache other) {
   }

   public WebCache setBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      m_baseCacheInfo = baseCacheInfo;
      return this;
   }

}
