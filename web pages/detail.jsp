<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>


<%
String Device = request.getParameter("Device");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SDHdetail</title>
<link href="css/layout2.css" rel="stylesheet" type="text/css" />
</head>

<body>

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost:3306/sdh_warning"
user="root"  password="1235831459437"/>

<sql:query dataSource="${snapshot}" var="result">
	SELECT * from statistics where Device =?   
<sql:param value="<%=Device %>"></sql:param>
</sql:query>




<div id="container">
  <div id="header">Check out the detail status!</div>
  <div class="clearfloat"></div>
  <div id="maincontent">
    <div id="pie1">
      <div id="piecont1">
	  <script type="text/javascript">
 		var Alarm = new Array();  
		<c:forEach  var="row" items="${result.rows}" varStatus="stat"> 
		<c:if test="${stat.last}">
			Alarm.push("${row.AllAlarm}");
			Alarm.push("${row.CAlarm}");
			Alarm.push("${row.MAlarm}");
			Alarm.push("${row.NAlarm}");
			Alarm.push("${row.WAlarm}");
			Alarm.push("${row.TeleAlarm}");
			Alarm.push("${row.DeviAlarm}");
			Alarm.push("${row.QoSAlarm}");
			Alarm.push("${row.ProcAlarm}");
			Alarm.push("${row.EnviAlarm}");
			Alarm.push("${row.SecuAlarm}");
		</c:if>
		</c:forEach>
	  </script>
      <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
      <script type="text/javascript" src="js/pie1.js"></script>
      </div>
      <div id="ex1">
        <table height="170" cellspacing="10">
        <tr>
        <td width="30" height="30" style="background-color:#FF0000"></td>
        <td width="50">Critical</td>
        </tr>
        <tr>
        <td  width="30" height="30" style="background-color:#FF34B3"></td>
        <td>Main</td>
        </tr>
        <tr>
        <td width="30" height="30" style="background-color:#FFD700"></td>
        <td>Minor</td>
        </tr>
        <tr>
        <td  width="30" height="30" style="background-color:#FFEFD5"></td>
        <td>Warning</td>
        </tr>
        </table>
      </div>

    </div>
    <div id="pie2">
      <div id="piecont2">
        <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
        <script type="text/javascript" src="js/pie2.js"></script>
      </div>
      <div id="ex2">
        <table height="170" cellspacing="10">
          <tr>
            <td width="30" height="30" style="background-color:#FF0000"></td>
            <td width="50">Tele.</td>
            <td width="30" height="30" style="background-color:#9400D3"></td>
            <td width="50">Envi.</td>
          </tr>
          <tr>
            <td  width="30" height="30" style="background-color:#FF34B3"></td>
            <td>Devi.</td>
            <td width="30" height="30" style="background-color:#8B0000"></td>
            <td width="50">Secu.</td>
          </tr>
          <tr>
            <td width="30" height="30" style="background-color:#FFD700"></td>
            <td>QoS.</td>
          </tr>
          <tr>
            <td  width="30" height="30" style="background-color:#FFEFD5"></td>
            <td>Proc.</td>
          </tr>
        </table>
      </div>

    </div>
    <div class="clearfloat"></div>
    <div id="other">
      <div id="up">
        <div id="place">
          <p>Places may have problems:</p>
		  <c:forEach  var="row" items="${result.rows}" varStatus="stat"> 
			<c:if test="${stat.last}"> 
				<c:if test="${row.MainPlace1!='null'}">
					<p><c:out value="${row.MainPlace1}"/></p>
				</c:if>
				<c:if test="${row.MainPlace2!='null'}">
					<p><c:out value="${row.MainPlace2}"/></p>
				</c:if>				
				<c:if test="${row.MainPlace3!='null'}">
					<p><c:out value="${row.MainPlace3}"/></p>
				</c:if>
			</c:if>
		  </c:forEach>

        </div>
        <div id="top5">
          <p>Top 5 alarms:</p>
		  <c:forEach  var="row" items="${result.rows}" varStatus="stat"> 
			<c:if test="${stat.last}">  
				<c:if test="${row.MainAlarm1!='null'}">
					<p><c:out value="${row.MainAlarm1}"/></p>
				</c:if>
				<c:if test="${row.MainAlarm2!='null'}">
					<p><c:out value="${row.MainAlarm2}"/></p>
				</c:if>
				<c:if test="${row.MainAlarm3!='null'}">
					<p><c:out value="${row.MainAlarm3}"/></p>
				</c:if>
				<c:if test="${row.MainAlarm4!='null'}">
					<p><c:out value="${row.MainAlarm4}"/></p>
				</c:if>
				<c:if test="${row.MainAlarm5!='null'}">
					<p><c:out value="${row.MainAlarm5}"/></p>
				</c:if>
			</c:if>

		  </c:forEach>

        </div>
      </div>
      <div class="clearfloat"></div>
      <div id="down">
      	<div id="rule">
          <p>Relative alarms:</p>
		  <c:forEach  var="row" items="${result.rows}" varStatus="stat"> 
			<c:if test="${stat.last}">
			
			
				<c:if test="${row.Relative1!='null'}">
					<p><c:out value="${row.Relative1}"/></p>
				</c:if>
				<c:if test="${row.Relative2!='null'}">
					<p><c:out value="${row.Relative2}"/></p>
				</c:if>
				<c:if test="${row.Relative3!='null'}">
					<p><c:out value="${row.Relative3}"/></p>
				</c:if>
				<c:if test="${row.Relative4!='null'}">
					<p><c:out value="${row.Relative4}"/></p>
				</c:if>
				<c:if test="${row.Relative5!='null'}">
					<p><c:out value="${row.Relative5}"/></p>
				</c:if>
			</c:if>
		  </c:forEach>
        </div>
        <div class="clearfloat"></div>
        <div id="control">
        
        <table>
          <tr>
          <td width="300" height="100">
            <center>
            <input type="button" class="ref" onclick="javascript:window.location.href='http://localhost:8080/SDH/index.jsp'" value="return"/>
            </center> 
          </td>
          <td width="300" height="100">
            <center>
            <input type="button" class="ref" onclick="javascript:window.location.reload() " value="update"/>
            </center> 
          </td>
          <td width="300" height="100">
            <center>
            <input type="button" class="ref" onclick="jump()" value="history"/>
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
					window.location.href="http://localhost:8080/SDH/detail2.jsp?Device="+getParam("Device");
				}
			</script>
            </center> 
          </td>
          </tr>
        </table>
        
		</div>
      
      </div>
    </div>
  </div>
  <div class="clearfloat"></div>
  <div id="footer">Designed by Gu Xinmeng</div>
</div>
</body>
</html>
