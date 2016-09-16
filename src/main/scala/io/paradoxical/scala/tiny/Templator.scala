//
// Copyright (c) 2011-2016 by Curalate, Inc.
//

package io.paradoxical.scala.tiny

import java.io.StringWriter
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants
import collection.JavaConversions._
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader


object Templator {
  def apply(templateName: String, ctx: Map[String, String]): String = {
    apply(new VelocityContext(ctx), templateName)
  }

  def apply(context: VelocityContext, templateName: String) = {
    val engine = new VelocityEngine()

    engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath")

    engine.setProperty("classpath.resource.loader.class", classOf[ClasspathResourceLoader].getName())

    engine.init()

    val w = new StringWriter()

    engine.getTemplate(templateName).merge(context, w)

    w.toString.trim
  }
}
