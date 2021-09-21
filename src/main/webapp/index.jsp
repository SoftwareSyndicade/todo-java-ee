<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link href="styles/bootstrap.min.css" rel="stylesheet">
    <link href="styles/styles.css" rel="stylesheet">
    <link href="styles/index.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid max-supported-width p-3 h-100">
        <div class="stage">
            <div class="text-center no-folder-wrapper">
                <img src="assets/empty.svg">
                <h3>No todo folder found.</h3>
                <button class="btn btn-primary" onclick="createFolder()">Create folder</button>
            </div>
        </div>
    </div>

    <script src="scripts/jquery-3.6.0.js"></script>
    <script src="scripts/bootstrap.min.js"></script>
    <script>
        function createFolder(){
            $.ajax({
                url:'/todo/folder',
                method: 'POST',
                success: function (data){

                }
            })
        }
    </script>
</body>
</html>