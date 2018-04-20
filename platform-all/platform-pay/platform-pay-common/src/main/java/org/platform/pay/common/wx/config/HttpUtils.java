package org.platform.pay.common.wx.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 
 */
public class HttpUtils {

	private HttpUtils() {
	}

	/**
	 * input to String
	 * 
	 * @param is
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getStringFromInputStream(InputStream is,
			String charset) throws Exception {
		if (is == null) {
			return null;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				charset));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		if (br != null) {
			br.close();
		}

		String str = sb.toString();
		return str;
	}
	
	public static String postXml(String url, String xml)
			throws IllegalStateException, IOException, Exception {
		String result = null;
		HttpEntity postEntity = new StringEntity(xml, "UTF-8");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpResponse response = null;
		response = httpclient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			String location = response.getFirstHeader("Location").getValue();
			return get(location);
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = getStringFromInputStream(entity.getContent(), "utf-8");
		}
		return result;
	}

	public static HttpRequestRetryHandler getRetryHandler() {
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 5) {
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					return false;
				}
				HttpRequest request = (HttpRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					return true;
				}
				return false;
			}
		};

		return myRetryHandler;
	}
	
	public static String get(String url) throws Exception {
		String result = "";
		HttpGet request = new HttpGet(url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.setHttpRequestRetryHandler(getRetryHandler());
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpResponse response = httpclient.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			String location = response.getFirstHeader("Location").getValue();
			return get(location);
		}
		HttpEntity entity = response.getEntity();
		HeaderElement[] hes = entity.getContentType().getElements();
		String encode = "utf-8";
		if (hes != null && hes.length > 0) {
			for (HeaderElement he : hes) {
				encode = he.getParameterByName("charset") == null ? "utf-8"
						: he.getParameterByName("charset").getValue();
			}
		}
		if (entity != null) {
			// result = getStringFromInputStream(entity.getContent(), encode);
			result = EntityUtils.toString(entity, encode);
		}
		return result;
	}
	  

	 
	public static String getHost(String url) {
		if (url == null || url.trim().equals("")) {
			return "";
		}
		String host = "";
		Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+(\\:[0-9]*){0,1}");
		Matcher matcher = p.matcher(url);
		if (matcher.find()) {
			host = matcher.group();
		}
		return host;
	}
	
	
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";
	
	/**
	 * get HttpURLConnection
	 * @param strUrl url地址
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection getHttpURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		return httpURLConnection;
	}
	
	/**
	 * get HttpsURLConnection
	 * @param strUrl url地址
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	public static HttpsURLConnection getHttpsURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
				.openConnection();
		return httpsURLConnection;
	}
	
	/**
	 * 获取不带查询串的url
	 * @param strUrl
	 * @return String
	 */
	public static String getURL(String strUrl) {

		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(0, indexOf);
			} 
			
			return strUrl;
		}
		
		return strUrl;
		
	}
	
	/**
	 * 获取查询串
	 * @param strUrl
	 * @return String
	 */
	public static String getQueryString(String strUrl) {
		
		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(indexOf+1, strUrl.length());
			} 
			
			return "";
		}
		
		return strUrl;
	}
	
	/**
	 * 查询字符串转换成Map<br/>
	 * name1=key1&name2=key2&...
	 * @param queryString
	 * @return
	 */
	public static Map queryString2Map(String queryString) {
		if(null == queryString || "".equals(queryString)) {
			return null;
		}
		
		Map m = new HashMap();
		String[] strArray = queryString.split("&");
		for(int index = 0; index < strArray.length; index++) {
			String pair = strArray[index];
			HttpUtils.putMapByPair(pair, m);
		}
		
		return m;
		
	}
	
	/**
	 * 把键值添加至Map<br/>
	 * pair:name=value
	 * @param pair name=value
	 * @param m
	 */
	public static void putMapByPair(String pair, Map m) {
		
		if(null == pair || "".equals(pair)) {
			return;
		}
		
		int indexOf = pair.indexOf("=");
		if(-1 != indexOf) {
			String k = pair.substring(0, indexOf);
			String v = pair.substring(indexOf+1, pair.length());
			if(null != k && !"".equals(k)) {
				m.put(k, v);
			}
		} else {
			m.put(pair, "");
		}
	}
	
	/**
	 * BufferedReader转换成String<br/>
	 * 注意:流关闭需要自行处理
	 * @param reader
	 * @return String
	 * @throws IOException
	 */
	public static String bufferedReader2String(BufferedReader reader) throws IOException {
		StringBuffer buf = new StringBuffer();
		String line = null;
		while( (line = reader.readLine()) != null) {
			buf.append(line);
			buf.append("\r\n");
		}
				
		return buf.toString();
	}
	
	/**
	 * 处理输出<br/>
	 * 注意:流关闭需要自行处理
	 * @param out
	 * @param data
	 * @param len
	 * @throws IOException
	 */
	public static void doOutput(OutputStream out, byte[] data, int len) 
			throws IOException {
		int dataLen = data.length;
		int off = 0;
		while(off < dataLen) {
			if(len >= dataLen) {
				out.write(data, off, dataLen);
			} else {
				out.write(data, off, len);
			}
			
			//刷新缓冲区
			out.flush();
			
			off += len;
			
			dataLen -= len;
		}
		
	}
	
	/**
	 * 获取SSLContext
	 * @param trustFile 
	 * @param trustPasswd
	 * @param keyFile
	 * @param keyPasswd
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 */
	public static SSLContext getSSLContext(
			FileInputStream trustFileInputStream, String trustPasswd,
			FileInputStream keyFileInputStream, String keyPasswd)
			throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException,
			KeyManagementException {

		// ca
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpUtils.SunX509);
		KeyStore trustKeyStore = KeyStore.getInstance(HttpUtils.JKS);
		trustKeyStore.load(trustFileInputStream, HttpUtils
				.str2CharArray(trustPasswd));
		tmf.init(trustKeyStore);

		final char[] kp = HttpUtils.str2CharArray(keyPasswd);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpUtils.SunX509);
		KeyStore ks = KeyStore.getInstance(HttpUtils.PKCS12);
		ks.load(keyFileInputStream, kp);
		kmf.init(ks, kp);

		SecureRandom rand = new SecureRandom();
		SSLContext ctx = SSLContext.getInstance(HttpUtils.TLS);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

		return ctx;
	}
	
	/**
	 * 获取CA证书信息
	 * @param cafile CA证书文件
	 * @return Certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static Certificate getCertificate(File cafile)
			throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cafile);
		Certificate cert = cf.generateCertificate(in);
		in.close();
		return cert;
	}
	
	/**
	 * 字符串转换成char数组
	 * @param str
	 * @return char[]
	 */
	public static char[] str2CharArray(String str) {
		if(null == str) return null;
		
		return str.toCharArray();
	}
	
	/**
	 * 存储ca证书成JKS格式
	 * @param cert
	 * @param alias
	 * @param password
	 * @param out
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static void storeCACert(Certificate cert, String alias,
			String password, OutputStream out) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");

		ks.load(null, null);

		ks.setCertificateEntry(alias, cert);
		// store keystore
		ks.store(out, HttpUtils.str2CharArray(password));
	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
