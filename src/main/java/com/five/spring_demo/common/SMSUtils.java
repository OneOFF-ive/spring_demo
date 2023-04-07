package com.five.spring_demo.common;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.models.*;
import com.aliyun.sdk.service.dysmsapi20170525.*;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
				.accessKeyId("LTAI5tS9M6p8zuumsbtyRjP6")
				.accessKeySecret("0n7dpmLnAeq1cbiFlWq9IAxQs33K7D")
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
			e.printStackTrace();
		}
		System.out.println(new Gson().toJson(resp));

		client.close();
	}

}
