<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>TODO</title>
    <link href="styles/bootstrap.min.css" rel="stylesheet">
    <link href="styles/styles.css" rel="stylesheet">
    <link href="styles/index.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid max-supported-width p-3 h-100">
        <div class="stage">

        </div>
    </div>

    <script src="scripts/jquery-3.6.0.js"></script>
    <script src="scripts/bootstrap.min.js"></script>
    <script>

        $(document).ready(function(){
            $.ajax({
                url:'/todo/folder',
                method: 'GET',
                success: function (data){
                    if(data !== "" || data !== undefined){
                        let folders = JSON.parse(data)

                        if(folders.length == 0){
                            // show no folders illustration
                            $('.stage').append($('template#no-folder-wrapper').html())
                        }
                    }
                }
            })
        })

        function createFolder(){
            $('.stage').empty()
            $('.stage').append($('template#create-folder').html())
        }

        function saveFolder(){
            $.ajax({
                url:'/todo/folder',
                method: 'POST',
                data: $('.create-folder').serialize(),
                success: function (data){
                    alert(data)
                }
            })
        }
    </script>

    <template id="create-folder">
        <form class="padding2030 container-fluid create-folder" style="width: 400px">
            <div class="row">
                <div class="col-12 mb-2">
                    <h3>Create folder</h3>
                </div>
                <div class="col-12 form-group">
                    <label for="txt-folder-name">Folder name</label>
                    <input type="text" class="form-control" id="txt-folder-name" name="txt-folder-name">
                </div>
                <div class="col-12 form-group">
                    <label for="txt-folder-description">Description</label>
                    <textarea type="text" class="form-control" id="txt-folder-description" name="txt-folder-description"></textarea>
                </div>
                <div class="col-12 text-end p-2">
                    <button class="btn btn-primary" type="button" onclick="saveFolder()">Save</button>
                </div>
            </div>
        </form>
    </template>
    <template id="no-folder-wrapper">
        <div class="text-center no-folder-wrapper">
            <img src="assets/empty.svg">
            <h3>No todo folder found.</h3>
            <button class="btn btn-primary" onclick="createFolder()">Create folder</button>
        </div>
    </template>
</body>
</html>