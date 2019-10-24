function karateConfig() {
  // Use 'prod' as default environment
  var env = karate.env || "prod";

  karate.configure("connectTimeout", 5000);
  karate.configure("readTimeout", 5000);

  // enable X509 certificate authentication with PKCS12 file 'devcap_test.pfx'
  
  // 
  //karate.configure('ssl', { trustAll: true, keyStore: 'classpath:devcap_test.pfx', keyStorePassword: '1886', keyStoreType: 'pkcs12' });

  var config = {
    consumerApi: {
      url: "http://1.2.3.4/api"
    }
  };

  if (env == "prod") {
    config = {
      consumerApi: {
        url: "https://some-service-somewhere.net/api"
      }
    };
  }

  return config;
}
