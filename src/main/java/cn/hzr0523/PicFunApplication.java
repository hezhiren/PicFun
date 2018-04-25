package cn.hzr0523;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.InputStream;
import java.util.Properties;

/**
 * hezhi
 * 2018/1/23 10:03
 */
@SpringBootApplication
public class PicFunApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception{
        Properties properties = new Properties();
        InputStream in = PicFunApplication.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(in);
        SpringApplication app = new SpringApplication(PicFunApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }
}
