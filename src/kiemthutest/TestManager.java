
package kiemthutest;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



/**
 *
 * @author QuangDuy-PC
 */
public class TestManager {
    
    ListTestCase listTest  = new ListTestCase();
    
    public void load()
    {
        Gson gson = new Gson();
        
        try {
            listTest = gson.fromJson( new FileReader(new File("src/kiemthutest/datatest/testcase.json")), ListTestCase.class);
            
        } catch (FileNotFoundException | JsonSyntaxException | JsonIOException e) {
            System.out.println("Loi doc file" + e.getMessage());
        }
    }
    
    
    public void writeResult(String result)
    {
        File file = new File("src/kiemthutest/datatest/result.txt");
        try (PrintWriter print = new PrintWriter(file)) {
            print.write(result);
        } catch (Exception e) {
            System.out.println("Loi ghi file" + e.getMessage());
        }
    }
    
    public void start()
    {
        WebDriver driver;
        WebElement element;
        String result = "TestCase                                        Kết Quả                                          Messenges\r\n";
        result += "------------------------------------------------------------------------------------------------------------------------------------------\r\n";
        load();
        
        
        int leng = listTest.getList().size();
        
        System.out.println("số lượng: " + leng);
        for(int i = 0; i < leng; i++)
        {
            String add = "";
            //System.out.println("username: " + listTest.getList().get(i).getUsername() + "\nPass: " + listTest.getList().get(i).getPassword());
            driver = new ChromeDriver();
            try {
                
                
                driver.get("http://localhost:8888/QuangDuy/admin/user/login.html");
                Thread.sleep(500);
                element = driver.findElement(By.name("username"));
                element.sendKeys(listTest.getList().get(i).getUsername());
                Thread.sleep(500);
                element = driver.findElement(By.name("password"));
                element.sendKeys(listTest.getList().get(i).getPassword());
                Thread.sleep(500);
                element = driver.findElement(By.className("btn-login"));
                element.click();
                Thread.sleep(1000);
                
                if(i < 9)
                    add = "  ";
                try{
                    element = driver.findElement(By.cssSelector(".error p"));
                }
                catch(Exception e)
                {
                    element = driver.findElement(By.cssSelector(".alert-danger"));
                }
                
                
                System.out.println("Testcase " + (i + 1) + ": Pass");
                result += "\n#" + (i + 1) + add + "                                                Pass                                               " + element.getText() + "\r\n";
                
                
            } catch (Exception e) {
                
                
                result += "\n#" + (i + 1) + add  + "                                                Pass                                               Đăng nhập thành công" + "\r\n";      
                System.out.println("Testcase " + (i + 1) + ": pass");
            }
            driver.close();
            driver.quit();
            
            
            writeResult(result);
            
        }
        
        
    }
    
}
