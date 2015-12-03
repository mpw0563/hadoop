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

import static org.apache.hadoop.hdfs.server.datanode.web.webhdfs.WebHdfsHandler.WEBHDFS_PREFIX;
<<<<<<< HEAD
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
<<<<<<< HEAD

import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.datanode.web.webhdfs.WebHdfsHandler;

class URLDispatcher extends SimpleChannelInboundHandler<HttpRequest> {
  private final InetSocketAddress proxyHost;
  private final Configuration conf;
  private final Configuration confForCreate;

  URLDispatcher(InetSocketAddress proxyHost, Configuration conf,
                Configuration confForCreate) {
    this.proxyHost = proxyHost;
    this.conf = conf;
    this.confForCreate = confForCreate;
=======
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.datanode.web.webhdfs.WebHdfsHandler;
import org.apache.hadoop.ozone.web.headers.Header;
import org.apache.hadoop.ozone.web.netty.ObjectStoreJerseyContainer;
import org.apache.hadoop.ozone.web.netty.RequestDispatchObjectStoreChannelHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

class URLDispatcher extends SimpleChannelInboundHandler<HttpRequest> {
  protected static final Logger LOG =
      LoggerFactory.getLogger(URLDispatcher.class);
  private final InetSocketAddress proxyHost;
  private final Configuration conf;
  private final Configuration confForCreate;
  private final ObjectStoreJerseyContainer objectStoreJerseyContainer;

  URLDispatcher(InetSocketAddress proxyHost, Configuration conf,
                Configuration confForCreate,
                ObjectStoreJerseyContainer objectStoreJerseyContainer)
      throws IOException {
    this.proxyHost = proxyHost;
    this.conf = conf;
    this.confForCreate = confForCreate;
    this.objectStoreJerseyContainer = objectStoreJerseyContainer;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpRequest req)
<<<<<<< HEAD
    throws Exception {
    String uri = req.uri();
    ChannelPipeline p = ctx.pipeline();
    if (uri.startsWith(WEBHDFS_PREFIX)) {
      WebHdfsHandler h = new WebHdfsHandler(conf, confForCreate);
      p.replace(this, WebHdfsHandler.class.getSimpleName(), h);
      h.channelRead0(ctx, req);
=======
      throws Exception {
    ChannelPipeline p = ctx.pipeline();
    if (isWebHdfsRequest(req)) {
      WebHdfsHandler h = new WebHdfsHandler(conf, confForCreate);
      p.replace(this, WebHdfsHandler.class.getSimpleName(), h);
      h.channelRead0(ctx, req);
    } else if (isObjectStoreRequest(req)) {
      RequestDispatchObjectStoreChannelHandler h =
          new RequestDispatchObjectStoreChannelHandler(
              this.objectStoreJerseyContainer);
      p.replace(this,
          RequestDispatchObjectStoreChannelHandler.class.getSimpleName(), h);
      h.channelRead0(ctx, req);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    } else {
      SimpleHttpProxyHandler h = new SimpleHttpProxyHandler(proxyHost);
      p.replace(this, SimpleHttpProxyHandler.class.getSimpleName(), h);
      h.channelRead0(ctx, req);
    }
  }
<<<<<<< HEAD
=======

  /*
   * Returns true if the request is to be handled by the object store.
   *
   * @param req HTTP request
   * @return true if the request is to be handled by the object store
   */
  private boolean isObjectStoreRequest(HttpRequest req) {
    if (this.objectStoreJerseyContainer == null) {
      LOG.debug("ozone : dispatching call to webHDFS");
      return false;
    }
    for (String version : req.headers().getAll(Header.OZONE_VERSION_HEADER)) {
      if (version != null) {
        LOG.debug("ozone : dispatching call to Ozone");
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if the request is to be handled by WebHDFS.
   *
   * @param req HTTP request
   * @return true if the request is to be handled by WebHDFS
   */
  private boolean isWebHdfsRequest(HttpRequest req) {
    return req.uri().startsWith(WEBHDFS_PREFIX);
  }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
}
