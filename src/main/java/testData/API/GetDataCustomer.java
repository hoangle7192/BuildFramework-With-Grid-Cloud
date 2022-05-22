package testData.API;

import actions.commons.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;

@Data
public class GetDataCustomer {

    public static GetDataCustomer getDataJson() {

        String fileName = GlobalConstants.API_TEST_DATA_FOLDER + "Customer.json";

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(fileName), GetDataCustomer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @JsonProperty("cmpNo")
    int cmpNo;

    @JsonProperty("userId")
    String userId;

    @JsonProperty("password")
    String password;

    @JsonProperty("userLastName")
    String userLastName;

    @JsonProperty("userFirstName")
    String userFirstName;

    @JsonProperty("userLastNameKana")
    String userLastNameKana;

    @JsonProperty("userFirstNameKana")
    String userFirstNameKana;

    @JsonProperty("mobileNumber")
    int mobileNumber;

    @JsonProperty("emailAddress")
    String emailAddress;

    @JsonProperty("emailAddressMobile")
    String emailAddressMobile;

    @JsonProperty("trinityCd")
    int trinityCd;

    @JsonProperty("trinityBuildingCd")
    int trinityBuildingCd;

    @JsonProperty("sex")
    int sex;

    @JsonProperty("birthDate")
    int birthDate;

    @JsonProperty("registerDivision")
    int registerDivision;
}
