package testData.API;

import actions.commons.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;

@Data
public class GenDataCustomer {

    public static GenDataCustomer getDataJson() {

        String fileName = GlobalConstants.API_TEST_DATA_FOLDER + "Customer.json";

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(fileName), GenDataCustomer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @JsonProperty("cmpNo")
    String cmpNo;

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
    String mobileNumber;

    @JsonProperty("emailAddress")
    String emailAddress;

    @JsonProperty("emailAddressMobile")
    String emailAddressMobile;

    @JsonProperty("trinityCdOfN2")
    String trinityCdOfN2;

    @JsonProperty("trinityBuildingCdOfN2")
    String trinityBuildingCdOfN2;

    @JsonProperty("sex")
    String sex;

    @JsonProperty("birthDate")
    String birthDate;

    @JsonProperty("registerDivision")
    String registerDivision;
}
