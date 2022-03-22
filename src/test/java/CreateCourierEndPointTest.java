import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class CreateCourierEndPointTest {

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

    //Проверка создания нового курьера
    @Test
    @DisplayName("Create new courier")
    @Description("New courier creation test for /api/v1/courier endpoint")
    public void createNewCourierAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
    }

    //Проверка удаления нового курьера (технический тест, необходим для дальнейшего использования в других тестах по проверке API курьера)
    @Test
    @DisplayName("Delete created courier (Service test)")
    @Description("Delete created courier (service test for /api/v1/courier/:id endpoint)")
    public void deleteCreatedCourierAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response courierId = scooterRegisterCourier.requestCourierId(scooterRegisterCourier.prepareCourierDataForIdRequest());
        courierId.then().assertThat().body("id", notNullValue())
                .and().statusCode(200);
        String courierIdNumber = courierId.then().extract().path("id").toString();
        Response deleteCourierResponse = scooterRegisterCourier.deleteCourierById(courierIdNumber);
        deleteCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(200);
    }

    //Проверка создания двух одинаковых курьеров
    @Test
    @DisplayName("Create two same couriers")
    @Description("Creation of two same couriers test for /api/v1/courier endpoint")
    public void createTwoSameCouriersAPITest() {
        Response newCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        newCourierResponse.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(201);
        Response secondSendTryCourierResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createFullCourierRandomTestRegData());
        secondSendTryCourierResponse.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(409);
    }

    //Проверка создания нового курьера без передачи логина
    @Test
    @DisplayName("Create new courier without Login")
    @Description("New courier creation test without Login for /api/v1/courier endpoint")
    public void createNewCourierWithoutLoginAPITest() {
        Response newCourierWithoutLoginResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createCourierRandomTestRegDataWithoutLogin());
        newCourierWithoutLoginResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    //Проверка создания нового курьера без передачи пароля
    @Test
    @DisplayName("Create new courier without Password")
    @Description("New courier creation test without Password for /api/v1/courier endpoint")
    public void createNewCourierWithoutPasswordAPITest() {
        Response newCourierWithoutPasswordResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createCourierRandomTestRegDataWithoutPassword());
        newCourierWithoutPasswordResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    //Проверка создания нового курьера без передачи имени
    //ВАЖНО: Нового курьера можно создать без передачи поля firstname. По согласованию с наставником группы тест написан с учетом того, что это баг
    @Test
    @DisplayName("Create new courier without Firstname")
    @Description("New courier creation test without Firstname for /api/v1/courier endpoint")
    public void createNewCourierWithoutFirstnameAPITest() {
        Response newCourierWithoutFirstnameResponse = scooterRegisterCourier.createNewCourierAPIRequest(scooterRegisterCourier.createCourierRandomTestRegDataWithoutFirstname());
        newCourierWithoutFirstnameResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

}