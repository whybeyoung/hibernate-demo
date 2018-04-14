package com.iflytek.iaas.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class HttpConfig {

    @Value("${http.maxTotal:100}")
    private Integer maxTotal;

    @Value("${http.defaultMaxPerRoute:10}")
    private Integer defaultMaxPerRoute;

    @Value("${http.connectTimeout:10000}")
    private Integer connectTimeout;

    @Value("${http.connectionRequestTimeout:120000}")
    private Integer connectionRequestTimeout;

    @Value("${http.socketTimeout:120000}")
    private Integer socketTimeout;

    @Value("${http.staleConnectionCheckEnabled:false}")
    private boolean staleConnectionCheckEnabled;

    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(@Qualifier("sslConnectionFactory") ConnectionSocketFactory ssl, @Qualifier("plainConnectionFactory") ConnectionSocketFactory plain){

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HTTP, plain)
                .register(HTTPS, ssl)
                .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        return httpClientConnectionManager;
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager")PoolingHttpClientConnectionManager httpClientConnectionManager){

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }

    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }


    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder(){
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }



    @Bean(name = "plainConnectionFactory")
    public ConnectionSocketFactory getPlainConnectionSocketFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return new PlainConnectionSocketFactory();
    }

    @Bean(name = "sslConnectionFactory")
    public ConnectionSocketFactory getSSlConnectionSocketFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContextBuilder builder = new SSLContextBuilder();
        // 全部信任 不做身份鉴定
        builder.loadTrustMaterial(null, (TrustStrategy) (x509Certificates, s) -> true);
        return new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
    }

    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder){
        return builder.build();
    }

}