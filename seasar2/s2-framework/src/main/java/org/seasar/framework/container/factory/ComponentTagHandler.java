/*
 * Copyright 2004-2015 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.framework.container.factory;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.assembler.AutoBindingDefFactory;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * diconファイルの<code>component</code>要素を解釈するためのクラスです。
 * 
 * @author higa
 */
public class ComponentTagHandler extends AbstractTagHandler {
    private static final long serialVersionUID = -8182227769800177833L;

    public void start(TagHandlerContext context, Attributes attributes) {
        AnnotationHandler annoHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        String className = attributes.getValue("class");
        Class componentClass = null;
        if (className != null) {
            componentClass = ClassUtil.forName(className);
        }
        String name = attributes.getValue("name");
        ComponentDef componentDef = null;
        if (componentClass != null) {
            componentDef = annoHandler.createComponentDef(componentClass, null);
            if (name != null) {
                componentDef.setComponentName(name);
            }
            annoHandler.appendDI(componentDef);
        } else {
            componentDef = createComponentDef(componentClass, name);
        }
        String instanceMode = attributes.getValue("instance");
        if (instanceMode != null) {
            componentDef.setInstanceDef(InstanceDefFactory
                    .getInstanceDef(instanceMode));
        }
        String autoBindingName = attributes.getValue("autoBinding");
        if (autoBindingName != null) {
            componentDef.setAutoBindingDef(AutoBindingDefFactory
                    .getAutoBindingDef(autoBindingName));
        }
        String externalBindingStr = attributes.getValue("externalBinding");
        if (externalBindingStr != null) {
            componentDef.setExternalBinding(Boolean.valueOf(externalBindingStr)
                    .booleanValue());
        }
        context.push(componentDef);
    }

    public void end(TagHandlerContext context, String body) {
        ComponentDef componentDef = (ComponentDef) context.pop();
        AnnotationHandler annoHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        annoHandler.appendInitMethod(componentDef);
        annoHandler.appendDestroyMethod(componentDef);
        annoHandler.appendAspect(componentDef);
        annoHandler.appendInterType(componentDef);
        String expression = null;
        if (body != null) {
            expression = body.trim();
            if (!StringUtil.isEmpty(expression)) {
                componentDef
                        .setExpression(createExpression(context, expression));
            } else {
                expression = null;
            }
        }
        if (componentDef.getComponentClass() == null
                && !InstanceDefFactory.OUTER.equals(componentDef
                        .getInstanceDef()) && expression == null) {
            throw new TagAttributeNotDefinedRuntimeException("component",
                    "class");
        }
        if (context.peek() instanceof S2Container) {
            S2Container container = (S2Container) context.peek();
            container.register(componentDef);
        } else {
            ArgDef argDef = (ArgDef) context.peek();
            argDef.setChildComponentDef(componentDef);
        }
    }

    /**
     * コンポーネント定義を作成します。
     * 
     * @param componentClass
     * @param name
     * @return コンポーネント定義
     */
    protected ComponentDef createComponentDef(Class componentClass, String name) {
        return new ComponentDefImpl(componentClass, name);
    }
}
