package com.calendlygui.utils;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.calendlygui.CalendlyApplication;

import static com.calendlygui.constant.ConstantValue.*;
import static com.calendlygui.utils.Helper.createRequest;
import static com.calendlygui.utils.Helper.createRequest2222;
public class SendData {

    private static String request;
    String token = CalendlyApplication.token;

    private static ArrayList<String> data = new ArrayList<>();

    public static void login(PrintWriter out, String account, String password) {
        data.clear();
        data.add(account);
        data.add(password);
        String token = CalendlyApplication.token;
        request = createRequest2222(LOGIN, data);
        out.println(request);
    }

    public static void register(PrintWriter out, String username, String email, String password, boolean isMale, boolean isTeacher) {
        data.clear();
        data.add(username);
        data.add(email);
        data.add(password);
        data.add(isMale ? "true" : "false");
        data.add(isTeacher ? "true" : "false");
        String token = CalendlyApplication.token;

        request = createRequest2222(REGISTER, data);
        out.println(request);
    }

    public static void viewAvailableSlots(PrintWriter out, int sId, String token) {
        data.clear();
        data.add(String.valueOf(sId));
        request = createRequest(STUDENT_VIEW_TIMESLOT, data, token);
        out.println(request);
    }

    public static void viewAvaiforname(PrintWriter out, String username, String token) {
        data.clear();
        data.add(username);
        request = createRequest(SEARCH, data, token);
        out.println(request);
    }

    public static void scheduleMeeting(PrintWriter out, int sId, int mId, String type, String token) throws ParseException {
        data.clear();
        data.add(String.valueOf(sId));
        data.add(String.valueOf(mId));
        data.add(type);
        request = createRequest(STUDENT_SCHEDULE_MEETING, data, token);
        out.println(request);
    }

    public static void viewScheduledMeeting(PrintWriter out, int sId, String token) {
        data.clear();
        data.add(String.valueOf(sId));
        request = createRequest(STUDENT_VIEW_SCHEDULED, data, token);
        out.println(request);
    }

    public static void cancelMeeting(PrintWriter out, int sId, int mId, String token) {
        data.clear();
        data.add(String.valueOf(sId));
        data.add(String.valueOf(mId));
        request = createRequest(STUDENT_CANCEL_MEETING, data, token);
        out.println(request);
    }

    public static void createMeeting(PrintWriter out, String name, String dateTime, String begin, String end, String classification, int tId, String token) {
        data.clear();
        data.add(String.valueOf(tId));
        data.add(name);
        data.add(dateTime);
        data.add(begin);
        data.add(end);
        data.add(classification);
        request = createRequest(TEACHER_CREATE_MEETING, data, token);
        out.println(request);
    }

    public static void viewStudentScheduledMeetings(PrintWriter out, int tId, String token) {
        data.clear();
        data.add(String.valueOf(tId));
        request = createRequest(TEACHER_VIEW_MEETING, data, token);
        out.println(request);
    }

    public static void editMeeting(PrintWriter out, int id, String name, String dateTime, String begin, String end, String status, String classification, String selectedClassification, int tId, String token) {
        data.clear();
        data.add(String.valueOf(id));
        data.add(name);
        data.add(dateTime);
        data.add(begin);
        data.add(end);
        data.add(status);
        data.add(classification);
        data.add(selectedClassification);
        data.add(String.valueOf(tId));
        request = createRequest(TEACHER_EDIT_MEETING, data, token);
        out.println(request);
    }

    public static void addContent(PrintWriter out, int mId, String content, String token) {
        data.clear();
        data.add(String.valueOf(mId));
        data.add(content);
        request = createRequest(TEACHER_ENTER_CONTENT, data, token);
        out.println(request);
    }

    public static void viewHistory(PrintWriter out, int tId, String token) {
        data.clear();
        data.add(String.valueOf(tId));
        request = createRequest(TEACHER_VIEW_HISTORY, data, token);
        out.println(request);
    }
}
