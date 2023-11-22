package com.example.cookblog.e2e;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

@Getter
public class SeleniumConfig {

    private static final String WEBDRIVER_PATH = "bin/";

    static {
        System.setProperty("webdriver.chrome.driver", findFile("chromedriver"));
    }

    private final WebDriver driver = new ChromeDriver();

    static private String findFile(String filename) {
        if (new File(WEBDRIVER_PATH + filename).exists()) {
            return WEBDRIVER_PATH + filename;
        } else if (new File(WEBDRIVER_PATH + filename + ".exe").exists()) {
            return WEBDRIVER_PATH + filename + ".exe";
        }
        throw new RuntimeException("File not found: " + filename);
    }

}
