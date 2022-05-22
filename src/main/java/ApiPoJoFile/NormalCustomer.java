package ApiPoJoFile;

import actions.commons.BaseTest;
import testData.API.GetDataCustomer;

public class NormalCustomer extends BaseTest {

    public int cmpNo;
    public String userId;
    public String password;
    public String userLastName;
    public String userFirstName;
    public String userLastNameKana;
    public String userFirstNameKana;
    public int mobileNumber;
    public String emailAddress;
    public String emailAddressMobile;
    public int trinityCd;
    public int trinityBuildingCd;
    public int sex;
    public int birthDate;
    public int registerDivision;

    public NormalCustomer() {
        GetDataCustomer getDataCustomer = GetDataCustomer.getDataJson();

        this.cmpNo = 1 + generateNumber();
        this.userId = "test" + generateNumber() + "@example.com";
        this.password = getDataCustomer.getPassword();
        this.userLastName = getDataCustomer.getUserLastName();
        this.userFirstName = getDataCustomer.getUserFirstName();
        this.userLastNameKana = getDataCustomer.getUserLastNameKana();
        this.userFirstNameKana = getDataCustomer.getUserFirstNameKana();
        this.mobileNumber = getDataCustomer.getMobileNumber();
        this.emailAddress = getDataCustomer.getEmailAddress();
        this.emailAddressMobile = getDataCustomer.getEmailAddressMobile();
        this.trinityCd = getDataCustomer.getTrinityCd();
        this.trinityBuildingCd = getDataCustomer.getTrinityBuildingCd();
        this.sex = getDataCustomer.getSex();
        this.birthDate = getDataCustomer.getBirthDate();
        this.registerDivision = getDataCustomer.getRegisterDivision();
    }

    public static NormalCustomer getInstance() {
        return new NormalCustomer();
    }

}
