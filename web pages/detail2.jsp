<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SDHhistory</title>
<link href="css/layout3.css" rel="stylesheet" type="text/css" />
</head>

<%
String Device = request.getParameter("Device");
%>

<body>

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost:3306/sdh_warning"
user="root"  password="123456"/>
	
<sql:query dataSource="${snapshot}" var="result">
	SELECT * from statistics where Device =?   
<sql:param value="<%=Device %>"></sql:param>
</sql:query>


<div id="container">
  <div id="header">Check out the detail of your SDH device!</div>
  <div class="clearfloat"></div>
  <div id="maincontent">
    <div id="main">
      <div id="history">
        <div id="duizhan">
          <script type="text/javascript">
			var Da = new Array();
			var Time = new Array(); 
			var CAlarm = new Array();
			var MAlarm = new Array();
			var NAlarm = new Array();
			var WAlarm = new Array();
			<c:forEach  var="row" items="${result.rows}">  
				Da.push("${row.Date}");
	　		　	Time.push("${row.Time}");
				CAlarm.push("${row.CAlarm}");
				MAlarm.push("${row.MAlarm}");
				NAlarm.push("${row.NAlarm}");
				WAlarm.push("${row.WAlarm}");
			</c:forEach>
		  </script>
          <script src="http://d3js.org/d3.v3.min.js" charset="utf-8">
          </script>
          <script type="text/javascript" src="js/history.js"></script>
        
        </div>
        <div id="line">
          <script type="text/javascript" src="js/Line.js"></script>
        </div>
      </div>
      <div id="refresh">
              <table>
          <tr>
          <td width="400" height="100">
            <center>
            <input type="button" class="ref" onclick="javascript:window.location.href='http://localhost:8080/SDH/index.jsp'" value="return"/>
            </center> 
          </td>
          <td width="400" height="100">
            <center>
            <input type="button" class="ref" onclick="javascript:window.location.reload() " value="update"/>
            </center> 
          </td>
          <td width="400" height="100">
            <center>
            <input type="button" class="ref" onclick="jump()" value="latest"/>
            <script type="text/javascript">
				function getParam(paramName) { 
    				paramValue = "", isFound = !1; 
    				if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) 
					{ 
       			 		arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        				while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    				} 
    				return paramValue == "" && (paramValue = null), paramValue 
			  } 
				
				
				function jump()
				{
					window.location.href="http://localhost:8080/SDH/detail.jsp?Device="+getParam("Device");
				}
			</script>
            </center> 
          </td>
          </tr>
        </table>
        
      </div>
    </div>
  </div>
  <div class="clearfloat"></div>
  <div id="footer">Designed by Gu Xinmeng</div>
</div>

</body>
</html>
