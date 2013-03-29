package com.dianping.cat.consumer.top.model.transform;

import static com.dianping.cat.consumer.top.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_SEGMENT;
import java.util.Stack;

import com.dianping.cat.consumer.top.model.IVisitor;
import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private TopReport m_topReport;

   public DefaultMerger(TopReport topReport) {
      m_topReport = topReport;
   }

   public TopReport getTopReport() {
      return m_topReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeDomain(Domain old, Domain domain) {
      old.mergeAttributes(domain);
   }

   protected void mergeSegment(Segment old, Segment segment) {
      old.mergeAttributes(segment);
   }

   protected void mergeTopReport(TopReport old, TopReport topReport) {
      old.mergeAttributes(topReport);
   }

   @Override
   public void visitDomain(Domain domain) {
      Object parent = m_objs.peek();
      Domain old = null;

      if (parent instanceof TopReport) {
         TopReport topReport = (TopReport) parent;

         old = topReport.findDomain(domain.getName());

         if (old == null) {
            old = new Domain(domain.getName());
            topReport.addDomain(old);
         }

         mergeDomain(old, domain);
      }

      visitDomainChildren(old, domain);
   }

   protected void visitDomainChildren(Domain old, Domain domain) {
      if (old != null) {
         m_objs.push(old);

         for (Segment segment : domain.getSegments().values()) {
            m_tags.push(ENTITY_SEGMENT);
            visitSegment(segment);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitSegment(Segment segment) {
      Object parent = m_objs.peek();
      Segment old = null;

      if (parent instanceof Domain) {
         Domain domain = (Domain) parent;

         old = domain.findSegment(segment.getId());

         if (old == null) {
            old = new Segment(segment.getId());
            domain.addSegment(old);
         }

         mergeSegment(old, segment);
      }

      visitSegmentChildren(old, segment);
   }

   protected void visitSegmentChildren(Segment old, Segment segment) {
   }

   @Override
   public void visitTopReport(TopReport topReport) {
      m_topReport.mergeAttributes(topReport);
      visitTopReportChildren(m_topReport, topReport);
   }

   protected void visitTopReportChildren(TopReport old, TopReport topReport) {
      if (old != null) {
         m_objs.push(old);

         for (Domain domain : topReport.getDomains().values()) {
            m_tags.push(ENTITY_DOMAIN);
            visitDomain(domain);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
