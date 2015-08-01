<!DOCTYPE html>
<%@ page import="java.util.*" %>
<html>
<head>
<title>DBTool</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/db.css">
<script type="text/javascript">
		function confirmInput(){
			document.getElementById("result").innerHTML = "Processing Query..";
			return true;
		}
	</script>
</head>
<body>
	<div id="main">
		<h1>Execute DB Queries here</h1>
		<form id="dbform"
			action="${pageContext.request.contextPath}/db/submitQuery"
			onsubmit="{return confirmInput()}">
			<textarea rows="12" cols="70" name="query"
				placeholder="Entery Your Query Here">${query}</textarea>
			<br /> <input type="submit" />
		</form>
	</div>
	<div>
		Result: <span id="result">${response.message}</span>
		<div>
		
			<%
				Map<String, Object> result2 = (Map<String, Object>)request.getAttribute("response");
				if(result2.get("result") != null){
		
						ArrayList<HashMap<String, Object>> rowList = (ArrayList<HashMap<String, Object>>)result2.get("result");
						ArrayList<String> colList = (ArrayList<String>)result2.get("columnList");
						boolean header = false;
						%> <table id="ql_rest" cellspacing="0"> <%
						%> </tr> <%
							for(String col: colList){
								
								%> <th><%= col %></th> <%
							}
						%> </tr> <%
						for(HashMap<String, Object> row: rowList){
							%> </tr> <%
							for(String col: colList){
								
								%> <td><%= row.get(col) %></td> <%
							}
							%> </tr> <%
						}
						%> </table> <%
				}

			 %>

		</div>

	</div>
</body>
</html>