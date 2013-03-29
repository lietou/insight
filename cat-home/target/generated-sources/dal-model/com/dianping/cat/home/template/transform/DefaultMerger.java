package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ENTITY_CONNECTION;
import static com.dianping.cat.home.template.Constants.ENTITY_DURATION;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAM;
import java.util.Stack;

import com.dianping.cat.home.template.IVisitor;
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private ThresholdTemplate m_thresholdTemplate;

   public DefaultMerger(ThresholdTemplate thresholdTemplate) {
      m_thresholdTemplate = thresholdTemplate;
   }

   public ThresholdTemplate getThresholdTemplate() {
      return m_thresholdTemplate;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeConnection(Connection old, Connection connection) {
      old.mergeAttributes(connection);
   }

   protected void mergeDuration(Duration old, Duration duration) {
      old.mergeAttributes(duration);
   }

   protected void mergeParam(Param old, Param param) {
      old.mergeAttributes(param);
   }

   protected void mergeThresholdTemplate(ThresholdTemplate old, ThresholdTemplate thresholdTemplate) {
      old.mergeAttributes(thresholdTemplate);
   }

   @Override
   public void visitConnection(Connection connection) {
      Object parent = m_objs.peek();
      Connection old = null;

      if (parent instanceof ThresholdTemplate) {
         ThresholdTemplate thresholdTemplate = (ThresholdTemplate) parent;

         old = thresholdTemplate.getConnection();

         if (old == null) {
            old = new Connection();
            thresholdTemplate.setConnection(old);
         }

         mergeConnection(old, connection);
      }

      visitConnectionChildren(old, connection);
   }

   protected void visitConnectionChildren(Connection old, Connection connection) {
      if (old != null) {
         m_objs.push(old);

         for (Param param : connection.getParams().values()) {
            m_tags.push(ENTITY_PARAM);
            visitParam(param);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitDuration(Duration duration) {
      Object parent = m_objs.peek();
      Duration old = null;

      if (parent instanceof ThresholdTemplate) {
         ThresholdTemplate thresholdTemplate = (ThresholdTemplate) parent;

         old = thresholdTemplate.findDuration(duration.getId());

         if (old == null) {
            old = new Duration(duration.getId());
            thresholdTemplate.addDuration(old);
         }

         mergeDuration(old, duration);
      }

      visitDurationChildren(old, duration);
   }

   protected void visitDurationChildren(Duration old, Duration duration) {
   }

   @Override
   public void visitParam(Param param) {
      Object parent = m_objs.peek();
      Param old = null;

      if (parent instanceof Connection) {
         Connection connection = (Connection) parent;

         old = connection.findParam(param.getType());

         if (old == null) {
            old = new Param(param.getType());
            connection.addParam(old);
         }

         mergeParam(old, param);
      }

      visitParamChildren(old, param);
   }

   protected void visitParamChildren(Param old, Param param) {
   }

   @Override
   public void visitThresholdTemplate(ThresholdTemplate thresholdTemplate) {
      m_thresholdTemplate.mergeAttributes(thresholdTemplate);
      visitThresholdTemplateChildren(m_thresholdTemplate, thresholdTemplate);
   }

   protected void visitThresholdTemplateChildren(ThresholdTemplate old, ThresholdTemplate thresholdTemplate) {
      if (old != null) {
         m_objs.push(old);

         if (thresholdTemplate.getConnection() != null) {
            m_tags.push(ENTITY_CONNECTION);
            visitConnection(thresholdTemplate.getConnection());
            m_tags.pop();
         }

         for (Duration duration : thresholdTemplate.getDurations().values()) {
            m_tags.push(ENTITY_DURATION);
            visitDuration(duration);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
