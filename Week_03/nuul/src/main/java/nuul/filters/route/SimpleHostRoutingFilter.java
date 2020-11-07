package nuul.filters.route;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpVersion;
import nuul.context.RequestContext;
import nuul.enums.FilterType;
import nuul.exception.NuulException;
import nuul.filters.NuulFilter;

public class SimpleHostRoutingFilter extends NuulFilter {

	private static Logger logger = LoggerFactory.getLogger(SimpleHostRoutingFilter.class);

	@Override
	public FilterType filterType() {
		return FilterType.ROUTE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return null != RequestContext.getCurrentContext().getRouteHost();
	}

	@Override
	protected Object run() throws NuulException {
		final RequestContext requestContext = RequestContext.getCurrentContext();
		FullHttpRequest request = requestContext.getRequest();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					// 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
//					ch.pipeline().addLast(new HttpResponseDecoder());// 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpClientCodec());
					ch.pipeline().addLast(new HttpObjectAggregator(65536));
					ch.pipeline().addLast(new HttpContentDecompressor());
					ch.pipeline().addLast(new NettyHttpClientOutboundHandler(requestContext));
				}
			});
			// Start the client.
			URL url = requestContext.getRouteHost();
			ChannelFuture f = b.connect(url.getHost(), NewPort(url.getPort())).sync();
			FullHttpRequest _request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, request.method(),
					url.getPath() + (null == url.getQuery() ? "" : "?" + url.getQuery()), request.content());
			_request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
			f.channel().write(_request);
			f.channel().flush();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NuulException(e, 500, e.getMessage());
		} finally {
			workerGroup.shutdownGracefully();
		}
		return null;
	}

	private int NewPort(int port) {
		return port < 0 ? 80 : port;
	}
}
