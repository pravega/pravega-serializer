/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.client;


import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import io.pravega.test.common.AssertExtensions;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JSONSerializerTest {

    @Test
    public void testSerialization() {
        JSONSerializer<CustomEvent> serialize = new JSONSerializer<>(new TypeToken<CustomEvent>() {
        }.getType());
        CustomEvent e = new CustomEvent(123L, "value1", ImmutableMap.of("key1", "value1"));
        ByteBuffer r = serialize.serialize(e);
        assertEquals(e, serialize.deserialize(r));
    }

    @Test
    public void testGenericSerialization() {
        JSONSerializer<CustomGenericEvent<String>> serialize = new JSONSerializer<>(new TypeToken<CustomGenericEvent<String>>() {
        }.getType());
        CustomGenericEvent<String> e = new CustomGenericEvent<>(123L, "value1", Collections.<String>singletonList("p1"));
        ByteBuffer r = serialize.serialize(e);
        assertEquals(e, serialize.deserialize(r));
    }

    @Test
    public void testByteArray() {
        JSONSerializer<byte[]> serializer = new JSONSerializer<>(new TypeToken<byte[]>() {
        }.getType());
        byte[] in = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] out = serializer.deserialize(serializer.serialize(in));
        AssertExtensions.assertArrayEquals("testByteArray failed", in, 0, out, 0, in.length);
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class CustomEvent {
        private long key; //
        private String value;
        private Map<String, String> properties;

    }
    
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class CustomGenericEvent<T> {
        private long key; //
        private String value;
        private List<T> properties;

    }
}
