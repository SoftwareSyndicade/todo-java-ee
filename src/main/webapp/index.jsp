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
            loadFolders();
        })

        function loadFolders(){
            $.ajax({
                url:'/todo/folder',
                method: 'GET',
                success: function (data){
                    if(data !== "" || data !== undefined){
                        let folders = JSON.parse(data)

                        if(folders.length == 0){
                            // show no folders illustration
                            $('.stage').empty()
                            $('.stage').append($('template#no-folder-wrapper').html())
                        }
                        else{
                            $('.stage').empty()
                            let foldersTable = $($('template#todo-folders').html())

                            $(folders).each(function (index, folder){
                                let row = $(document.createElement('tr'))

                                let nameCell = $(document.createElement('td'))
                                nameCell.text(folder.name)

                                let descriptionCell = $(document.createElement('td'))
                                descriptionCell.text(folder.description)

                                let createdonCell = $(document.createElement('td'))
                                createdonCell.text(folder.created_ON)

                                row.append(nameCell)
                                row.append(descriptionCell)
                                row.append(createdonCell)
                                foldersTable.find('tbody').append(row)
                            });


                            $('.stage').append(foldersTable)
                        }
                    }
                }
            })
        }

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
                    <button class="btn" type="button" onclick="loadFolders()">Cancel</button>
                    <button class="btn btn-primary" type="button" onclick="saveFolder()">Save</button>
                </div>
            </div>
        </form>
    </template>
    <template id="no-folder-wrapper">
        <div class="text-center no-folder-wrapper">
            <img src="assets/empty.svg">
            <h3>No TODO folder found.</h3>
            <button class="btn btn-primary" onclick="createFolder()">Create folder</button>
        </div>
    </template>
    <template id="todo-folders">
        <div class="padding2030 todo-folders">
            <div class="text-end p-2">
                <button class="btn btn-primary" onclick="createFolder()">Create folder</button>
            </div>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th style="width: 25%">Name</th>
                    <th style="width: 50%">Description</th>
                    <th style="width: 25%">Created On</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>

    </template>
</body>
</html>