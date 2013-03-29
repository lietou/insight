package com.dianping.cat.consumer.ip.model.transform;

import com.dianping.cat.consumer.ip.model.IVisitor;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitIp(Ip ip) {
   }

   @Override
   public void visitIpReport(IpReport ipReport) {
      for (Period period : ipReport.getPeriods().values()) {
         visitPeriod(period);
      }
   }

   @Override
   public void visitPeriod(Period period) {
      for (Ip ip : period.getIps().values()) {
         visitIp(ip);
      }
   }
}
