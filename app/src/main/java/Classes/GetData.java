package Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by hieuduong on 10/25/17.
 */

public class GetData {

    public String LogIn(String User, String Pass){

        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/MobileLogIn";
        final String METHOD_NAME = "MobileLogIn";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo PI = new PropertyInfo();
        PI.setName("Username");
        PI.setValue(User);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("Password");
        PI.setValue(Pass);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("SessionID");
        PI.setValue("abc");
        PI.setType(String.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String RegisterUser(String code, String firstName, String lastName, String email, String username, String password) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/Register";
        final String METHOD_NAME = "Register";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("Code");
        PI.setValue(code);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("FirstName");
        PI.setValue(firstName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("LastName");
        PI.setValue(lastName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("Email");
        PI.setValue(email);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("Username");
        PI.setValue(username);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("Password");
        PI.setValue(password);
        PI.setType(String.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetUsers() {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetUsers";
        final String METHOD_NAME = "GetUsers";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String AddCourse(String courseName, String courseSession, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/AddCourse";
        final String METHOD_NAME = "AddCourse";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("courseName");
        PI.setValue(courseName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("courseSession");
        PI.setValue(courseSession);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetCourses(int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetCourses";
        final String METHOD_NAME = "GetCourses";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetAllCourses(int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetAllCourses";
        final String METHOD_NAME = "GetAllCourses";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetChapters(int courseID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetChapters";
        final String METHOD_NAME = "GetChapters";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String AddChapter(String chapterName, String chapterDes, int courseID, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/AddChapter";
        final String METHOD_NAME = "AddChapter";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("chapterName");
        PI.setValue(chapterName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("chapterDescription");
        PI.setValue(chapterDes);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetAssignment(int chapterID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetAssignments";
        final String METHOD_NAME = "GetAssignments";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("chapterID");
        PI.setValue(chapterID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetAssignmentDetail(int aID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetAssignmentDetail";
        final String METHOD_NAME = "GetAssignmentDetail";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("aID");
        PI.setValue(aID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String AddAssignment(String aName, String aDes, int chapterID, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/AddAssignment";
        final String METHOD_NAME = "AddAssignment";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("aName");
        PI.setValue(aName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("aDescription");
        PI.setValue(aDes);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("chapterID");
        PI.setValue(chapterID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String UpdateAssignment(String aName, String aDes, int userID, int aID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/UpdateAssignment";
        final String METHOD_NAME = "UpdateAssignment";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("aName");
        PI.setValue(aName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("aDescription");
        PI.setValue(aDes);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("aID");
        PI.setValue(aID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetCourseDetail(int courseID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetCourseDetail";
        final String METHOD_NAME = "GetCourseDetail";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String UpdateCourse(String courseName, String courseSession, int courseID, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/UpdateCourse";
        final String METHOD_NAME = "UpdateCourse";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("courseName");
        PI.setValue(courseName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("courseSession");
        PI.setValue(courseSession);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String DeleteCourse(int courseID, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/DeleteCourse";
        final String METHOD_NAME = "DeleteCourse";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetChapterDetail(int chapterID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetChapterDetail";
        final String METHOD_NAME = "GetChapterDetail";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("chapterID");
        PI.setValue(chapterID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String UpdateChapter(String cName, String cDes, int chapterID, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/UpdateChapter";
        final String METHOD_NAME = "UpdateChapter";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("chapterName");
        PI.setValue(cName);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("chapterDescription");
        PI.setValue(cDes);
        PI.setType(String.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("chapterID");
        PI.setValue(chapterID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String DeleteChapter(int chapterID, int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/DeleteChapter";
        final String METHOD_NAME = "DeleteChapter";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("chapterID");
        PI.setValue(chapterID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetAssignedCourses(int userID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetAssignedCourses";
        final String METHOD_NAME = "GetAssignedCourses";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String GetAssignedAssignment(int userID, int courseID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/GetAssignedAssignment";
        final String METHOD_NAME = "GetAssignedAssignment";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public String AssignCourse(int userID, int courseID) {
        String response = "";

        final String NAMESPACE = "http:/myaddb.azurewebsites.net/";
        final String URL = "http://myaddb.azurewebsites.net/WebService.asmx";
        final String SOAP_ACTION = "http:/myaddb.azurewebsites.net/AssignCourse";
        final String METHOD_NAME = "AssignCourse";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo PI = new PropertyInfo();

        PI = new PropertyInfo();
        PI.setName("courseID");
        PI.setValue(courseID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        PI = new PropertyInfo();
        PI.setName("userID");
        PI.setValue(userID);
        PI.setType(Integer.class);
        request.addProperty(PI);

        response = SendData(request, URL, SOAP_ACTION);

        return response;
    }

    public static String SendData(SoapObject request, String URL, String SOAP_ACTION){

        int TimeOut = 3000;
        String response = "";

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transportSE = new HttpTransportSE(URL,TimeOut);

        try{
            transportSE.debug = true;
            transportSE.call(SOAP_ACTION, envelope);
            response = envelope.getResponse().toString();
            //response = transportSE.responseDump;

        }catch (Exception ex){
            ex.printStackTrace();
            Log.e("Error", ex.toString());
        }

        return response;
    }
}
