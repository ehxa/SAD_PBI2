<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="SADPBI2.RunWeka" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>PBI2 - Association Rules</title>
</head>
<body>
    <h2>PBI2 - Association Rules</h2>

    <%
        RunWeka runWeka = new RunWeka();
        List<List<String>> tasks = runWeka.apriori(20, 0.5, 0.05);
        out.println("<p>Number of rules: 20</p>");
        out.println("<p>Level of confidence: 0.5</p>");
        out.println("<p>Minimum support: 0.05</p>");
        for (int i = 1; i < tasks.size(); i++) {
            List<String> associations = tasks.get(i);
            out.println("<h2>TASKDATA" + i + "</h2>");
            for (String association : associations) {
                out.println("<p>" + association + "</p>");
            }
        }
    %>

</body>
</html>
