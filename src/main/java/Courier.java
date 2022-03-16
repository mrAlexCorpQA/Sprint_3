import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.*;


public class Courier {

    //URL адрес endpoint для создания нового курьера
    String createCourierEndpointURL = "https://qa-scooter.praktikum-services.ru/api/v1/courier";

    //URL адрес endpoint для получения id курьера (авторизация)
    String requestCourierIdEndpointURL = "https://qa-scooter.praktikum-services.ru/api/v1/courier/login";

    //URL адрес endpoint для удаления курьера
    String deleteCourierEndpointURL = "https://qa-scooter.praktikum-services.ru/api/v1/courier/";


    //Служебный метод для генерации логина для тестового набора данных. В качестве параметра передается длинна генерируемой строки
    String courierLogin = RandomStringUtils.randomAlphabetic(10);
    //Служебный метод для генерации пароля для тестового набора данных. В качестве параметра передается длинна генерируемой строки
    String courierPassword = RandomStringUtils.randomAlphabetic(10);
    //Служебный метод для генерации имени курьера для тестового набора данных. В качестве параметра передается длинна генерируемой строки
    String courierFirstName = RandomStringUtils.randomAlphabetic(10);


    //Служебный метод для создания полного набора данных для регистрации нового курьера
    @Step("Create full courier random test registration data")
    public String createFullCourierRandomTestRegData() {
        String regDataFull = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        return regDataFull;
    }

    //Служебный метод для создания набора данных для регистрации нового курьера без генерации логина
    @Step("Create courier random test registration data without login")
    public String createCourierRandomTestRegDataWithoutLogin() {
        String regDataWithoutLogin = "{\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        return regDataWithoutLogin;
    }

    //Служебный метод для создания набора данных для регистрации нового курьера без генерации пароля
    @Step("Create courier random test registration data without password")
    public String createCourierRandomTestRegDataWithoutPassword() {
        String regDataWithoutPassword = "{\"login\":\"" + courierLogin + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        return regDataWithoutPassword;
    }

    //Служебный метод для создания набора данных для регистрации нового курьера без генерации имени курьера
    @Step("Create courier random test registration data without firstname")
    public String createCourierRandomTestRegDataWithoutFirstname() {
        String regDataWithoutFirstname = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\"}";
        return regDataWithoutFirstname;
    }

    //Служебный метод по сборке данных для запроса на получение id созданного курьера
    @Step("Prepare courier data for id request")
    public String prepareCourierDataForIdRequest() {
        String dataForCourierIdRequest = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\"}";
        return dataForCourierIdRequest;
    }

    //Служебный метод по сборке данных для запроса на получение id созданного курьера без передачи логина
    @Step("Prepare courier data without Login for id request")
    public String prepareCourierDataWithoutLoginForIdRequest() {
        String dataWithoutLoginForCourierIdRequest = "{\"login\":\"" + "\","
                + "\"password\":\"" + courierPassword + "\"}";
        return dataWithoutLoginForCourierIdRequest;
    }

    //Служебный метод по сборке данных для запроса на получение id созданного курьера без передачи пароля
    @Step("Prepare courier data without Password for id request")
    public String prepareCourierDataWithoutPasswordForIdRequest() {
        String dataWithoutPasswordForCourierIdRequest = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + "\"}";
        return dataWithoutPasswordForCourierIdRequest;
    }

    //Служебный метод по сборке данных для запроса на получение id созданного курьера с передачей некорректного логина
    @Step("Prepare courier data with incorrect Login for id request")
    public String prepareCourierDataWithIncorrectLoginForIdRequest() {
        String dataForCourierWithIncorrectLoginIdRequest = "{\"login\":\"" + courierLogin + "IncorrectText" + "\","
                + "\"password\":\"" + courierPassword + "\"}";
        return dataForCourierWithIncorrectLoginIdRequest;
    }

    //Служебный метод по сборке данных для запроса на получение id созданного курьера с передачей некорректного пароля
    @Step("Prepare courier data with incorrect Password for id request")
    public String prepareCourierDataWithIncorrectPasswordForIdRequest() {
        String dataForCourierWithIncorrectPasswordRequest = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "IncorrectText" + "\"}";
        return dataForCourierWithIncorrectPasswordRequest;
    }

    //Служебный метод для отправки запроса на создание нового курьера.
    //В качестве аргумента принимает один из тестовых методов по подготовке тестовых данных regData
    @Step("Create new courier request sending")
    public Response createNewCourierAPIRequest(String regDataVariant) {
        Response newCourierResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(regDataVariant)
                .when()
                .post(createCourierEndpointURL);
        return newCourierResponse;
    }

    //Служебный метод для запроса id курьера (авторизация)
    @Step("Request courier id (/api/v1/courier/login endpoint)")
    public Response requestCourierId(String bodyVariantForIdRequest) {
        Response courierIdResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyVariantForIdRequest)
                .when()
                .post(requestCourierIdEndpointURL);
        return courierIdResponse;
    }

    //Служебный метод на удаление курьера
    @Step("Delete courier by id (/api/v1/courier/:id endpoint)")
    public Response deleteCourierById(String courierIdNumber) {
        Response courierDeleteResponse = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(deleteCourierEndpointURL + courierIdNumber);
        return courierDeleteResponse;
    }

}