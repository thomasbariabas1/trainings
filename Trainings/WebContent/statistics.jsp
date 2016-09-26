<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
 <title>SO Example</title>
    <script>
   
    $( document ).ready(function() {
   
    	var data = [];
    
    	<% int k =  (int)session.getAttribute("arithmos");%>
    	
    	<% for(int i = 1;i<k+1;i++) { %>
    	
			alert('<%= session.getAttribute("completed"+i) %>');
    	data[<%= i %>] = ['<%= session.getAttribute("count"+i) %>','<%= session.getAttribute("name"+i) %>','<%= session.getAttribute("email"+i) %>','<%= session.getAttribute("completed"+i) %>','<%= session.getAttribute("notcompleted"+i) %>','<%= session.getAttribute("last"+i) %>'];
    	
    	<%  } %>
    	
    	
    	
    	var html = '<table><thead><tr></tr></thead><tbody>';
    	
    	for (var i = 1, len = data.length; i < len; ++i) {
    		
    	    html += '<tr>';
    	    for (var j = 0, rowLen = data[i].length; j < rowLen; ++j ) {
    	    	
    	    	html += '<td>';
    	        html +=  data[i][j] ;
    	        html += '</td>';
    	    }
    	    html += '</tr>';
    	}
    	html += '</tbody><tfoot><tr></tr></tfoot></table>';

    	$(html).appendTo('#div1');
        });
    
    </script>
</head>

<body>
<div id="div1"></div>
<div>
<form action="AddTrainings" method="post" >
       <input  type="submit"  value="submitTrain" name="submitt">
    </form>
     <form  action="ShowStatistics" method="post">
       <input type="submit" value="Show statistics" >
    </form> 
    <form  action="Disconnected" method="post">
       <input type="submit" value="logout" >
    </form> 
</div>  
  
  <h1>You can have only 4 active Trainings</h1>



</body>
</html>
