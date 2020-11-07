package nuul.filters.post;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import nuul.context.RequestContext;
import nuul.enums.FilterType;
import nuul.exception.NuulException;
import nuul.filters.NuulFilter;

public class SendResponseFilter extends NuulFilter {

//	private static Logger logger = LoggerFactory.getLogger(SendResponseFilter.class);

	@Override
	public FilterType filterType() {
		return FilterType.POST;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	protected Object run() throws NuulException {
		ChannelHandlerContext ctx = RequestContext.getCurrentContext().getChannelHandlerContext();
		HttpResponse response = RequestContext.getCurrentContext().getResponse();
		if (null == response) response = new DefaultFullHttpResponse(HTTP_1_1, OK);
		ctx.write(response);
		ctx.flush();
		return null;
	}

}
