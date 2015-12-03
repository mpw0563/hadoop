/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.server.datanode.web;

<<<<<<< HEAD
=======
import java.io.IOException;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.datanode.web.dtp.DtpHttp2Handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.stream.ChunkedWriteHandler;
<<<<<<< HEAD
=======
import org.apache.hadoop.ozone.web.netty.ObjectStoreJerseyContainer;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

/**
 * A port unification handler to support HTTP/1.1 and HTTP/2 on the same port.
 */
@InterfaceAudience.Private
public class PortUnificationServerHandler extends ByteToMessageDecoder {

  private static final ByteBuf HTTP2_CLIENT_CONNECTION_PREFACE = Http2CodecUtil
      .connectionPrefaceBuf();

  // we only want to support HTTP/1.1 and HTTP/2, so the first 3 bytes is
  // enough. No HTTP/1.1 request could start with "PRI"
  private static final int MAGIC_HEADER_LENGTH = 3;

  private final InetSocketAddress proxyHost;

  private final Configuration conf;

  private final Configuration confForCreate;

<<<<<<< HEAD
  public PortUnificationServerHandler(InetSocketAddress proxyHost,
      Configuration conf, Configuration confForCreate) {
    this.proxyHost = proxyHost;
    this.conf = conf;
    this.confForCreate = confForCreate;
  }

  private void configureHttp1(ChannelHandlerContext ctx) {
    ctx.pipeline().addLast(new HttpServerCodec(), new ChunkedWriteHandler(),
        new URLDispatcher(proxyHost, conf, confForCreate));
=======
  private final ObjectStoreJerseyContainer objectStoreJerseyContainer;

  public PortUnificationServerHandler(InetSocketAddress proxyHost,
      Configuration conf, Configuration confForCreate,
      ObjectStoreJerseyContainer container) {
    this.proxyHost = proxyHost;
    this.conf = conf;
    this.confForCreate = confForCreate;
    this.objectStoreJerseyContainer = container;
  }

  private void configureHttp1(ChannelHandlerContext ctx) throws IOException {
    ctx.pipeline().addLast(new HttpServerCodec(), new ChunkedWriteHandler(),
        new URLDispatcher(proxyHost, conf, confForCreate,
            objectStoreJerseyContainer));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  private void configureHttp2(ChannelHandlerContext ctx) {
    ctx.pipeline().addLast(new DtpHttp2Handler());
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in,
      List<Object> out) throws Exception {
    if (in.readableBytes() < MAGIC_HEADER_LENGTH) {
      return;
    }
    if (ByteBufUtil.equals(in, 0, HTTP2_CLIENT_CONNECTION_PREFACE, 0,
        MAGIC_HEADER_LENGTH)) {
      configureHttp2(ctx);
    } else {
      configureHttp1(ctx);
    }
    ctx.pipeline().remove(this);
  }

}
