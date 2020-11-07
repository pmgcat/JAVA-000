package nuul.filters.route;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import nuul.context.RequestContext;

public class NettyHttpClientOutboundHandler  extends ChannelInboundHandlerAdapter {
    
	private RequestContext requestContext;

	public NettyHttpClientOutboundHandler(RequestContext requestContext) {
		this.requestContext = requestContext;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof FullHttpResponse && null == requestContext.getResponse()){
			requestContext.setResponse((FullHttpResponse)msg);
		}
	}

}