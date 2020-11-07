package nuul.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import nuul.context.RequestContext;
import nuul.exception.NuulException;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

//    private static Logger logger = LoggerFactory.getLogger(HttpboundHandler.class);
    private NuulRunner nuulRunner = new NuulRunner();
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	RequestContext.getCurrentContext().setChannelHandlerContext(ctx);
    	RequestContext.getCurrentContext().setRequest((FullHttpRequest)msg);
    	try {
            try {
                preRouting();
            } catch (NuulException e) {
                error(e);
                postRouting();
                return;
            }
            try {
                routing();
            } catch (NuulException e) {
                error(e);
                postRouting();
                return;
            }
            try {
                postRouting();
            } catch (NuulException e) {
                error(e);
                return;
            }
        } catch (Throwable e) {
            error(new NuulException(e, 500, "UNCAUGHT_EXCEPTION_FROM_FILTER_" + e.getClass().getName()));
        } finally {
            RequestContext.getCurrentContext().unset();
            ctx.close();
        }
    }

    void postRouting() throws NuulException {
    	nuulRunner.postRoute();
    }

    void routing() throws NuulException {
    	nuulRunner.route();
    }

    void preRouting() throws NuulException {
    	nuulRunner.preRoute();
    }

    void error(NuulException e) {
    	RequestContext.getCurrentContext().setThrowable(e);
        nuulRunner.error();
    }
    
    void init() {
    	nuulRunner.init();
    }
    
}
