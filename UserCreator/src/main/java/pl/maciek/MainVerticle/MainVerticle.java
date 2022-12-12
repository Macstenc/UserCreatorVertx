package pl.maciek.MainVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.maciek.MainVerticle.methods.ItemMethods;
import pl.maciek.MainVerticle.methods.UserMethods;



public class MainVerticle extends AbstractVerticle {
    public static String connectionString = "mongodb+srv://Admin:admin@project.hxbbttt.mongodb.net/test";



    private static final Logger LOG= LoggerFactory.getLogger(MainVerticle.class);
    public static final int PORT = 8888;

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://Admin:admin@project.hxbbttt.mongodb.net/test";


        Vertx vertx = Vertx.vertx();

        vertx.exceptionHandler(error->{
            LOG.error("Unhandler: {}",error);
        });
        vertx.deployVerticle(new MainVerticle(),ar->{
            if(ar.failed()) {
                LOG.error("Failed to deploy: {}",ar.cause());
                return;
            }
            LOG.info("Deployed MainVerticle");
        });


    }
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        final Router router=Router.router(vertx);

        router.route()
                .handler(BodyHandler.create())
                .failureHandler(MainVerticle::handleFailure);
        UserMethods.run(router);
        ItemMethods.run(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .exceptionHandler(error->LOG.error("error",error))
                .listen(PORT, http -> {
                    if (http.succeeded()) {
                        startPromise.complete();
                        System.out.println("HTTP server started on port 8888");
                    } else {
                        startPromise.fail(http.cause());
                    }
                });


    }

    private static void handleFailure(RoutingContext errorContext) {
        if (errorContext.response().ended()) {
            return;
        }
        LOG.error("Rounter Error:", errorContext.failure());
        errorContext.response()
                .setStatusCode(500)
                .end(new JsonObject().put("message", "Something went wrong:(").toBuffer());
    }
}