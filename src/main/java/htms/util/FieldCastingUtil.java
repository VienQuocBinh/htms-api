package htms.util;

import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.ClassStatus;
import htms.common.constants.EnrollmentStatus;
import htms.common.constants.ProfileStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
public class FieldCastingUtil {

    public Object castStringToFieldType(String stringValue, Class<?> fieldType) {
        if (fieldType.equals(String.class)) {
            return stringValue;
        } else if (fieldType.equals(UUID.class)) {
            return UUID.fromString(stringValue);
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.parseLong(stringValue);
        } else if (fieldType.equals(Date.class)) {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(stringValue, dateFormatter);
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.parseInt(stringValue);
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.parseDouble(stringValue);
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.parseBoolean(stringValue);
        } else if (fieldType.equals(ClassStatus.class)) {
            return switch (stringValue.toUpperCase()) {
                case "PLANNING" -> ClassStatus.PLANNING;
                case "CLOSED" -> ClassStatus.CLOSED;
                case "REJECT" -> ClassStatus.REJECT;
                case "ACCEPTED" -> ClassStatus.OPENING;
                case "PENDING" -> ClassStatus.PENDING;
                default ->
                    // todo: handle exceptions
                        throw new IllegalArgumentException("Unknown approval status " + stringValue);
            };
        } else if (fieldType.equals(ClassApprovalStatus.class)) {
            return switch (stringValue.toUpperCase()) {
                case "PENDING" -> ClassApprovalStatus.PENDING;
                case "REJECT_FOR_PUBLISHING" -> ClassApprovalStatus.REJECT_FOR_PUBLISHING;
                case "APPROVE_FOR_PUBLISHING" -> ClassApprovalStatus.APPROVE_FOR_PUBLISHING;
                case "REJECT_FOR_OPENING" -> ClassApprovalStatus.REJECT_FOR_OPENING;
                case "APPROVE_FOR_OPENING" -> ClassApprovalStatus.APPROVE_FOR_OPENING;
                default ->
                    // todo: handle exceptions
                        throw new IllegalArgumentException("Unknown approval status " + stringValue);
            };
        } else if (fieldType.equals(ProfileStatus.class)) {
            return switch (stringValue.toUpperCase()) {
                case "FINISHED" -> ProfileStatus.FINISHED;
                case "STUDYING" -> ProfileStatus.STUDYING;
                case "DROPPED_OUT" -> ProfileStatus.DROPPED_OUT;
                case "PENDING" -> ProfileStatus.PENDING;
                default ->
                    // todo: handle exceptions
                        throw new IllegalArgumentException("Unknown approval status " + stringValue);
            };
        } else if (fieldType.equals(EnrollmentStatus.class)) {
            return switch (stringValue.toUpperCase()) {
                case "REJECT" -> EnrollmentStatus.REJECT;
                case "APPROVE" -> EnrollmentStatus.APPROVE;
                case "PENDING" -> EnrollmentStatus.PENDING;
                default ->
                    // todo: handle exceptions
                        throw new IllegalArgumentException("Unknown approval status " + stringValue);
            };
        } else {
            // Handle unsupported field types or throw an exception
            throw new UnsupportedOperationException("Unsupported field type: " + fieldType.getName());
        }
    }
}
