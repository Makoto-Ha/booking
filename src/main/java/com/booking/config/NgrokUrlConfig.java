package com.booking.config;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
public class NgrokUrlConfig {

	private String ngrokURL;
    
    @PostConstruct
    public void init() {
        String ngrokUrl = fetchNgrokUrl();
        if (ngrokUrl != null) {
            System.out.println("連結到ngrok的url" + ngrokUrl);
            this.ngrokURL = ngrokUrl;
        } else {
            System.err.println("找不到ngrok的url");
            this.ngrokURL = "http://localhost:8080";
        }
    }
    
    private String fetchNgrokUrl() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject("http://localhost:4040/api/tunnels", String.class);
            JSONObject jsonObject = new JSONObject(response);
   
            return jsonObject.getJSONArray("tunnels").getJSONObject(0).getString("public_url");
        } catch (Exception e) {
            e.printStackTrace();     
            return null;
        }
    }

	public String getNgrokURL() {
		return ngrokURL;
	}

	public void setNgrokURL(String ngrokURL) {
		this.ngrokURL = ngrokURL;
	}

}