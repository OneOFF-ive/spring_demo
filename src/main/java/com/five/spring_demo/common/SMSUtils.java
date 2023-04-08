package com.five.spring_demo.common;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.models.*;
import com.aliyun.sdk.service.dysmsapi20170525.*;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 短信发送工具类
 */
@Slf4j
@Component
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){

		AnnotationConfigApplicationContext applicationContext = BaseContext.getApplicationContext();
		String accessKeyId = applicationContext.getEnvironment().getProperty("reggie.access-key-id");
		String accessKeySecret = applicationContext.getEnvironment().getProperty("reggie.access-key-secret");

		StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
				.accessKeyId(accessKeyId)
				.accessKeySecret(accessKeySecret)
				.build());

		AsyncClient client = AsyncClient.builder()
				.region("cn-shanghai") // Region ID
				.credentialsProvider(provider)
				.overrideConfiguration(
						ClientOverrideConfiguration.create()
								.setEndpointOverride("dysmsapi.aliyuncs.com")
				)
				.build();

		SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
				.signName(signName)
				.templateCode(templateCode)
				.phoneNumbers(phoneNumbers)
				.templateParam("{\"code\":\"" + param + "\"}")
				.build();

		CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
		SendSmsResponse resp = null;
		try {
			resp = response.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
		}
		System.out.println(new Gson().toJson(resp));

		client.close();
	}

}
