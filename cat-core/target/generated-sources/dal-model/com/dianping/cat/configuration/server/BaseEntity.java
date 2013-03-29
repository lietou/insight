package com.dianping.cat.configuration.server;

import java.util.Formattable;
import java.util.Formatter;

import com.dianping.cat.configuration.server.transform.DefaultXmlBuilder;

public abstract class BaseEntity<T> implements IEntity<T>, Formattable {

   public static final String XML = "%1s";
   
   public static final String XML_COMPACT = "%1.0s";
   
   protected void assertAttributeEquals(Object instance, String entityName, String name, Object expectedValue, Object actualValue) {
      if (expectedValue == null && actualValue != null || expectedValue != null && !expectedValue.equals(actualValue)) {
         throw new IllegalArgumentException(String.format("Mismatched entity(%s) found! Same %s attribute is expected! %s: %s.", entityName, name, entityName, instance));
      }
   }

   @Override
   public void formatTo(Formatter formatter, int flags, int width, int precision) {
      boolean compact = (precision == 0);
      DefaultXmlBuilder builder = new DefaultXmlBuilder(compact);

      formatter.format(builder.buildXml(this));
   }

   @Override
   public String toString() {
      return new DefaultXmlBuilder().buildXml(this);
   }
}
