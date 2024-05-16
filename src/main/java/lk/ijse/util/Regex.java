package lk.ijse.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean isTextFieldValid(TextFields textField , String text){
        String field = "";


        switch (textField) {

            case CliID :
                field = "^[C][0-9]{3,4}$";
                break;

            case EmpAva :
                field = "^(yes|no)$";
                break;
            case EmpType:
                field = "^(Others|Driver|Guide)$";
                break;
            case EmployeeID :
                field = "^[E][0-9]{3,4}$";
                break;
            case PackageID:
                //Done
                field = "^[P][0-9]{3,4}$";
                break;
            case NIC:
                field = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
                break;
            case NAME:
                //Done
                field = "^[A-z|\\s]{3,}$";
                break;
            case EMAIL:
                field = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case ADDRESS:
                field = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;
            case PHONE:
                field = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case DOUBLE:
                field = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case INVOICE:
                field = "^([0-9]){1,}$";
                break;
            case NONE_CHARACTER:
                field = "^[\\W]{1,}$";
                break;
            case INT:
                field = "^\\d+$";
                break;
            case USERNAME:
                field = "^[A-Za-z]+[A-Za-z0-9]*$";
                break;
            case PASSWARD:
                field = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=|{}\\[\\]:;<>,.?]).{8,}$";
                break;
            case ONLY_LETTERS:
                field = "^[a-zA-Z]+$";
                break;
            case LETTERS_AND_NUMBERS:
                field = "^[a-zA-Z0-9 ]+$";
                break;
            case DATE :
                field = "^(\\d{4}-\\d{2}-\\d{2})$";
                break;

            case PayId :
                field = "^PA[0-9]{3,4}$";
                break;
            case PayMethod :
                field = "^(Cash|Card)$";
                break;
            case PayStatus :
                field = "^(Paid|Not Paid)$";
                break;

            case rooID :
                field = "^[R][0-9]{3,4}$";
                break;
            case rooStatus :
                field = "^(Booked|Available)$";
                break;
            case VehicId :
                field = "^[V][0-9]{3,4}$";
                break;

            case VehicActive :
                field = "^(Active|Inavtive)$";
                break;
            case Bookigid :
                field = "^[B][0-9]{3,4}$";
                break;
            case ResID :
                field = "^RE[0-9]{3,4}$";

            break;

        }
        Pattern pattern =Pattern.compile(field);

        if (text != null) {
            if (text.trim().isEmpty()){
                return  false;
            }
        }else {
            return false;
        }
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;

    }

    public static boolean setTextColor(TextFields location, TextField textField) {
        if (Regex.isTextFieldValid(location, textField.getText())) {
            textField.setStyle("-fx-text-fill: black;");
            return true;
        } else {
            textField.setStyle("-fx-text-fill: Red;");
            return false;
        }
    }



}
