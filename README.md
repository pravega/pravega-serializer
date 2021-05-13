<!--
Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
-->
# Pravega Custom Serializers

[Pravega client](https://github.com/pravega/pravega/client) currently implements the following serializers by default `io.pravega.client.stream.impl.UTF8StringSerializer`, `io.pravega.client.stream.impl.ByteArraySerializer`, `io.pravega.client.stream.impl.ByteBufferSerializer` and `io.pravega.client.stream.impl.JavaSerializer`. It does not support any other form of serialization or serializers to enable compression. This is to avoid forcing unwanted dependencies on the user application.

To solve this pravega-serializer repository creates different gradle build targets to generate specific artifacts for serializers which depend on external dependencies. These specialized Pravega serializers depend on a specific type of serializer thereby ensuring no unwanted transitive dependencies are forced on the user application.

## Project Structure

The project structure is described below.
```
pravega-serializer
   |---- gson (This supports Json based serializers)
   |---- avro 
   |---- customer serializer
```   
   Each sub module will generate a serailizer specific artifact which would depend on the pravaga-client and the specific external serializer libraries.
   
## Usage
Adding a dependency on a specific serializer by the user application in the build system like gradle / maven / ant.
e.g: `pravega-serializer-json:x.y.z` will result in the pravega-client:x.y.z being downloaded as part of the transitive dependency along with the serializer specific libraries.
