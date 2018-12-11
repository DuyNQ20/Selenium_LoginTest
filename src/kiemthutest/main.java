/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiemthutest;

/**
 *
 * @author QuangDuy-PC
 */
public class main {
    public static void main(String[] args) {
        TestManager test = new TestManager();
        System.setProperty("webdriver.chrome.driver", "libs/chromedriver.exe");
        test.start();
    }
}
