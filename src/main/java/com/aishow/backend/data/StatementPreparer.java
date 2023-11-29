package com.aishow.backend.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import com.aishow.backend.models.ClientServiceInteraction;
import com.aishow.backend.models.ServiceInformation;
import com.aishow.backend.models.UserInformation;

//TODO #26 organizar essa porra
//TODO #31 ottimizar consultas que são feita em duas partes

public class StatementPreparer {

    //USERS
    public static PreparedStatement matchUserCredentials(Connection conn, UserInformation info) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM user WHERE user.email = ? AND user.password = ?");
        st.setString(1, info.getEmail());
        st.setString(2, info.getPassword());
        return st;
    }

    public static PreparedStatement getUserById(Connection conn, int id) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM user WHERE user.idUser = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getUserByEmail(Connection conn, String email) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM user WHERE user.email = ?");
        st.setString(1, email);
        return st;
    }

    public static PreparedStatement createUser(Connection conn, UserInformation info) throws SQLException{
        PreparedStatement st = conn.prepareStatement("INSERT INTO user (name,email,password,birthday,profileUrl,gender,area)"+
        "VALUES(?,?,?,?,?,?,?)");
        st.setString(1, info.getName());
        st.setString(2, info.getEmail());
        st.setString(3, info.getPassword());
        st.setDate(4, info.getBirthday());
        st.setString(5, "0.png");
        st.setString(6, info.getGender());
        st.setInt(7, info.getArea().Id);
        return st;
    }

    public static PreparedStatement updateUserName(Connection conn, int id, String newVarchar) throws SQLException{
        PreparedStatement st = conn.prepareStatement("UPDATE user SET name = ? WHERE idUser = ?");
        st.setString(1, newVarchar);
        st.setInt(2, id);
        return st;
    }

    public static PreparedStatement updateUserImageUrl(Connection conn, int id, String newVarchar) throws SQLException{
        PreparedStatement st = conn.prepareStatement("UPDATE user SET profileUrl = ? WHERE idUser = ?");
        st.setString(1, newVarchar);
        st.setInt(2, id);
        return st;
    }

    public static PreparedStatement updateUserArea(Connection conn, int idUser, int idArea) throws SQLException{
        PreparedStatement st = conn.prepareStatement("UPDATE user SET area = ? WHERE idUser = ?");
        st.setInt(1, idArea);
        st.setInt(2, idUser);
        return st;
    }

    public static PreparedStatement getUserProfileImageUrlById(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT profileUrl FROM user WHERE idUser = ?");
        st.setInt(1, id);
        return st;
    }

    //SERVICES/TEMPLATES

    public static PreparedStatement getServicesByProviderId(Connection conn, int id) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM servicetemplates WHERE idProvider = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getServiceById (Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT idProvider, idServiceTemplates, costPerHour, description, serviceName, templateImageUrl,serviceModality,serviceCategory FROM servicetemplates WHERE idServiceTemplates = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement createService(Connection conn, ServiceInformation info) throws SQLException{
        PreparedStatement st = conn.prepareStatement("INSERT INTO servicetemplates (idProvider,serviceName,description,costPerHour,serviceModality,serviceCategory,templateImageUrl)"+
        "VALUES (?,?,?,?,?,?,?)");
        st.setInt(1, info.getProvider().getUserId());
        st.setString(2, info.getServiceName());
        st.setString(3, info.getDescription());
        st.setFloat(4, info.getCostPerHour());
        st.setInt(5, info.getModality());
        st.setInt(6, info.getCategory());
        st.setString(7, "0.png");
        return st;
    }

    public static PreparedStatement updateService(Connection conn, ServiceInformation info) throws SQLException{
        PreparedStatement st = conn.prepareStatement("UPDATE servicetemplates "+
        "SET serviceModality = ?, serviceCategory = ?, description = ?, serviceName = ?, costPerHour = ? "+
        "WHERE idServiceTemplates = ?");
        st.setInt(1, info.getModality());
        st.setInt(2, info.getCategory());
        st.setString(3, info.getDescription());
        st.setString(4, info.getServiceName());
        st.setFloat(5, info.getCostPerHour());
        st.setInt(6, info.getTemplateId());
        return st;
    }

    public static PreparedStatement updateTemplateImageUrl(Connection conn, int id, String newVarchar) throws SQLException{
        PreparedStatement st = conn.prepareStatement("UPDATE servicetemplates SET templateImageUrl = ? WHERE idServiceTemplates = ?");
        st.setString(1, newVarchar);
        st.setInt(2, id);
        return st;
    }

    public static PreparedStatement getLastCreatedServiceByProviderId(Connection conn, int creator) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ? ORDER BY idServiceTemplates DESC LIMIT 1");
        st.setInt(1, creator);
        return st;
    }

    public static PreparedStatement getServiceImageUrlById(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT templateImageUrl FROM servicetemplates WHERE idServiceTemplates = ?");
        st.setInt(1, id);
        return st;
    }

    //AVAILABILITY

    public static PreparedStatement getAvailabilityByTemplateId(Connection conn, int templateId) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM serviceavailability WHERE templateID = ?");
        st.setInt(1, templateId);
        return st;
    }

    public static PreparedStatement addAvailability(Connection conn,int templateId, int weekday, Time from, Time to) throws SQLException{
        PreparedStatement st = conn.prepareStatement("INSERT INTO serviceavailability (templateID, weekday,startHour,endHour) VALUES (?,?,?,?)");
        st.setInt(1, templateId);
        st.setInt(2, weekday);
        st.setTime(3, from);
        st.setTime(4, to);
        return st;
    }

    public static PreparedStatement deleteleAvailabilityById(Connection conn, int id) throws SQLException{
        PreparedStatement st = conn.prepareStatement("DELETE FROM serviceavailability WHERE serviceAvailabilityID = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement deleteleAvailabilityByTemplateId(Connection conn, int id) throws SQLException{
        PreparedStatement st = conn.prepareStatement("DELETE FROM serviceavailability WHERE templateID = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getAvailabilityAtFrame(Connection conn, ClientServiceInteraction info) throws SQLException {
         PreparedStatement st = conn.prepareStatement("SELECT startTime, endTime FROM serviceinstances WHERE (templateID IN (SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ?) OR clientID = ?) AND" + //
                "((DATE(?) BETWEEN startDate AND endDate) AND ((TIME(?) BETWEEN startTime AND endTime) OR (TIME(?) BETWEEN startTime AND endTime))) ORDER BY startDate, startTime");
         st.setInt(1, info.getService().getProvider().getUserId());
        st.setInt(2, info.getService().getProvider().getUserId());
        //ISSO NAO ACOMODA PRA MULTIPLOS DIAS
        st.setString(3, info.getStartDate());
        st.setTime(4, info.getStartTime());
        st.setTime(5, info.getEndTime());
        return st;
    }

    //REVIEW

    public static PreparedStatement createServiceReview(Connection conn,int reviewer, int target, int score, String comment) throws SQLException {
        PreparedStatement st = conn.prepareStatement("INSERT INTO servicereviews VALUES (?,?,?,?)");
        st.setInt(1, reviewer);
        st.setInt(2, target);
        st.setInt(3, score);
        st.setString(4, comment);
        return st;
    }

    public static PreparedStatement createUserReview(Connection conn,int reviewer, int target, int score, String comment) throws SQLException {
        PreparedStatement st = conn.prepareStatement("INSERT INTO userreviews VALUES (?,?,?,?)");
        st.setInt(1, reviewer);
        st.setInt(2, target);
        st.setInt(3, score);
        st.setString(4, comment);
        return st;
    }

    public static PreparedStatement getAllReviewsToUser(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT idclient, score, comment FROM userreviews WHERE idtarget = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getAllReviewsToService (Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT idclient, score, comment FROM servicereviews WHERE idtemplate = ?");
        st.setInt(1, id);
        return st;
    }

    //Generic Info

    public static PreparedStatement getAllGenericInformation(Connection conn,String table) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM "+table);
        return st;
    }

    public static PreparedStatement getGenericInformationById(Connection conn, String table,String idCol, int id) throws SQLException{
        PreparedStatement st = conn.prepareStatement("SELECT * FROM "+table+" WHERE "+idCol+" = ?");
        st.setInt(1, id);
        return st;
    }

    //REQUESTS AND INSTANCES

    public static PreparedStatement createServiceRequest (Connection conn, ClientServiceInteraction info) throws SQLException {
        PreparedStatement st = conn.prepareStatement("INSERT INTO servicerequests (templateID, clientID, startDate, endDate, startTime, endTime,cost)"+
        "VALUES (?,?,?,?,?,?,?)");
        st.setInt(1, info.getService().getTemplateId());
        st.setInt(2, info.getClient().getUserId());
        st.setString(3, info.getStartDate());
        st.setString(4, info.getEndDate());
        st.setTime(5, info.getStartTime());
        st.setTime(6, info.getEndTime());
        st.setFloat(7, info.getCost());
        return st;
    }

    public static PreparedStatement getServiceRequestsByRequesterId(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM servicerequests WHERE clientID = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getNonCompletedInstancesByProviderId(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM serviceinstances WHERE templateID IN (SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ?) AND finished = false ORDER BY startDate, startTime");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getNonCompletedInstancesByClientId(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM serviceinstances WHERE clientID = ? AND finished = false ORDER BY startDate, startTime");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement setDueInstancesAsCompleted(Connection conn) throws SQLException {
        PreparedStatement st = conn.prepareStatement("UPDATE serviceinstances SET finished = true WHERE endDate <= CURDATE() AND TIMEDIFF(CURTIME(),\"08:00:00\")>=\"00:30:00\" AND finished = false");
        return st;
    }

    public static PreparedStatement getRequestsByProviderId(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM servicerequests WHERE templateID IN"+ 
        "(SELECT idServiceTemplates FROM servicetemplates WHERE idProvider = ?) ORDER BY startDate, startTime");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getRequestsByClientId(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM servicerequests WHERE clientID = ? ORDER BY startDate, startTime");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement getRequestById(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM servicerequests WHERE serviceRequestID = ?");
        st.setInt(1, id);
        return st;
    }

    public static PreparedStatement createServiceInstance(Connection conn, ClientServiceInteraction info) throws SQLException {
        PreparedStatement st = conn.prepareStatement("INSERT INTO serviceinstances (clientID,templateID,startDate,endDate,startTime,endTime,cost)"+
        "VALUES (?,?,?,?,?,?,?)");
        st.setInt(1, info.getClient().getUserId());
        st.setInt(2, info.getService().getTemplateId());
        st.setDate(3, Date.valueOf(info.getStartDate()));
        st.setDate(4, Date.valueOf(info.getEndDate()));
        st.setTime(5, info.getStartTime());
        st.setTime(6, info.getEndTime());
        st.setFloat(7, info.getCost());
        return st;
    }

    public static PreparedStatement deleteServiceRequest(Connection conn, int id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("DELETE FROM servicerequests WHERE serviceRequestID = ?");
        st.setInt(1, id);
        return st;
    }

    //SEARCH

    public static PreparedStatement searchTemplates(Connection conn,String toSearch, int offset) throws SQLException {
        //toSearch = "'%" + toSearch + "%'";
        String query = "SELECT * FROM servicetemplates WHERE serviceName LIKE '%"+toSearch+"%' LIMIT 20 OFFSET "+offset;
        PreparedStatement st = conn.prepareStatement(query);
        //st.setString(1, toSearch);
        //st.setInt(1, offset);
        return st;
    }

    public static PreparedStatement searchUsers(Connection conn,String toSearch, int offset) throws SQLException {
        //toSearch = "'%" + toSearch + "%'";
        String query = "SELECT * FROM user WHERE name LIKE '%"+toSearch+"%' LIMIT 20 OFFSET "+offset;
        PreparedStatement st = conn.prepareStatement(query);
        //st.setString(1, toSearch);
        //st.setInt(1, offset);
        return st;
    }

}
