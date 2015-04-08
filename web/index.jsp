<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="./styles.css">
</head>
<body>
<%=(request.getAttribute("equation")==null)?"<br>":request.getAttribute("equation").toString()%>
<br>
<form method="post" name = "calc" action = "/calc">
    <input  type="text" id="calcinput" maxlength="100" name="equation"
            value="<%=(request.getAttribute("result")==null)?"":request.getAttribute("result").toString()%>">
    <br><br>
    <ul>
        <li><a href="#" onclick=calc.equation.value+="1">1</a></li>
        <li><a href="#" onclick=calc.equation.value+="2">2</a></li>
        <li><a href="#" onclick=calc.equation.value+="3">3</a></li>
        <li><a href="#" onclick=calc.equation.value+="+">+</a></li>
        <li><a href="#" onclick=calc.equation.value+="abs">abs</a></li>
        <li><a href="#" onclick=calc.equation.value+="exp">exp</a></li>
    </ul><ul>
    <li><a href="#" onclick=calc.equation.value+="4">4</a></li>
    <li><a href="#" onclick=calc.equation.value+="5">5</a></li>
    <li><a href="#" onclick=calc.equation.value+="6">6</a></li>
    <li><a href="#" onclick=calc.equation.value+="-">-</a></li>
    <li><a href="#" onclick=calc.equation.value+="^">^</a></li>
    <li><a href="#" onclick=calc.equation.value+="ln">ln</a></li>
</ul><ul>
    <li><a href="#" onclick=calc.equation.value+="7">7</a></li>
    <li><a href="#" onclick=calc.equation.value+="8">8</a></li>
    <li><a href="#" onclick=calc.equation.value+="9">9</a></li>
    <li><a href="#" onclick=calc.equation.value+="*">*</a></li>
    <li><a href="#" onclick=calc.equation.value+="log">log</a></li>
    <li><a href="#" onclick=calc.equation.value+=")">)</a></li>
</ul><ul>
    <li><a href="#" onclick=calc.equation.value="">C</a></li>
    <li><a href="#" onclick=calc.equation.value+="0">0</a></li>
    <li><input type="submit" value="="></li>
    <li><a href="#" onclick=calc.equation.value+="/">/</a></li>
    <li><a href="#" onclick=calc.equation.value+=".">.</a></li>
    <li><a href="#" onclick=calc.equation.value+="(">(</a></li>

</ul>
</form>
</body>
</html>