package com.testing.center.web.common.utils.http;

import com.testing.center.web.common.utils.ZLYJSON.ZLYJSONArray;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * http类型，用于发送网络请求
 */
public class HttpUtils {
    // HTTP内容类型。相当于form表单的形式，提交数据
    private static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
    // 超时时间:ms
    private static final int SOCKET_TIME_OUT = 60 * 1000;
    //创建连接的最长时间:ms
    private static final int CONNECTION_TIME_OUT = 60 * 2000;
    // 从连接池中获取到连接的最长时间:ms
    private static final int CONNECTION_REQUEST_TIME_OUT = 60 * 2000;
    // 线程池最大连接数
    private static final int POOL_MAX_TOTAL = 3 * 1000;
    //默认的每个路由的最大连接数
    private static final int POOL_MAX_PERROUTE = 10;
    //检查永久链接的可用性:ms
    private static final int POOL_VALIDATE_AFTER_INACTIVITY = 2 * 1000;
    //关闭Socket等待时间，单位:s
    private static final int SOCKET_LINGER = 60;
    //建立httpClient配置
    private static HttpClientBuilder httpBulder;
    private static PoolingHttpClientConnectionManager pool;

    static {
        try {
            //User-Agent
            String USER_AGENT = "testing-center";
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create
                    ().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            // 连接管理器
            PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(POOL_MAX_TOTAL);
            //默认的每个路由的最大连接数
            pool.setDefaultMaxPerRoute(POOL_MAX_PERROUTE);
            //官方推荐使用这个来检查永久链接的可用性，而不推荐每次请求的时候才去检查
            pool.setValidateAfterInactivity(POOL_VALIDATE_AFTER_INACTIVITY);
            //设置默认连接配置
            pool.setDefaultSocketConfig(setSocketConfig());
            //请求重试处理
            // 如果已经重试了3次，就放弃
            // 如果服务器丢掉了连接，那么就重试
            // 不要重试SSL握手异常
            // 超时
            // 目标服务器不可达
            // ssl握手异常
            // 如果请求是幂等的，就再次尝试
            //请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
                if (executionCount >= 3) return false;// 如果已经重试了3次，就放弃
                if (exception instanceof NoHttpResponseException) return true; // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof SSLHandshakeException) return false; // 不要重试SSL握手异常
                if (exception instanceof InterruptedIOException) return false; // 超时
                if (exception instanceof UnknownHostException) return false;// 目标服务器不可达
                if (exception instanceof SSLException) return false;// ssl握手异常
                // 如果请求是幂等的，就再次尝试
                return !(HttpClientContext.adapt(context).getRequest()
                        instanceof HttpEntityEnclosingRequest);
            };
            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("Accept-Encoding", "chunked"));
            headers.add(new BasicHeader("Charset", StandardCharsets.UTF_8.name()));
            httpBulder = HttpClients.custom()
                    .setConnectionManager(pool) // 设置请求配置
                    .setDefaultRequestConfig(requestConfig())
                    .setUserAgent(USER_AGENT)
                    .setDefaultHeaders(headers)
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                    .setRetryHandler(httpRequestRetryHandler);  // 设置重试次数
//            ScheduledExecutorService service = Executors
//                    .newScheduledThreadPool(1);
//            service.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    pool.closeExpiredConnections();
//                    pool.closeIdleConnections(5000, TimeUnit.SECONDS);
//                }
//            }, 1, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * socket配置（默认配置 和 某个host的配置）
     */
    private static SocketConfig setSocketConfig() {
        return SocketConfig.custom()
                .setTcpNoDelay(true)     //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoTimeout(SOCKET_TIME_OUT)       //接收数据的等待超时时间，单位ms
                .setSoLinger(SOCKET_LINGER)         //关闭Socket时，要么发送完所有数据，要么等待XXs后，就关闭连接，此时socket.closeAllPresentThread()是阻塞的
                .setSoKeepAlive(true)    //开启监视TCP连接是否有效
                .build();
    }

    /**
     * 构建请求配置信息
     * 超时时间
     */
    private static RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIME_OUT) // 创建连接的最长时间
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT) // 从连接池中获取到连接的最长时间
                .setSocketTimeout(SOCKET_TIME_OUT) // 数据传输的最长时间
                .setProxy(new HttpHost("localhost", 8888))
                .setRedirectsEnabled(false)//禁止重定向
                .build();
    }

    private static CloseableHttpClient getHttpClient() {
        return httpBulder.build();
    }

    public static String doGet(String url, String param, Map<String, Object> headers) {
        return doGet(getURI(url, param), headers, null);
    }

    public static String doGet(String url, String param) {
        return doGet(getURI(url, param));
    }

    public static String doGet(URI uri) {
        return doGet(uri, null, null);
    }


    public static String doGet(URI uri, Map<String, Object> headers
            , NetworkHeaders networkHeaders) {
        String result = null;
        try {
            result = getResponse(getHttpGet(uri, headers), networkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //不可以关闭，不然连接池就会被关闭
//            httpClient.closeAllPresentThread();
        }
        return result;
    }

    public static String doPostJson(String url, String param) {
        return doPostJson(url, param, null, null);
    }


    public static String doPost(String url, Object param) {
        return doPost(url, param, null, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        return doPost(url, param, null, null);
    }

    public static String doPostMultipart(Object url
            , File param
            , Map<String, Object> requestHead
            , NetworkHeaders networkHeaders) {
        String resultString = null;
        try {
            HttpPost httpPost = getHttpPost(url.toString(), requestHead);
            if (param != null && param.exists() && param.isFile()) {
                FileBody file = new FileBody(param);
                String boundary = requestHead.get("boundary").toString();
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                if (boundary != null) multipartEntityBuilder.setBoundary(boundary);
                multipartEntityBuilder.addPart("file", file);
                httpPost.setEntity(multipartEntityBuilder.build());
            }
            resultString = getResponse(httpPost, networkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }

    @SuppressWarnings("unchecked")
    public static String doPost(Object url
            , Object param
            , Map<String, Object> requestHead
            , NetworkHeaders networkHeaders) {
        String resultString = null;

        try {
            HttpPost httpPost = getHttpPost(url.toString(), requestHead);
            // 创建参数列表
            if (param != null) {
                if (param instanceof Map) {
                    Map<String, Object> mapParam = (Map<String, Object>) param;
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (String key : mapParam.keySet()) {
                        paramList.add(new BasicNameValuePair(key, String.valueOf(mapParam.get(key))));
                    }
                    // 模拟表单
                    httpPost.setEntity(new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8));
                } else if (param instanceof String || param instanceof JSONArray || param instanceof ZLYJSONArray) {
                    StringEntity entity = new StringEntity(param.toString(), StandardCharsets.UTF_8);
                    httpPost.setEntity(entity);
                } else if (param instanceof byte[]) {
                    httpPost.setEntity(new ByteArrayEntity((byte[]) param));
                } else if (param instanceof URIBuilder) {
                    return HttpUtils.doPost(url, ((URIBuilder) param).build(), requestHead, networkHeaders);
                } else if (param instanceof URI) {
                    return HttpUtils.doPost(url, ((URI) param).getQuery(), requestHead, networkHeaders);

                } else if (param instanceof File) {
//                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//                    FileBody file = new FileBody((File) param);
//                    multipartEntityBuilder.addPart("file", file);
//                    httpPost.setEntity(multipartEntityBuilder.build());
                    InputStream inputStream = new FileInputStream((File) param);
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    inputStream.close();
                    boolean b = false;
                    for (Header header : httpPost.getAllHeaders()) {
                        if ("Content-Disposition".equals(header.getName())) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        httpPost.addHeader("Content-Disposition", "form-data; name=\"file\"; filename=\"" + ((File) param).getName() + "\"");
//                        httpPost.addHeader("Content-type", "multipart/form-data; boundary=----WebKitFormBoundaryq6XehrXmGNAAFHRw");
                    }
                    httpPost.setEntity(new ByteArrayEntity(bytes));
                } else {
                    throw new RuntimeException("不支持的类型");
                }
            }
            resultString = getResponse(httpPost, networkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }


//    @SuppressWarnings("unchecked")
//    public static byte[] doPostByte(Object url
//            , byte[] param
//            , Map<String, String> requestHead
//            , NetworkHeaders networkHeaders) {
//        try {
//            HttpPost httpPost = getHttpPost(url.toString(), requestHead);
//            // 创建参数列表
//            if (param != null) {
//                httpPost.setEntity(new ByteArrayEntity(param));
//            }
//            CloseableHttpClient closeableHttpClient = getHttpClient();
//            long startTime = System.currentTimeMillis();
//            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
//            if (networkHeaders != null) {
//                networkHeaders.setResponseTime(System.currentTimeMillis() - startTime);
//                networkHeaders.setHttpRequestBase(httpPost);
//            }
//            if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
//                responseNetworkHeaders(closeableHttpResponse, networkHeaders);
//                return getErrCodeFormat(closeableHttpResponse.getStatusLine().getStatusCode()).getBytes(StandardCharsets.UTF_8);
//            } else {
//                responseNetworkHeaders(closeableHttpResponse, networkHeaders);
//                return EntityUtils.toByteArray(closeableHttpResponse.getEntity());
//            }
//        } catch (Exception e) {
//            ServerAssert.fail(e);
//        }
//        return null;
//    }


    public static String doPostJson(String url
            , String param
            , Map<String, Object> requestHead
            , NetworkHeaders networkHeaders) {
        String resultString = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = getHttpPost(url, requestHead);
            // 创建请求内容
            if (null == param) param = "";
            StringEntity entity = new StringEntity(param, ContentType.APPLICATION_JSON);
            entity.setContentType(CONTENT_TYPE_JSON_URL);
            httpPost.setEntity(entity);
            // 执行http请求
            resultString = getResponse(httpPost, networkHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }

    private static Header[] getHeaders(Map<String, Object> requestHead) {
        List<Header> headers = new ArrayList<>();
        if (null != requestHead) {
            for (Map.Entry<String, Object> entry : requestHead.entrySet()) {
                headers.add(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }

        return headers.toArray(new Header[]{});
    }

    private static HttpGet getHttpGet(URI uri, Map<String, Object> headers) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(getHeaders(headers));
        return httpGet;
    }

    private static HttpPost getHttpPost(String url, Map<String, Object> requestHead) {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(getHeaders(requestHead));
        return httpPost;
    }


    public static byte[] doGetByte(URI uri, Map<String, Object> headers
            , NetworkHeaders networkHeaders) {
        byte[] result = null;
        try {
            result = (byte[]) getResponse(getHttpGet(uri, headers), networkHeaders, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //不可以关闭，不然连接池就会被关闭
//            httpClient.closeAllPresentThread();
        }
        return result;
    }

    private static String getResponse(HttpRequestBase httpRequestBase
            , NetworkHeaders networkHeaders) {
        return getURLDecoderString((String) getResponse(httpRequestBase, networkHeaders, true), StandardCharsets.UTF_8);
    }

    /**
     * 获取响应正文
     *
     * @param httpRequestBase
     * @param networkHeaders
     * @return
     */
    private static Object getResponse(HttpRequestBase httpRequestBase
            , NetworkHeaders networkHeaders, boolean string) {
        Object result = null;
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            CloseableHttpClient closeableHttpClient = getHttpClient();
            long startTime = System.currentTimeMillis();
            closeableHttpResponse = closeableHttpClient.execute(httpRequestBase);
            if (networkHeaders != null) {
                networkHeaders.setResponseTime(System.currentTimeMillis() - startTime);
                networkHeaders.setHttpRequestBase(httpRequestBase);
            }
            if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                result = getErrCodeFormat(closeableHttpResponse.getStatusLine().getStatusCode());
                if (!string) {
                    result = result.toString().getBytes();
                }
            } else {
                if (string) {
                    result = EntityUtils.toString(closeableHttpResponse.getEntity(), StandardCharsets.UTF_8);
                } else {
                    result = EntityUtils.toByteArray(closeableHttpResponse.getEntity());
                }

            }
            responseNetworkHeaders(closeableHttpResponse, networkHeaders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (closeableHttpResponse != null) closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 将服务器响应头添加到networkHeaders
     *
     * @param closeableHttpResponse
     * @param networkHeaders
     */
    private static void responseNetworkHeaders(CloseableHttpResponse closeableHttpResponse
            , NetworkHeaders networkHeaders) {
        if (networkHeaders != null) {
            networkHeaders.setResponseCode(closeableHttpResponse.getStatusLine().getStatusCode());
            Map<String, List<String>> map = new HashMap<>();
            List<String> list;
            for (Header header : closeableHttpResponse.getAllHeaders()) {
                list = new ArrayList<>();
                for (HeaderElement element : header.getElements()) {
                    list.add(element.getName());
                }
                map.put(header.getName(), list);
            }
            networkHeaders.setHeaders(map);
        }

    }

    public static URI getURI(String url, String param) {
        URI uri = null;
        try {
            if (param == null || param.equals("")) {
                uri = new URIBuilder(url.trim()).build();
            } else {
                uri = new URIBuilder(url.trim()).setCustomQuery(param.trim()).build();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return uri;
    }


    /**
     * 设置本地代理
     */
    public static void LocalProxy() {
        System.setProperty("http.proxySet", "true"); //将请求通过本地发送
        System.setProperty("http.proxyHost", "127.0.0.1");  //将请求通过本地发送
        System.setProperty("http.proxyPort", "8888"); //将请求通过本地发送
    }

    public static String getEncoderString(String param) {
        return getEncoderString(param, StandardCharsets.UTF_8);
    }

    /**
     * 转码
     *
     * @param param
     * @return
     */
    public static String getEncoderString(String param, Charset encoder) {
        param = param.replace("\n", "\r\n");
        try {
            param = URLEncoder.encode(param, encoder.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return param;
    }


    /**
     * 默认UTF-8
     *
     * @param str
     * @return
     */
    public static String getURLDecoderString(String str) {
        return getURLDecoderString(str, StandardCharsets.UTF_8);
    }

    /**
     * URL 解码
     *
     * @return StringText
     * @author zhanglianyu
     * @date 2017.7.23
     */
    public static String getURLDecoderString(String str, Charset encodingName) {
        String result = null;
        if (null == str) {
            throw new NullPointerException();
        }
        str = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        str = str.replaceAll("\\+", "%2B");
        if (str.indexOf("%", str.length() - 1) != -1) str = str.substring(0, str.length() - 1);
        try {
            result = URLDecoder.decode(str, encodingName.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 这里只是其中的一种场景,也就是把参数用&符号进行连接且进行URL编码
     * 根据实际情况拼接参数
     */
    public static String toHttpGetParams(Map<String, String> param) throws UnsupportedEncodingException {
        StringBuilder res = new StringBuilder();
        if (param == null) {
            return null;
        }
        for (Map.Entry<String, String> entry : param.entrySet()) {
            res.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name())).append("&");
        }
        return "".equals(res.toString()) ? "" : StringUtils.chop(res.toString());
    }


    //判断是否为16进制数
    private static boolean isHex(char c) {
        if (((c >= '0') && (c <= '9')) ||
                ((c >= 'a') && (c <= 'f')) ||
                ((c >= 'A') && (c <= 'F')))
            return true;
        else
            return false;
    }

    private static String convertPercent(String str) {
        StringBuilder sb = new StringBuilder(str);

        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            //判断是否为转码符号%
            if (c == '%') {
                if (((i + 1) < sb.length() - 1) && ((i + 2) < sb.length() - 1)) {
                    char first = sb.charAt(i + 1);
                    char second = sb.charAt(i + 2);
                    //如只是普通的%则转为%25
                    if (!(isHex(first) && isHex(second)))
                        sb.insert(i + 1, "25");
                } else {//如只是普通的%则转为%25
                    sb.insert(i + 1, "25");
                }

            }
        }

        return sb.toString();
    }

    /**
     * 错误响应code格式化
     *
     * @return
     */
    public static String getErrCodeFormat(int errCode) {
        return "{\"_testingCenterRequestServerResponseStatusCode\":" + errCode + "}";
    }

    public static void addParameterURIBuilder(URIBuilder uriBuilder, String key, Object value) {
        Objects.requireNonNull(uriBuilder);
        Objects.requireNonNull(key);
        if (value != null) uriBuilder.addParameter(key, String.valueOf(value));
    }

}
