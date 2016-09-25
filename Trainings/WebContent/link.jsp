<%@page import="java.util.*"%>

<%@page language="java" session="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

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
    	
    	<% for(int i = 0;i<k;i++) { %>
    	

    	data[<%= i %>] = ['<%= session.getAttribute("start"+i) %>','<%= session.getAttribute("stop"+i) %>'];
    	
    	
    	
    	<%  } %>
    	
    	
    	
    	var html = '<table><thead><tr></tr></thead><tbody>';
    	for (var i = 0, len = data.length; i < len; ++i) {
    	    html += '<tr><td><form><a href=#>kwdikos</a></td>';
    	    for (var j = 0, rowLen = data[i].length; j < rowLen; ++j ) {
    	    	html += '<td>';
    	        html +=  data[i][j] ;
    	        html += '</td>';
    	    }
    	    html += '<td><input type="submit" value="Akirosi" name="submit"></form></td></tr>';
    	}
    	html += '</tbody><tfoot><tr></tr></tfoot></table>';

    	$(html).appendTo('#div1');
        });
    
    </script>
</head>

<body>
<div id="div1"></div>
 <form action="Trainings" method="post" >
        
      
        <input  type="submit"  value="submit" name="submit">
    </form>

<p></p>
  
<p></p>
<form  action="Disconnected" method="post">
  <input type="submit" value="logout" >
</form> 
   
  
  <h1>You can have only 4 active Trainings</h1>



</body>
</html>
