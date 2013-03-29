package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIOD;
import java.util.Stack;

import com.dianping.cat.consumer.ip.model.IVisitor;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private IpReport m_ipReport;

   public DefaultMerger(IpReport ipReport) {
      m_ipReport = ipReport;
   }

   public IpReport getIpReport() {
      return m_ipReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeIp(Ip old, Ip ip) {
      old.mergeAttributes(ip);
   }

   protected void mergeIpReport(IpReport old, IpReport ipReport) {
      old.mergeAttributes(ipReport);
   }

   protected void mergePeriod(Period old, Period period) {
      old.mergeAttributes(period);
   }

   @Override
   public void visitIp(Ip ip) {
      Object parent = m_objs.peek();
      Ip old = null;

      if (parent instanceof Period) {
         Period period = (Period) parent;

         old = period.findIp(ip.getAddress());

         if (old == null) {
            old = new Ip(ip.getAddress());
            period.addIp(old);
         }

         mergeIp(old, ip);
      }

      visitIpChildren(old, ip);
   }

   protected void visitIpChildren(Ip old, Ip ip) {
   }

   @Override
   public void visitIpReport(IpReport ipReport) {
      m_ipReport.mergeAttributes(ipReport);
      visitIpReportChildren(m_ipReport, ipReport);
   }

   protected void visitIpReportChildren(IpReport old, IpReport ipReport) {
      if (old != null) {
         m_objs.push(old);

         for (Period period : ipReport.getPeriods().values()) {
            m_tags.push(ENTITY_PERIOD);
            visitPeriod(period);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitPeriod(Period period) {
      Object parent = m_objs.peek();
      Period old = null;

      if (parent instanceof IpReport) {
         IpReport ipReport = (IpReport) parent;

         old = ipReport.findPeriod(period.getMinute());

         if (old == null) {
            old = new Period(period.getMinute());
            ipReport.addPeriod(old);
         }

         mergePeriod(old, period);
      }

      visitPeriodChildren(old, period);
   }

   protected void visitPeriodChildren(Period old, Period period) {
      if (old != null) {
         m_objs.push(old);

         for (Ip ip : period.getIps().values()) {
            m_tags.push(ENTITY_IP);
            visitIp(ip);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
