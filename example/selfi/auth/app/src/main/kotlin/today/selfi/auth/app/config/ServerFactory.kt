package today.selfi.auth.app.config

import org.eclipse.jetty.http.HttpVersion
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.HttpConfiguration
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.server.SecureRequestCustomizer
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.SslConnectionFactory
import org.eclipse.jetty.server.handler.ContextHandlerCollection
import org.eclipse.jetty.server.handler.DefaultHandler
import org.eclipse.jetty.server.handler.HandlerCollection
import org.eclipse.jetty.util.ssl.SslContextFactory
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object ServerFactory {
  fun secureServer(port: Port, securePort: Port): Server {
    // Setup threadpool
    val threadPool = QueuedThreadPool()
    threadPool.maxThreads = 500

    // Server
    val server = Server(threadPool)

    // Scheduler
    server.addBean(ScheduledExecutorScheduler(null, false))

    // HTTP Configuration
    val httpConfig = HttpConfiguration()
      .apply {
        secureScheme = "https"
        outputBufferSize = 32768
        requestHeaderSize = 8192
        responseHeaderSize = 8192
        sendServerVersion = true
        sendDateHeader = false
      }
    httpConfig.securePort = securePort.port

    // Handler Structure
    // httpConfig.addCustomizer(new ForwardedRequestCustomizer());
    val handlers = HandlerCollection()
    val contexts = ContextHandlerCollection()
    handlers.handlers = arrayOf<Handler>(contexts, DefaultHandler())
    server.handler = handlers

    // === jetty-http.xml ===
    val http = ServerConnector(
      server,
      HttpConnectionFactory(httpConfig)
    )
    http.port = port.port
    http.idleTimeout = 30000
    server.addConnector(http)

    // === jetty-https.xml ===
    // SSL Context Factory
//    val keystorePath: Path = Paths.get("src/main/resources/etc/keystore").toAbsolutePath()
    val keystorePath: Path = Paths.get("example/selfi/auth/app/src/main/resources/etc/keystore").toAbsolutePath()
    if (!Files.exists(keystorePath)) throw FileNotFoundException(keystorePath.toString())
    val sslContextFactory: SslContextFactory = SslContextFactory.Server()

    sslContextFactory.keyStorePath = keystorePath.toString()
    sslContextFactory.setKeyStorePassword("6a4BhrjLMjA")
    sslContextFactory.setKeyManagerPassword("6a4BhrjLMjA")
    sslContextFactory.trustStorePath = keystorePath.toString()
    sslContextFactory.setTrustStorePassword("6a4BhrjLMjA")

    // SSL HTTP Configuration
    val httpsConfig = HttpConfiguration(httpConfig)
    httpsConfig.addCustomizer(SecureRequestCustomizer())

    // SSL Connector
    val sslConnector = ServerConnector(
      server,
      SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
      HttpConnectionFactory(httpsConfig)
    )
    sslConnector.port = securePort.port
    server.addConnector(sslConnector)

    return server
  }
}