package com.gorest.crudtest;
import com.gorest.info.UserSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {
    static String name = "Sarah" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "abc@gmail.com";
    static String gender = "female";
    static String status = "active";
    static int uid;
    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(name, email, gender, status);

    }
    @Title("Verify if the user has added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap, hasValue(name));
        uid = (int) userMap.get("id");

    }
    @Title("This will get the Product with id ")
    @Test
    public void test003(){
        userSteps.getUserById(uid).log().all().statusCode(200);
    }

    @Title("Update the user information and verify the updated information")
    @Test
    public void test004() {
        name = name + "_updated" + TestUtils.getRandomValue();
        userSteps.updateUser(uid, name, email, gender, status);
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap, hasValue(name));
    }
    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test005() {
        userSteps.deleteUser(uid).statusCode(204);
        userSteps.getUserById(uid).statusCode(404);
    }

}
