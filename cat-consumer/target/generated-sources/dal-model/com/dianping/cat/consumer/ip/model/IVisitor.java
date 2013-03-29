package com.dianping.cat.consumer.ip.model;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public interface IVisitor {

   public void visitIp(Ip ip);

   public void visitIpReport(IpReport ipReport);

   public void visitPeriod(Period period);
}
