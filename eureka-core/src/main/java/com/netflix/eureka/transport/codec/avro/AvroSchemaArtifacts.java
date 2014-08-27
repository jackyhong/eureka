/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.eureka.transport.codec.avro;

import java.util.ArrayList;
import java.util.List;

import com.netflix.eureka.transport.Acknowledgement;
import com.netflix.eureka.transport.utils.TransportModel;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

/**
 * Avro schema is generated from Java classes, based on the {@link TransportModel}.
 *
 * @author Tomasz Bak
 */
class AvroSchemaArtifacts {

    private final Schema rootSchema;
    private final ConfigurableReflectData reflectData;

    AvroSchemaArtifacts(TransportModel model) {
        reflectData = new ConfigurableReflectData(model);

        List<Schema> schemas = new ArrayList<Schema>();
        schemas.add(reflectData.getSchema(Acknowledgement.class));

        for (Class<?> c : model.getProtocolTypes()) {
            schemas.add(reflectData.getSchema(c));
        }
        rootSchema = Schema.createUnion(schemas);
    }

    public Schema getRootSchema() {
        return rootSchema;
    }

    public ReflectData getReflectData() {
        return reflectData;
    }
}