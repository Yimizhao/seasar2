/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.extension.jdbc.gen.command;

import org.junit.After;
import org.junit.Test;
import org.seasar.extension.jdbc.gen.GenerationContext;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import static org.junit.Assert.*;

/**
 * @author taedium
 * 
 */
public class GenerateEntityCommandTest {

    @After
    public void tearDown() throws Exception {
        SingletonS2ContainerFactory.destroy();
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testValidate() throws Exception {
        GenerateEntityCommand command = new GenerateEntityCommand();
        command.setConfigPath("s2jdbc-gen-core-test.dicon");
        command.validate();
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testFactoryMethod() throws Exception {
        GenerateEntityCommand command = new GenerateEntityCommand();
        command.setConfigPath("s2jdbc-gen-core-test.dicon");
        command.init();
        assertNotNull(command.createEntityDescFactory());
        assertNotNull(command.createEntityModelFactory());
        assertNotNull(command.createGenerator());
        assertNotNull(command.createSchemaReader());
        GenerationContext context = command.createGenerationContext(
                new Object(), "aaa.bbb.Hoge", "ccc.ftl", true);
        assertNotNull(context);
    }

}
