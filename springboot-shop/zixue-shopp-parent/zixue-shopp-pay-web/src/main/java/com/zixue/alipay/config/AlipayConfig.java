package com.zixue.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092900626012";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCSc1JvhwOQh4QaW/hD+WzAfnhBP071fTUc3TJvCXntmmL5Pn5hsj+gkyo2TGO1CvvZyo/vtiV4iw0SZMqq9AsdXNTZhWPySMKHkTHpdWx+FP2WHzW8ZNCMeyE+/rgeP6h1fhs7X2oFj1AcWVlC9/dvGJIhsAvVMuJDj90zeAhODR3njfDri6GJ0GRGQjEy1glP3kKO53Lo/ZGBguIoUQOrccvgIGXcS8XwA8HqjUmF4lPur3Dj/3uqi9ntCMbHKrfb4QINgSxrNxXpjZ8oDk2yLXR3nNrLwMBbOeDx13QnEiIxd9Kl8t4abqMSUt+cfVS8/3F4gpA5hGpanCaneGZTAgMBAAECggEAYuwaxtLACaRACz4MWH3mg6/pssj8oL3OXozbQD16apBaQlmALKWmbOR7Iuo+f0/IBrjYuOakMORcFAyZCRiHWXIXJEcB1+pl3ASrXjK+8ftjvz+doH/Ijtus/Uw5OdVuCM8Bvf0ZNGA1kLCZpq6wKA7ExpSPqZcRVumaVOXDVlRoRdk2a9u8EzXzyqNi5//WHxZZtZfwXGhO4grmyWX8XEQslmyRhFFw24FUTPRACjD93vndrt28G01M4joSeQ3dNmVvP4WfK/aNBLa8cLf4K3k29+cqefsF/w2BfzGMKb2oWGzelI2qIqgmyW3dDe7rV3uzjjAArseDQK1HkGdUQQKBgQDCMOHwCj5JxkUwfpfJeYNnuamyIsEcjHguz3JURXAuUXOawhArdamPz9Uv2VYO9IISgCkJ4TVtyNq9FeBZi6elxIuvjngkY1tGnLfsx5Nm47GCi7B9nG13Z21Zdj/ls5mLXR8EOA6jlDxlvBtkyo4SIG5hyuxsR1CRCoJWiL2SawKBgQDBEHAdElRCdyyBnXpZ9sqUo5567u13ztuaGxiTgWtcGsVCbZtpp3R/Ajaq948xgluDYXrs2PJ4ssukz+aLDfC5v8mBAkHT2/ezeg8Uh0URlJ+ZpN+DF2ANys/tKpEVUFLBrkPO8/w/PcHbV1iuxnwIfZNVbsk+w1AscpZadjiFuQKBgEiyMALCcur/VGuBGKWN3cl40bQkGpLraxprAmvVytcsGtD2kUMTuzRhI3fULslOc1slqzdNncjJhkl+5QvgohQGbhx8gzFsGIFordnwJJhB6PPsA1gitUAb8e2pvOXqxDJN3AyiR6ipF6smLkMfKOxUQ75MTxGB6QFDI+PtMLqdAoGAbrcNhC5xsZgEJuR3OMZFUE4wYRFJjw5KcyqcwmJSfgRAISMYH9PPA9LyJMNJE7xM3J/3TYHwMvVdwUsUS6QX632ngdsYgvU/LGaP5VbZbgaMLCEXqGasR9yJttlVUCK7AqT779+NRXsWA8IocjoTAQ96gWDB+cMp5ANmY8QuD8kCgYBRY0n1I4gtcWHFQy4otdCWhQ+vtA5PRB2UJE7lHG7HEyHxx60zGsQZTN0Hfl+NK8RRrR3+0DvcZlLZuBZv8c6WCK7qb5enArzRQSagBYttBna+20B2r3/hFgDuyxL6IjFthZayXiemZxGA+cuJrFoSyRFfe/AqEskJw37Fjjkvaw==";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
	// 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1K9VjfFZn8T3ZK/igCh62hY/BYpKT0s/hJJjFtYMcww6GtH3nunVthpwUyUdTUbDPyHuzKuo4xYZJwGbFb078oOmwmaPOvvg9B5d7/B5cp6Ipb89vO7zT3lLFPDULL9mbQk2uWHDuBB8KPHE5HaO1uW4atIXIwWSZm44N/rpbVF3MLtLYf5kiVSipRzP/hGZsBcWAoRSP7jSsKJV/9Oqg4rUgVrXUoVlIGCJt5LA9ipL/qKFdpipFtknEVdPqyok2XJFRzjuslNpgiJIRmZI+AL41ENpnfF28POs9cR7GjGdcYMMLQoXCFNIna/2+ejBSpBVykUuTt2Oip+LPRxr5wIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://lovexixi.natapp1.cc/pay-web/alipay/callback/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://lovexixi.natapp1.cc/pay-web/alipay/callback/returnUrl";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "D:\\";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
