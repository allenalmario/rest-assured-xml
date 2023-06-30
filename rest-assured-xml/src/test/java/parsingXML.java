import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class parsingXML {
    @Test
    public void testXMLResponse() throws IOException {

        // ADD

        FileInputStream fileInputStream = new FileInputStream(".\\SOAPRequest\\request1.xml");

        RestAssured.baseURI="http://www.dneonline.com";

        Response response =
                given()
                        .header("Content-Type", "text/xml")
                        .and()
                    .body(IOUtils.toString(fileInputStream, "UTF-8"))
                    .when()
                        .post("/calculator.asmx")
                    .then()
                        .statusCode(200)
                        .body("Envelope.Body.AddResponse.AddResult", equalTo("2"))
                        .log().all().extract().response();

        XmlPath xmlpath = new XmlPath(response.asString());
        String result = xmlpath.getString("AddResult");
        System.out.println("result is: " + result);

        // SUBTRACT

        FileInputStream fileInputStream2 = new FileInputStream(".\\SOAPRequest\\request2.xml");

        Response response2 =
                given()
                        .header("Content-Type", "text/xml")
                        .and()
                        .body(IOUtils.toString(fileInputStream2, "UTF-8"))
                        .when()
                        .post("/calculator.asmx")
                        .then()
                        .statusCode(200)
                        .body("Envelope.Body.SubtractResponse.SubtractResult", equalTo("0"))
                        .log().all().extract().response();

        XmlPath xmlpath2 = new XmlPath(response2.asString());
        String result2 = xmlpath2.getString("SubtractResult");
        System.out.println("result is: " + result2);
    }

}
