package app.Config;

import app.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("app")
public class AppConfig {

    @Bean
    public UrlService userInputUrlService() {
        return new UserInputUrlService(sentence2WordsService());
    }

    @Bean
    public CrawlerService userInputCrawlerService() {
        return new UserInputCrawlerService(sentence2WordsService());
    }

    @Bean
    public Sentence2WordsService sentence2WordsService() {
        return new Sentence2WordsService(coreNlpService());
    }

    @Bean
    public CoreNlpService coreNlpService() {
        return new CoreNlpService();
    }

    @Bean
    public UserInputService userInputService() {
        return new UserInputService();
    }
}
