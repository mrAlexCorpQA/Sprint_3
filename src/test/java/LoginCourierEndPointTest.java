import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class LoginCourierEndPointTest {

    Courier scooterRegisterCourier;

    @Before
    public void setUp() {
        scooterRegisterCourier = new Courier();
    }

    //Служебный метод по удалению созданных курьеров после прохождения тестов
    @After
    public void deleteCreatedCourierAfterTest() {
        Response courierIdNumberForDeletion = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataForIdRequest());
        int courierIdResponseStatusCode = courierIdNumberForDeletion.statusCode();
        if (courierIdResponseStatusCode == 200) {
            String courierIdForDeletion = courierIdNumberForDeletion.then().extract().path("id").toString();
            scooterRegisterCourier.deleteCourierById(courierIdForDeletion);
        }
    }

    //Проверка авторизации курьера
    @Test
    @DisplayName("Courier authorization")
    @Description("New courier authorization test for /api/v1/courier/login endpoint")
    public void courierAutoriszationAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response CourierIdResponse = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataForIdRequest());
        CourierIdResponse.then().assertThat().body("id", notNullValue())
                .and().statusCode(200);
    }

    //Проверка авторизации курьера без передачи логина
    @Test
    @DisplayName("Courier authorization without Login")
    @Description("New courier authorization test without Login for /api/v1/courier/login endpoint")
    public void courierAutoriszationWithoutLoginAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response CourierIdResponse = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataWithoutLoginForIdRequest());
        CourierIdResponse.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }

    //Проверка авторизации курьера без передачи пароля
    @Test
    @DisplayName("Courier authorization without Password")
    @Description("New courier authorization test without Password for /api/v1/courier/login endpoint")
    public void courierAutoriszationWithoutPasswordAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response CourierIdResponse = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataWithoutPasswordForIdRequest());
        CourierIdResponse.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }

    //Проверка авторизации курьера с передачей некорректного логина но корректного пароля
    @Test
    @DisplayName("Courier authorization with incorrect Login")
    @Description("New courier authorization test with incorrect Login for /api/v1/courier/login endpoint")
    public void courierAutoriszationWithIncorrectLoginAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response CourierIdResponse = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataWithIncorrectLoginForIdRequest());
        CourierIdResponse.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(404);
    }

    //Проверка авторизации курьера с передачей некорректного пароля но корректного логина
    @Test
    @DisplayName("Courier authorization with incorrect Password")
    @Description("New courier authorization test with incorrect Password for /api/v1/courier/login endpoint")
    public void courierAutoriszationWithIncorrectPasswordAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response CourierIdResponse = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataWithIncorrectPasswordForIdRequest());
        CourierIdResponse.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(404);
    }

}