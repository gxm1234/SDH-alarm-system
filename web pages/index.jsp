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
<title>SDHindex</title>
<link href="css/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost:3306/sdh_warning"
user="root"  password="1235831459437"/>
	
<sql:query dataSource="${snapshot}" var="result">
  SELECT * from statistics;
</sql:query>


<div id="container">
  <div id="header">Check out the status of your SDH device!</div>
  <div class="clearfloat"></div>
  <div id="maincontent">
    <div id="side">
      <table height="170" cellspacing="10">
      <tr>
      <td width="30" height="30" style="background-color:#FF0000"></td>
      <td width="50">Status 1</td>
      </tr>
      <tr>
      <td  width="30" height="30" style="background-color:#FF34B3"></td>
      <td>Status 2</td>
      </tr>
      <tr>
      <td width="30" height="30" style="background-color:#FFD700"></td>
      <td>Status 3</td>
      </tr>
      <tr>
      <td  width="30" height="30" style="background-color:#FFEFD5"></td>
      <td>Status 4</td>
      </tr>
      </table>
    </div>
    <div id="main">
      <div id="force">
	  
		<script type="text/javascript">
		
		

		  <c:forEach  var="row" items="${result.rows}" varStatus="stat"> 
			<c:if test="${stat.last}">  
				var time="${row.Time}";
				var date="${row.Date}";
			</c:if>

		  </c:forEach>
		  
		  
		  var save = new Array(); 
		  var state = new Array();
		  <c:forEach  var="row1" items="${result.rows}">
		  	var date1="${row1.Date}";
			var time1="${row1.Time}";
		  	if(date1==date)
			{
				if(time1==time)	
				{
					save.push("${row1.Device}");
					state.push("${row1.Status}");
				}
			}
		  </c:forEach>

			
		  

			



		  
		  
		</script>
        <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
        <script type="text/javascript" src="js/force.js"></script>
      </div>
      <div id="refresh">
        
        <table>
          <tr>
          <td width="750" height="100">
            <center>
            <input type="button" class="ref" onclick="javascript:window.location.reload() " value="update"/>
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
