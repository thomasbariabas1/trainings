<%@page import="java.util.*"%>

<%@page language="java" session="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%!
public class SumSet {
    public int Num1;
    public int Num2;
    public int Num3;

    public SumSet() {
        this.Num1 = 0;
        this.Num2 = 0;
        this.Num3 = 0;
    }

    public void add(NumberSet number) {
        if(number.Num1 != null && number.Num2 != null && number.Num3 != null) {
            this.Num1 += number.Num1.intValue();
            this.Num2 += number.Num2.intValue();
            this.Num3 += number.Num3.intValue();
        }
    }
}

public class NumberSet {
    public Integer Num1;
    public Integer Num2;
    public Integer Num3;

    public NumberSet() {}

    public NumberSet(String num1, String num2, String num3) {
        this.Num1 = Integer.parseInt(num1, 10);
        this.Num2 = Integer.parseInt(num2, 10);
        this.Num3 = Integer.parseInt(num3, 10);
    }
}
%>
<%
SumSet sum = new SumSet();

List<NumberSet> numbers = new ArrayList<NumberSet>();
String[] num1 = request.getParameterValues("num1");
String[] num2 = request.getParameterValues("num2");
String[] num3 = request.getParameterValues("num3");

if(num1 != null && num2 != null && num3 != null) {
    for(int v = 0; v < num1.length; v++) {
        try {
            numbers.add(new NumberSet(num1[v], num2[v], num3[v]));
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
            //Skip invalid value
        }
    }
}
//Add some empty rows
numbers.add(new NumberSet());
numbers.add(new NumberSet());
numbers.add(new NumberSet());
%>
  <!DOCTYPE html>
<html>
<head>
 <title>SO Example</title>
    <script>
        function AddRow() {
            document.getElementById('TableInputs').insertAdjacentHTML('beforeend',
                '<tr>'+
                   
                    '<td><% out.print( request.getParameterValues("start"));%></td>'+            
                    '<td><% out.print( request.getParameterValues("stop"));%></td>'+
                   
                '</tr>');
        }
    </script>
</head>

<body>
 <form action="Trainings" method="post">
        <table>
            <tbody id='TableInputs'>
            <% for(NumberSet number : numbers) {
                sum.add(number);%>
                <tr>
                    
                </tr>
            <%}%>
            </tbody>
            <tfoot>
                <tr>
                   
                </tr>
            </tfoot>
        </table>
      
        <input type="submit" onclick='AddRow(); return false;' value="Submit"></input>
    </form>

<p></p>
  <form  action="Trainings" method="post">
   <input type="submit" value="SubmitTrain"  id="button">
  </form>
<p></p>
<form  action="Disconnected" method="post">
  <input type="submit" value="logout" >
</form> 
   
  
  <h1>You can have only 4 active Trainings</h1>



</body>
</html>
